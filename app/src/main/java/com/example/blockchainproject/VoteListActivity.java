package com.example.blockchainproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_list);


        //로그인 한 학번 받아오기
        Intent UserNumberIntent = getIntent();
        String Userid = UserNumberIntent.getExtras().getString("Userid");
        String UserNumber = UserNumberIntent.getExtras().getString("UserNumber");
        System.out.println(UserNumber+"VoteListActivity에서 USerNumeber");
        System.out.println(Userid+"VoteListActivity에서 Userid");


        //adapter와 recyclerView 연결
        recyclerView = findViewById(R.id.rv_vote_list);
        adapter = new ListViewVoteAdapter(this, listViewVoteList, UserNumber, Userid);
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
                        String college = jObject.getString("college");
                        String startDate = jObject.getString("startDate");
                        String endDate = jObject.getString("endDate");

                        //아이템의 개수만큼 recyclerView에 객체 넣어주기
                        listViewVoteList.add(new ListViewVote(college, startDate, endDate));
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

}