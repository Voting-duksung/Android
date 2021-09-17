package com.example.blockchainproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.blockchainproject.Adapter.DialogVotingResultAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VotingResultDetailActivity extends AppCompatActivity {

    private ArrayList<ListViewVoteResultDetail> listViewVoteResultDetails = new ArrayList<ListViewVoteResultDetail>();
    private DialogVotingResultAdapter dialog_adapter;

    public String candidateName;
    public int candidateresult;
    public int studentNum;

    public String placeName;
    public String start_period;
    public String end_period;
    public String placeid;
    public int count;
    public float vote_result_ratio;

    RecyclerView votingDetail;
    TextView tv_voting_name;
    TextView tv_period;
    TextView tv_vote_rate;
    TextView tv_voting_result_full;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_voting_result_detail);


        //사이즈 조절
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();

        int width = (int) (dm.widthPixels * 0.98); // Display 사이즈의 90%

        int height = (int) (dm.heightPixels * 0.98); // Display 사이즈의 90%

        getWindow().getAttributes().width = width;

        getWindow().getAttributes().height = height;



        //투표 이름, 선거기간, placeid, 학생 받아오기.
        Intent UserNumberIntent = getIntent();
        placeName = UserNumberIntent.getExtras().getString("place_name");
        start_period = UserNumberIntent.getExtras().getString("start_period");
        end_period = UserNumberIntent.getExtras().getString("end_period");
        placeid = UserNumberIntent.getExtras().getString("placeid");
        studentNum = UserNumberIntent.getExtras().getInt("studentNum");
//        vote_result_ratio = UserNumberIntent.getExtras().getFloat("vote_result_ratio");
        count = UserNumberIntent.getExtras().getInt("count");
        vote_result_ratio =  count/studentNum * 100;
        System.out.println("vote_result_ratio"+vote_result_ratio);

        //투표 결과 더보기 recyclerView
        votingDetail = findViewById(R.id.rv_result_candidate_list);
        dialog_adapter = new DialogVotingResultAdapter(this, listViewVoteResultDetails);
        votingDetail.setAdapter(dialog_adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        votingDetail.setLayoutManager(layoutManager);

        //recycler();
        respond();

        tv_voting_name = findViewById(R.id.tv_voting_name);
        tv_period = findViewById(R.id.tv_period);
        tv_vote_rate = findViewById(R.id.tv_vote_rate);
        tv_voting_result_full = findViewById(R.id.tv_voting_result_full);

        System.out.println(count);
        System.out.println(vote_result_ratio);
        tv_voting_name.setText(placeName);
        tv_period.setText("투표 기간 "+start_period+"~"+end_period);
        tv_vote_rate.setText("최종 투표율 "+String.valueOf(vote_result_ratio)+"%");
        tv_voting_result_full.setText("전체 유권자 "+studentNum+"명 중 "+count+"명 투표\n 최종 투표율 "+String.valueOf(vote_result_ratio)+"%");

    }


    public void respond(){

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog_adapter.notifyDataSetChanged();

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jObject = jsonArray.getJSONObject(i);
                        candidateName = jObject.getString("candidateName");
                        candidateresult = jObject.getInt("candidateresult");
                        listViewVoteResultDetails.add(new ListViewVoteResultDetail(candidateName, candidateresult, studentNum));
                        dialog_adapter.notifyItemInserted(0);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        };

        VotingResultDetailRequest VotingDetailRequest = new VotingResultDetailRequest(placeid, responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(VotingDetailRequest);
    }
}
