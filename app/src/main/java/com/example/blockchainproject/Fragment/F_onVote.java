package com.example.blockchainproject.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



//    RecyclerView recyclerView;
//    private ArrayList<ListViewVote> listViewVoteList = new ArrayList<ListViewVote>();
//    private ListViewVoteAdapter adapter;
//
//    public String UserNumber;

//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(view, savedInstanceState);
//        setContentView(R.layout.activity_fragment_on_vote);
//
//        //로그인 한 학번 받아오기
//        Intent UserNumberIntent = getIntent();
//        String UserNumber = UserNumberIntent.getExtras().getString("UserNumber");
//        //여기까진 로그인한 학번 받아와짐.
//
//        //adapter와 recyclerView 연결
//        recyclerView = findViewById(R.id.rv_vote_list);
//        adapter = new ListViewVoteAdapter(this, listViewVoteList);
//        recyclerView.setAdapter(adapter);
//
//        //LinearLayoutManager와 recyclerView 연결
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(layoutManager);
//
//        Response.Listener<String> responseListener = new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                adapter.notifyDataSetChanged();
//
//                try {
//                    JSONArray jsonArray = new JSONArray(response);
//
//                    //아이템의 개수만큼 파싱하기
//                    for (int i = 0; i < response.length(); i++) {
//                        JSONObject jObject = jsonArray.getJSONObject(i);
//                        String college = jObject.getString("college");
//
//
//                        //아이템의 개수만큼 recyclerView에 객체 넣어주기
//                        listViewVoteList.add(new ListViewVote(college));
//                        adapter.notifyItemInserted(0);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//
//        //받아온 학번(UserNumber)를 서버에 보내기
//        VoteListRequest votelistRequest = new VoteListRequest(UserNumber,responseListener);
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(votelistRequest);
//
//    }

