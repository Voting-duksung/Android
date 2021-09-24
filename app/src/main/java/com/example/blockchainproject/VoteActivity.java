package com.example.blockchainproject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import com.example.blockchainproject.Adapter.ListViewVotingNowAdapter;
import com.example.blockchainproject.Model.ApiClient;
import com.example.blockchainproject.Model.ApiInterface;
import com.example.blockchainproject.Model.Vote;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;


public class VoteActivity extends AppCompatActivity implements OnItemClickListener {

    Context context;

    RecyclerView recyclerView;
    private ArrayList<ListViewCandidate> listViewCandidateList = new ArrayList<ListViewCandidate>();
    private ListViewVotingNowAdapter adapter;
    private Button btn_vote;

    public View tv_vote_college1;

    public int voteCount;
    public String UserNumber;
    int candidateNumber;
    String colleage;
    String Userid;
    public int UserVoteState;
    public String startDate;
    public String endDate;

    TextView candidateName;

    //retrofit
    private ApiInterface service;

    //placeid를 받아와야함.
    String placeid;

    public Dialog dialog_voting_paper;
    public Button btn_summit;

    public Dialog dialog_finish_voting;
    public Button btn_go_home;

    public String name_clicked;
    public String candidateid_clicked;
    public String campname_clicked;

    public TextView tv_candidate_number1;
    public TextView tv_campname1;

    public TextView tv_place_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        //TextView textView = findViewById(R.id.tv_title);


        Intent intent = getIntent();
        colleage = intent.getExtras().getString("colleage");
        Userid = intent.getExtras().getString("Userid");
        placeid = intent.getExtras().getString("placeid");
        UserVoteState = intent.getExtras().getInt("UserVoteState");
        UserNumber = intent.getExtras().getString("UserNumber");
        startDate = intent.getExtras().getString("startDate");
        endDate = intent.getExtras().getString("endDate");
        //colleage 잘 받아와짐 (0922)

        TextView tv_vote_college1 = findViewById(R.id.tv_vote_college1);
        tv_vote_college1.setText(colleage);

        TextView tv_vote_period1 = findViewById(R.id.tv_vote_period1);
        tv_vote_period1.setText("투표기간    "+startDate+" ~ "+endDate);


        recyclerView = findViewById(R.id.rv_vote_candidate_list);
        adapter = new ListViewVotingNowAdapter(this, listViewCandidateList, this);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        voting();

        //다이얼로그_'투표하기'눌렀을 때 선거용지 보여주기
        dialog_voting_paper = new Dialog(VoteActivity.this);
        dialog_voting_paper.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_voting_paper.setContentView(R.layout.dialog_voting_paper);
        dialog_voting_paper.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        //선거용지에서 '투표 제출하기' 눌렀을 때
        dialog_finish_voting = new Dialog(VoteActivity.this);
        dialog_finish_voting.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_finish_voting.setContentView(R.layout.activity_finish_voting);
        dialog_finish_voting.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                adapter.notifyDataSetChanged();

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jObject = jsonArray.getJSONObject(i);
                        String candidateid = jObject.getString("candidateid");
                        String candidate_name = jObject.getString("name");
                        String campname = jObject.getString("campname");
                        String slogan = jObject.getString("slogan");
                        String promise = jObject.getString("promise");
                        String colleage = jObject.getString("colleage");
                        placeid = jObject.getString("wantvote");
                        String candidateresult = jObject.getString("candidateresult");


                        listViewCandidateList.add(new ListViewCandidate(candidateid, candidate_name, campname, slogan, promise, colleage, placeid, candidateresult));


                        adapter.notifyItemInserted(0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        RecyclerView list_container = findViewById(R.id.rv_candidate_list);
        ListViewVotingNowAdapter adapter = new ListViewVotingNowAdapter(this, listViewCandidateList, this);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this);

        CandidateListRequest candidatelistRequest = new CandidateListRequest(placeid, responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(candidatelistRequest);


    }
        //SQLiteDatabase db = voteCount.getWriteableDatabase();

    //투표하기 버튼
    public void voting() {
        // 레트로핏 연결
        service = ApiClient.getApiClient().create(ApiInterface.class);
    }

    //'투표하러가기'눌렀을 때 보이는 투표용지 dialog
    public void showDialogVotingPaper(){

        dialog_voting_paper.show();
        btn_summit = dialog_voting_paper.findViewById(R.id.btn_summit);
    }

    public void showDialogFinishVoting(){

        dialog_finish_voting.show();
//        tv_place_name = (TextView) dialog_finish_voting.findViewById(R.id.tv_place_name);
//        tv_place_name.setText(colleage);
    }

    //Adapter에서 누른 후보자 정보 가지고오기
    @Override
    public void onItemClick(String name_clicked, String candidateid_clicked, String campname_clicked, String candidateresult_clicked){
        //System.out.println("고른 후보 이름" + name_clicked);
        //System.out.println("고른 후보 이름" + campname_clicked);
        //여기까진 고른 후보 정보 잘 온다


        String name = name_clicked;
        String candidateid = candidateid_clicked;
        String campname = campname_clicked;
        String candidateresult = candidateresult_clicked;

        System.out.println("고른 후보 이름" + campname);

        Button btn_vote = (Button) findViewById(R.id.btn_vote);
        btn_vote.setOnClickListener (new View.OnClickListener(){
            //후보 선택하고 투표 완료하는 과정
            @Override
            public void onClick(View v){


                showDialogVotingPaper();
                tv_candidate_number1 = (TextView) dialog_voting_paper.findViewById(R.id.tv_candidate_number1);
                tv_candidate_number1.setText(candidateid);

                tv_campname1 = (TextView) dialog_voting_paper.findViewById(R.id.tv_campname1);
                tv_campname1.setText(campname);

                Call<Vote> call_get = service.getVote(placeid, candidateNumber, UserNumber);
                call_get.enqueue(new Callback<Vote>() {
                    @Override
                    public void onResponse(Call<Vote> call, retrofit2.Response<Vote> response) {
                        //성공했을 경우
                        if (response.isSuccessful()) {//응답을 잘 받은 경우
                            String result = response.body().toString();
                            System.out.println("투표 성공~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                            Toast.makeText(getApplicationContext(), "투표 완료", Toast.LENGTH_LONG).show();

                        } else {    //통신은 성공했지만 응답에 문제있는 경우
                            System.out.println("error="+String.valueOf(response.code()));
                            Toast.makeText(getApplicationContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Vote> call, Throwable t) {//통신 자체 실패
                        Toast.makeText(getApplicationContext(), "Response Fail", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //dialog 속 '투표제출하기' 버튼 눌렀을때
        btn_summit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDialogFinishVoting();
                Intent intent = new Intent(VoteActivity.this, FinishVotingActivity.class);

                intent.putExtra("UserNumber", UserNumber);
                intent.putExtra("Userid", Userid);
                intent.putExtra("UserVoteState", UserVoteState);
                intent.putExtra("placeid", placeid);
                intent.putExtra("colleage", colleage);
                intent.putExtra("candidateid", candidateid);
                intent.putExtra("candidateresult", candidateresult);


                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        //아래 리스너는 btn_summit 제대로 작동 시 삭제할 것
        btn_go_home = dialog_finish_voting.findViewById(R.id.btn_go_home);
        btn_go_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ////최종 다이얼로그

                //Intent intent = new Intent(VoteActivity.this, VoteListActivity.class);
                Intent intent = new Intent(VoteActivity.this, FinishVotingActivity.class);


                intent.putExtra("UserNumber", UserNumber);
                intent.putExtra("Userid", Userid);
                intent.putExtra("UserVoteState", UserVoteState);
                intent.putExtra("placeid", placeid);
                intent.putExtra("colleage", colleage);

                intent.putExtra("candidateid", candidateid);
                intent.putExtra("candidateresult", candidateresult);


                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });



    }

}
