package com.example.blockchainproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FinishVotingActivity extends AppCompatActivity {

    public String UserNumber;
    public String Userid;
    public int UserVoteState;
    public String placeid;
    public String colleage;
    public String candidateresult;
    public String candidateid;

    public TextView tv_place_name;
    public Button btn_go_home;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_voting);

        Intent intent = getIntent();
        UserNumber = intent.getExtras().getString("UserNumber");
        Userid = intent.getExtras().getString("Userid");
        UserVoteState = intent.getExtras().getInt("UserVoteState");
        placeid = intent.getExtras().getString("placeid");
        colleage = intent.getExtras().getString("colleage");
        candidateresult = intent.getExtras().getString("candidateresult");
        candidateid = intent.getExtras().getString("candidateid");

        tv_place_name = findViewById(R.id.tv_place_name);
        tv_place_name.setText(colleage);

        btn_go_home = (Button) findViewById(R.id.btn_go_home);
        btn_go_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FinishVotingActivity.this, VoteListActivity.class);

                intent.putExtra("UserNumber", UserNumber);
                intent.putExtra("Userid", Userid);
                intent.putExtra("placeid", placeid);

                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject( response );
                    boolean success = jsonObject.getBoolean("success");
                    if(success){
                        Toast.makeText(getApplicationContext(), "투표 기록 완료", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        //서버로 Volley를 이용해서 요청
        FinishVotingRequest finishVotingRequest = new FinishVotingRequest(candidateresult, UserVoteState, candidateid, UserNumber, responseListener);
        RequestQueue queue = Volley.newRequestQueue( FinishVotingActivity.this );
        queue.add( finishVotingRequest );


    }
}
