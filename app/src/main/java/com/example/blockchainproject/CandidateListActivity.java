package com.example.blockchainproject;

import android.accounts.Account;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.blockchainproject.Adapter.ListViewCandidateAdapter;
import com.example.blockchainproject.Model.AccountList;
import com.example.blockchainproject.Model.ApiClient;
import com.example.blockchainproject.Model.ApiInterface;
import com.example.blockchainproject.Model.Vote;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import jnr.a64asm.SYSREG_CODE;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;


public class CandidateListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private ArrayList<ListViewCandidate> listViewCandidateList = new ArrayList<ListViewCandidate>();
    private ListViewCandidateAdapter adapter;

    private Gson mGson;

    //Retrofit
    private ApiInterface service;

    public String UserNumber;
    public String Userid;
    public String college;
    public String startDate;
    public String endDate;

    Dialog dialog_voting_info;
    public Button btn_ok;

    Dialog dialog_account_info;
    public Button btn_go_voting;
    public Button btn_check;
    public String placeid;
    public String content;
    public String candidateresult;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_list);

        //retrofit
        service = ApiClient.getApiClient().create(ApiInterface.class);

        account();

        dialog_voting_info = new Dialog(CandidateListActivity.this);
        dialog_voting_info.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_voting_info.setContentView(R.layout.dialog_voting_info);

        // 버튼: 커스텀 다이얼로그 띄우기
        findViewById(R.id.btn_voting_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogVotingInfo(); // 아래 showDialogVotingInfo() 함수 호출
            }
        });


        Intent UserNumberIntent = getIntent();
        UserNumber = UserNumberIntent.getExtras().getString("UserNumber");
        Userid = UserNumberIntent.getExtras().getString("Userid");
        System.out.println(Userid+"CandidateListActivity 여기 Userid 넘어와야함");

        Intent intent = getIntent();
        placeid = intent.getExtras().getString("placeid");
        college = intent.getExtras().getString("college");
        startDate = intent.getExtras().getString("startDate");
        endDate = intent.getExtras().getString("endDate");
        //잘 넘어오는거 확인

        System.out.println(placeid+"CandidateListActivity의 college");




        TextView VoteCollege = findViewById(R.id.tv_vote_college1);
        VoteCollege.setText(content);

        TextView VotePeriod = findViewById(R.id.tv_vote_period1);
        VotePeriod.setText("투표기간    "+startDate+" ~ "+endDate);

        Button btn_voting_info = findViewById(R.id.btn_voting_info);


        recyclerView = findViewById(R.id.rv_candidate_list);
        adapter = new ListViewCandidateAdapter(this, listViewCandidateList);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                adapter.notifyDataSetChanged();

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jObject = jsonArray.getJSONObject(i);
                        String candidate_name = jObject.getString("name");
                        String campname = jObject.getString("campname");
                        String slogan = jObject.getString("slogan");
                        String promise = jObject.getString("promise");
                        String colleage = jObject.getString("colleage");
                        placeid = jObject.getString("wantvote");
                        String candidateresult = jObject.getString("candidateresult");

                        System.out.println(placeid+"wantvote 캔디데이트~");

//                        int voteCount = jObject.getInt("voteCount");
//                        int candidateNumber = jObject.getInt("candidateNumber");



//                        imgPath = "http://voting.dothome.co.kr"+imgPath;
//                        listViewCandidateList.add(new ListViewCandidate(candidate_name,imgPath,promisePath));
                        listViewCandidateList.add(new ListViewCandidate(candidate_name, campname, slogan, promise, colleage, placeid, candidateresult));


                        adapter.notifyItemInserted(0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        dialog_account_info = new Dialog(CandidateListActivity.this);
        dialog_account_info.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_account_info.setContentView(R.layout.dialog_account_info);

        RecyclerView list_container = findViewById(R.id.rv_candidate_list);
        ListViewCandidateAdapter adapter = new ListViewCandidateAdapter(this,listViewCandidateList);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this);

        CandidateListRequest candidatelistRequest = new CandidateListRequest(placeid,responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(candidatelistRequest);

    }
    public void showDialogVotingInfo(){
        dialog_voting_info.show();

        btn_ok = dialog_voting_info.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_voting_info.dismiss();
            }
        });

    }
    public void showDialogAccountInfo(){
        dialog_account_info.show();
        System.out.println("onClick함22");

        //계정 10개 가져오기
        Call<ResponseBody> call_accounts = service.getAccountList();
        call_accounts.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                //성공했을 경우
                if (response.isSuccessful()) {//응답을 잘 받은 경우
                    String result = null;
                    try {
                        result = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println(result+"??");
                    System.out.println("10개 가져오기 성공~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

                } else {    //통신은 성공했지만 응답에 문제있는 경우
                    Toast.makeText(getApplicationContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {//통신 자체 실패
                System.out.println("통신 자체 실패...");
                Toast.makeText(getApplicationContext(), "Response Fail", Toast.LENGTH_SHORT).show();
            }
        });

        //"확인했습니다" 눌렀을 때
        btn_check = dialog_account_info.findViewById( R.id.btn_check );
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("onClick함");

                Intent intent = new Intent(CandidateListActivity.this, VoteActivity.class );
                intent.putExtra("college", college);
                intent.putExtra("placeid",placeid);
                intent.putExtra("UserNumber",UserNumber);
                intent.putExtra("Userid",Userid);

                startActivity(intent);
            }
        });

    }

    //투표하러가기 버튼 (계정나눠주기)
    public void account() {

        //투표하러가기 버튼 눌렀을 때
        btn_go_voting = (Button)findViewById( R.id.btn_go_voting );
        btn_go_voting.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //계정 나눠주기 dialog 짜잔
                showDialogAccountInfo();

                Call<ResponseBody> call_get = service.getAccount(Userid);
                call_get.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                        //성공했을 경우
                        if (response.isSuccessful()) {//응답을 잘 받은 경우
                            String result = response.body().toString();
                            System.out.println("계정성공~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                        } else {    //통신은 성공했지만 응답에 문제있는 경우
                            System.out.println("error="+String.valueOf(response.code()));
                            Toast.makeText(getApplicationContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {//통신 자체 실패
//                       Log.v(TAG, "Fail");
                        System.out.println("계정 나눠주기 통신 실패");
//                        Toast.makeText(getApplicationContext(), "Response Fail", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}

