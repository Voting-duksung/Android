package com.example.blockchainproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.blockchainproject.Adapter.DialogVotingResultAdapter;
import com.example.blockchainproject.Adapter.ListViewVotingStateAdapter;

import org.bouncycastle.crypto.tls.TlsExtensionsUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;


public class VotingStateActivity extends AppCompatActivity {

    FrameLayout candidateFrameLayout;
    private RelativeLayout rv_vote_result;

    public String UserNumber;
    public String Userid;
    public String placeid;
    public String UserName;

    public String placeName;
    public String start_regist_period;
    public String end_regist_period;
    public String candidateName;

    //투표율과 후보자율
    public int studentNum;
    public int candidateresult;
    public int count;

    TextView tv_username;

    RecyclerView recyclerView;  //투표목록 recyclerview

    private ArrayList<ListViewVoteResult> listViewVoteResultsList = new ArrayList<ListViewVoteResult>();
    private ListViewVotingStateAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting_state);

        onButtonMenu();
        //onButtonResult();

        Intent UserNumberIntent = getIntent();
        UserName = UserNumberIntent.getExtras().getString("UserName");
        UserNumber = UserNumberIntent.getExtras().getString("UserNumber");
        Userid = UserNumberIntent.getExtras().getString("Userid");
        placeid = UserNumberIntent.getExtras().getString("placeid");
        studentNum = UserNumberIntent.getExtras().getInt("studentNum");
        //학번 짱 잘나옴

        //collegeTextView = (TextView) findViewById(R.id.doneCountTextView);

        candidateFrameLayout =  findViewById(R.id.candidateFrameLayout);


        //총 선거 가능 투표장 수 변수 추가해주기
        //collegeTextView.setText("<"+college+">");

        TextView textView = findViewById(R.id.tv_candidate_name);

        //RecyclerView item 간격 주기
        RecyclerDecorator spaceDecoration = new RecyclerDecorator(30);

        tv_username = findViewById(R.id.tv_username);
        tv_username.setText(UserName);

        //투표 목록 recyclerView
        recyclerView = findViewById(R.id.rv_vote_result);
        adapter = new ListViewVotingStateAdapter(this, listViewVoteResultsList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(spaceDecoration);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
//        votingDetail.setLayoutManager(layoutManager);


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                adapter.notifyDataSetChanged();
               // dialog_adapter.notifyDataSetChanged();

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jObject = jsonArray.getJSONObject(i);
                        placeName = jObject.getString("placeName");
                        start_regist_period = jObject.getString("start_regist_period");
                        end_regist_period = jObject.getString("end_regist_period");
                        candidateName = jObject.getString("candidateName");
                        candidateresult = jObject.getInt("candidateresult");
                        count = jObject.getInt("count");
//                        listViewCandidateList.add(new ListViewCandidate(candidate_name, start_regist_period, end_regist_period));
                        listViewVoteResultsList.add(new ListViewVoteResult(placeName, start_regist_period, end_regist_period, candidateName, candidateresult, count, studentNum, placeid));
                        //listViewVoteResultDetails.add(new ListViewVoteResultDetail(candidateName,candidateresult,studentNum));
                        adapter.notifyItemInserted(0);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        };

        RecyclerView list_container = findViewById(R.id.rv_vote_result);
        ListViewVotingStateAdapter adapter = new ListViewVotingStateAdapter(this,listViewVoteResultsList);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this);

        VotingStateRequest VotingStateRequest = new VotingStateRequest(placeid, responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(VotingStateRequest);

    }

    //진행중 눌렀을 경우
    public void onButtonMenu() {

        Button btn_onVote = (Button) findViewById(R.id.btn_onVote);
        btn_onVote.setOnClickListener (new View.OnClickListener(){

            @Override
            public void onClick(View v){

                Intent intent = new Intent(VotingStateActivity.this, VoteListActivity.class );
                intent.putExtra("UserName",UserName);
                intent.putExtra("UserNumber", UserNumber);
                intent.putExtra("Userid", Userid);

                startActivity(intent);
                finish(); //액티비티 종료(메모리에서 제거)
            }
        });
    }

}