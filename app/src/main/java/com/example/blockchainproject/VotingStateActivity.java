package com.example.blockchainproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TableRow;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.blockchainproject.Adapter.ListViewVotingStateAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class VotingStateActivity extends AppCompatActivity {

    TableLayout candidateTableLayout;
    TextView collegeTextView;
    public String UserNumber;

    RecyclerView recyclerView;
    private ArrayList<ListViewCandidate> listViewCandidateList = new ArrayList<ListViewCandidate>();
    private ListViewVotingStateAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting_state);


        //로그인 한 학번 받아오기
        Intent UserNumberIntent = getIntent();
        String UserNumber = UserNumberIntent.getExtras().getString("UserNumber");
        int Userid = UserNumberIntent.getExtras().getInt("Userid");
        //학번 짱 잘나옴

        String college = "총학생회 학생회 선거";

        //collegeTextView = (TextView) findViewById(R.id.doneCountTextView);

        candidateTableLayout = (TableLayout) findViewById(R.id.candidateTableLayout);

        //총 선거 가능 투표장 수 변수 추가해주기
        //collegeTextView.setText("<"+college+">");

        TextView textView = findViewById(R.id.tv_name);

        recyclerView = findViewById(R.id.rv_vote_result);
        adapter = new ListViewVotingStateAdapter(this, listViewCandidateList);
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
//                        String imgPath = jObject.getString("imgPath");
                        int voteCount = jObject.getInt("voteCount");
                        int candidateNumber = jObject.getInt("candidateNumber");

                        listViewCandidateList.add(new ListViewCandidate(candidate_name, voteCount, candidateNumber));

                        adapter.notifyItemInserted(0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        RecyclerView list_container = findViewById(R.id.rv_vote_result);
        ListViewVotingStateAdapter adapter = new ListViewVotingStateAdapter(this,listViewCandidateList);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this);

        CandidateListRequest candidatelistRequest = new CandidateListRequest(college,responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(candidatelistRequest);

    }

    public void onButtonMenu(View view) {
        Intent HomeIntent = new Intent(VotingStateActivity.this, VoteListActivity.class);
        startActivity(HomeIntent);
    }
}