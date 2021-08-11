package com.example.blockchainproject;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class VoteActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private ArrayList<ListViewCandidate> listViewCandidateList = new ArrayList<ListViewCandidate>();
    private ListViewVotingNowAdapter adapter;
    private Button btn_vote;

    RadioGroup rg_candidate;
    int state;

    public int voteCount;
    public String UserNumber;

    TextView candidateName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        TextView textView = findViewById(R.id.tv_title);


        Intent intent = getIntent();
        String college = intent.getExtras().getString("college");
        //college 잘 받아와짐

        //로그인 한 학번 받아오기
        Intent UserNumberIntent = getIntent();
        String UserNumber = UserNumberIntent.getExtras().getString("UserNumber");
        System.out.println(UserNumber + "여긴 VoteActivity 학번 출력");
        //못받아오고 있음

        recyclerView = findViewById(R.id.rv_vote_candidate_list);
        adapter = new ListViewVotingNowAdapter(this, listViewCandidateList);
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
                        //String imgPath = jObject.getString("imgPath");
                        int voteCount = jObject.getInt("voteCount");
                        int candidateNumber = jObject.getInt("candidateNumber");
                        //여기껀 정보 모두 잘 받아와짐.

//                        String promisePath =jObject.getString("promisePath");
//                        imgPath = "http://voting.dothome.co.kr"+imgPath;
//                        listViewCandidateList.add(new ListViewCandidate(candidate_name,imgPath,promisePath));

                        listViewCandidateList.add(new ListViewCandidate(candidate_name, voteCount, candidateNumber));

                        adapter.notifyItemInserted(0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        RecyclerView list_container = findViewById(R.id.rv_candidate_list);
        ListViewVotingNowAdapter adapter = new ListViewVotingNowAdapter(this,listViewCandidateList);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this);

        CandidateListRequest candidatelistRequest = new CandidateListRequest(college,responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(candidatelistRequest);


        //SQLiteDatabase db = voteCount.getWriteableDatabase();

        RadioGroup.OnCheckedChangeListener radioGroupClickListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup rg_candidate, int i) {
                if (i == R.id.rb_candidate1) {
                    state = 1;
                }
                else {
                    state = 2;
                }
            }
        };

        Button btn_vote = (Button) findViewById(R.id.btn_vote);
        btn_vote.setOnClickListener (new View.OnClickListener(){

            //후보 선택하고 투표 완료하는 과정
            @Override
            public void onClick(View v){

                if(state==1){
                    voteCount++;
                }
                else{
                    voteCount++;
                }

                System.out.println(voteCount+"플러스 1 잘 들어감?");
                Toast.makeText(getApplicationContext(), "투표 완료", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(VoteActivity.this, VoteListActivity.class );
                intent.putExtra("college", college);

                startActivity(intent);

            }
        });


    }
}