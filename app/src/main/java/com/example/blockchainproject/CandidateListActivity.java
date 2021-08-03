package com.example.blockchainproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.blockchainproject.Adapter.ListViewCandidateAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CandidateListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private ArrayList<ListViewCandidate> listViewCandidateList = new ArrayList<ListViewCandidate>();
    private ListViewCandidateAdapter adapter;

    public String UserNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_list);

        TextView textView = findViewById(R.id.tv_candidate);
        Button btn_go_voting;

        Intent UserNumberIntent = getIntent();
        String UserNumber = UserNumberIntent.getExtras().getString("UserNumber");
        System.out.println(UserNumber+"CandidateListActivity 여기 학번 넘어와야함");

        Intent intent = getIntent();
        String college = intent.getExtras().getString("college");
        //대학은 잘 넘어오는거 확인

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
                        //String imgPath = jObject.getString("imgPath");
                        int voteCount = jObject.getInt("voteCount");
                        int candidateNumber = jObject.getInt("candidateNumber");

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
        ListViewCandidateAdapter adapter = new ListViewCandidateAdapter(this,listViewCandidateList);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this);

        CandidateListRequest candidatelistRequest = new CandidateListRequest(college,responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(candidatelistRequest);

        //투표하러가기 버튼 눌렀을 때
        btn_go_voting = findViewById( R.id.btn_go_voting );
        btn_go_voting.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CandidateListActivity.this, VoteActivity.class );
                intent.putExtra("college", college);

                startActivity(intent);
            }
        });

    }
}
