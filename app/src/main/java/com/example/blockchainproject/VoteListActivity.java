package com.example.blockchainproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.blockchainproject.Adapter.ListViewVoteAdapter;
//import com.example.blockchainproject.Model.ApiClient;
//import com.example.blockchainproject.Model.ApiInterface;
//import com.example.blockchainproject.Model.PlaceInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;


public class VoteListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private ArrayList<ListViewVote> listViewVoteList = new ArrayList<ListViewVote>();
    private ListViewVoteAdapter adapter;

    TextView tv_starting_voting;
    TextView tv_user_name;
    Button btn_voting_state;
    boolean included = false;

    int UserVoteState;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_list);

        votingFinish();

        if(UserVoteState==1){//참여완료 했을 경우
            btn_voting_state.setBackgroundColor(Color.parseColor("#495057"));
            btn_voting_state.setTextColor(Color.parseColor("#FFFFFF"));
        }else {
            included=true;
        }


        //로그인 한 학번 받아오기
        Intent UserNumberIntent = getIntent();
        String Userid = UserNumberIntent.getExtras().getString("Userid");
        String UserNumber = UserNumberIntent.getExtras().getString("UserNumber");
        String UserName = UserNumberIntent.getExtras().getString("UserName");
        UserVoteState = UserNumberIntent.getExtras().getInt("UserVoteState");

        //진행중인 투표
        tv_starting_voting= findViewById(R.id.tv_starting_voting);
        tv_user_name=findViewById(R.id.tv_user_name);

        //adapter와 recyclerView 연결
        recyclerView = findViewById(R.id.rv_vote_list);
        adapter = new ListViewVoteAdapter(this, listViewVoteList, UserNumber, Userid, UserVoteState, included);
        recyclerView.setAdapter(adapter);


        //LinearLayoutManager와 recyclerView 연결
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                adapter.notifyDataSetChanged();

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    //아이템의 개수만큼 파싱하기
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jObject = jsonArray.getJSONObject(i);
                        String contents = jObject.getString("contents");
                        String start_regist_period = jObject.getString("start_regist_period");
                        String end_regist_period = jObject.getString("end_regist_period");
                        int count = jObject.getInt("count");
                        int studentNum = jObject.getInt("studentNum");
                        int placeCnt = jObject.getInt("placeCnt");
                        float ratio = count/studentNum*100;
                        System.out.println(start_regist_period+end_regist_period);

                        tv_starting_voting.setText("진행중인 투표 "+placeCnt+"개");
                        tv_user_name.setText(UserName);


                        //아이템의 개수만큼 recyclerView에 객체 넣어주기
                        listViewVoteList.add(new ListViewVote(contents, start_regist_period, end_regist_period, count, ratio));
                        adapter.notifyItemInserted(0);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        //받아온 학번(UserNumber)를 서버에 보내기
        VoteListRequest votelistRequest = new VoteListRequest(UserNumber,responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(votelistRequest);

        Button btn_doneVote = (Button) findViewById(R.id.btn_doneVote);
        btn_doneVote.setOnClickListener (new View.OnClickListener(){

            //후보 선택하고 투표 완료하는 과정
            @Override
            public void onClick(View v){

                Intent intent = new Intent(VoteListActivity.this, VotingStateActivity.class );
                intent.putExtra("UserNumber", UserNumber);
                intent.putExtra("Userid", Userid);

                startActivity(intent);
                finish(); //액티비티 종(메모리에서 제거)
            }
        });

    }

    public void votingFinish(){
        btn_voting_state = (Button) findViewById(R.id.btn_voting_state);
        btn_voting_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!included){// 참여완료 포함 아닐 경우
                    btn_voting_state.setBackgroundColor(Color.parseColor("#E9ECEF"));
                    btn_voting_state.setTextColor(Color.parseColor("#ADB5BD"));
                    included=true;
                }
                else {  //참여완료 포함일 경우
                    btn_voting_state.setBackgroundColor(Color.parseColor("#495057"));
                    btn_voting_state.setTextColor(Color.parseColor("#FFFFFF"));
                    included=false;
                }
            }
        });
    }

}