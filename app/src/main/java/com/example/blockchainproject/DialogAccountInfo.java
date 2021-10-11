package com.example.blockchainproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DialogAccountInfo extends AppCompatActivity {

    TextView tv_account1;
    TextView tv_account2;
    TextView tv_account3;
    TextView tv_account4;
    TextView tv_account5;
    TextView tv_account6;
    TextView tv_account7;
    TextView tv_account8;
    TextView tv_account9;
    TextView tv_account10;

    public String UserNumber;
    public String Userid;
    public String colleage;
    public String placeid;
    public int UserVoteState;
    public String startDate;
    public String endDate;
    public int count;

    public Button btn_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_account_info);

        check_btn();

        //10개 계정 받아오기
        Intent AccountIntent = getIntent();
        String[] accounts = AccountIntent.getStringArrayExtra("accounts");

        //선거장 정보 받아오기
        placeid = AccountIntent.getExtras().getString("placeid");
        colleage = AccountIntent.getExtras().getString("colleage");
        Userid = AccountIntent.getExtras().getString("Userid");
        UserNumber= AccountIntent.getExtras().getString("UserNumber");
        UserVoteState = AccountIntent.getExtras().getInt("UserVoteState");
        startDate= AccountIntent.getExtras().getString("startDate");
        endDate= AccountIntent.getExtras().getString("endDate");
        count = AccountIntent.getExtras().getInt("count");


        tv_account1 = findViewById(R.id.tv_account1);
        tv_account1.setText(accounts[0]);
        tv_account2 = findViewById(R.id.tv_account2);
        tv_account2.setText(accounts[1]);
        tv_account3 = findViewById(R.id.tv_account3);
        tv_account3.setText(accounts[2]);
        tv_account4 = findViewById(R.id.tv_account4);
        tv_account4.setText(accounts[3]);
        tv_account5 = findViewById(R.id.tv_account5);
        tv_account5.setText(accounts[4]);
        tv_account6 = findViewById(R.id.tv_account6);
        tv_account6.setText(accounts[5]);
        tv_account7 = findViewById(R.id.tv_account7);
        tv_account7.setText(accounts[6]);
        tv_account8 = findViewById(R.id.tv_account8);
        tv_account8.setText(accounts[7]);
        tv_account9 = findViewById(R.id.tv_account9);
        tv_account9.setText(accounts[8]);
        tv_account10 = findViewById(R.id.tv_account10);
        tv_account10.setText(accounts[9]);


        //사이즈 조절
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();

        int width = (int) (dm.widthPixels * 1); // Display 사이즈의 90%

        int height = (int) (dm.heightPixels * 1); // Display 사이즈의 90%

        getWindow().getAttributes().width = width;

        getWindow().getAttributes().height = height;
    }

    public void check_btn() {
        //"확인했습니다" 눌렀을 때
        btn_check = findViewById(R.id.btn_check);
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("plaaaa"+placeid);

                Intent intent = new Intent(DialogAccountInfo.this, VoteActivity.class);
                intent.putExtra("colleage", colleage);
                intent.putExtra("placeid", placeid);
                intent.putExtra("UserNumber", UserNumber);
                intent.putExtra("Userid", Userid);
                intent.putExtra("UserVoteState", UserVoteState);
                intent.putExtra("count", count);

                startActivity(intent);
            }
        });
    }
    
}
