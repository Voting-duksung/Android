package com.example.blockchainproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.blockchainproject.Adapter.ListViewVoteAdapter;
import com.example.blockchainproject.Model.ApiClient;
import com.example.blockchainproject.Model.ApiInterface;
import com.example.blockchainproject.Model.PlaceInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
        String UserNumber = UserNumberIntent.getExtras().getString("UserNumber");
        System.out.println(UserNumber+"VoteListActivity에서 USerNumeber");

        //placeid가져오기
        String placeid = "";
//        getPlaceId(placeid);

        //adapter와 recyclerView 연결
        recyclerView = findViewById(R.id.rv_vote_list);
        adapter = new ListViewVoteAdapter(this, listViewVoteList, UserNumber);
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

                        //아이템의 개수만큼 recyclerView에 객체 넣어주기
                        listViewVoteList.add(new ListViewVote(college));
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

    }

//    private void getPlaceId(String placeid) {
//        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//        Call<PlaceInfo> call = apiInterface.getPlaceid(placeid);
//        System.out.println("들어가졌지?");
//        call.enqueue(new Callback<PlaceInfo>() {
//            @Override
//            public void onResponse(Call<PlaceInfo> call, retrofit2.Response<PlaceInfo> response) {
//                if(response.isSuccessful() && response.body() != null) {
//                    String getted_placeid = response.body().getPlaceid();
//                    System.out.println("placeid는 "+getted_placeid);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PlaceInfo> call, Throwable t) {
//                System.out.println("에러났어... "+t.getMessage());
//            }
//        });
//    }


}