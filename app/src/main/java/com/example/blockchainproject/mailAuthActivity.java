package com.example.blockchainproject;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.blockchainproject.Adapter.ListViewCandidateAdapter;
import com.example.blockchainproject.Model.ApiClient;
import com.example.blockchainproject.Model.ApiInterface;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;


public class mailAuthActivity extends AppCompatActivity {


    MainHandler mainHandler;


    //인증코드
    String GmailCode;

    //인증번호 입력하는 곳
    EditText emailText, emailCodeText;
    Button sendButton, emailCodeButton;
    static int value;
    int mailSend=0;

    private ApiInterface service;

    public String UserNumber;
    public String Userid;
    public String colleage;
    public String startDate;
    public String endDate;
    public int UserVoteState;
    public String placeid;
    public String content;
    public String candidateresult;

    public String[] accounts = new String[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mailauth);

        //retrofit
        service = ApiClient.getApiClient().create(ApiInterface.class);

        account_show();

        Intent intent = getIntent();
        UserNumber = intent.getExtras().getString("UserNumber");
        Userid = intent.getExtras().getString("Userid");
        placeid = intent.getExtras().getString("placeid");
        colleage = intent.getExtras().getString("colleage");
        UserVoteState = intent.getExtras().getInt("UserVoteState");
        startDate = intent.getExtras().getString("startDate");
        endDate = intent.getExtras().getString("endDate");


        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        sendButton = findViewById(R.id.sendButton);
        //이메일 입력하는 뷰
        emailText= findViewById(R.id.emailText);
        emailCodeText= findViewById(R.id.emailCodeText);
        emailCodeButton= findViewById(R.id.emailCodeButton);

        //이메일 인증 하는 부분
        //인증코드 시간초가 흐르는데 이때 인증을 마치지 못하면 인증 코드를 지우게 만든다.
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                이메일 인증부분을 보여준다.
                System.out.println("인증번호 전송 버튼");

                //메일을 보내주는 쓰레드
                MailTread mailTread = new MailTread();
                mailTread.start();

                if(mailSend==0){
                    value=180;
                    //쓰레드 객체 생성
                    BackgrounThread backgroundThread = new BackgrounThread();
                    //쓰레드 스타트
                    backgroundThread.start();
                    mailSend+=1;
                }else{
                    value = 180;
                }

//핸들러 객체 생성
                mainHandler=new MainHandler();

            }
        });


        //인증하는 버튼이다
        //혹시 이거랑 같으면 인증을 성공시켜라라
        emailCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //이메일로 전송한 인증코드와 내가 입력한 인증코드가 같을 때
                if(emailCodeText.getText().toString().equals(GmailCode)){
                    Toast.makeText(getApplicationContext(), "인증성공", Toast.LENGTH_SHORT).show();

                    Call<ResponseBody> call_get = service.getAccount(Userid);
                    call_get.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                            //성공했을 경우
                            if (response.isSuccessful()) {//응답을 잘 받은 경우
                                String result = response.body().toString();
                                System.out.println("계정 부여하기 성공");
                                //10개 계정 보내주기
                                Intent intent = new Intent(mailAuthActivity.this, DialogAccountInfo.class );
                                intent.putExtra("accounts", accounts);
                                intent.putExtra("colleage",  colleage);
                                intent.putExtra("placeid", placeid);
                                intent.putExtra("UserNumber", UserNumber);
                                intent.putExtra("Userid", Userid);
                                intent.putExtra("UserVoteState", UserVoteState);
                                intent.putExtra("startDate", startDate);
                                intent.putExtra("endDate", endDate);

                                startActivity(intent);

                            } else {    //통신은 성공했지만 응답에 문제있는 경우
                                System.out.println("error="+String.valueOf(response.code()));
                                Toast.makeText(getApplicationContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {//통신 자체 실패
//                       Log.v(TAG, "Fail");
                            System.out.println("계정 나눠주기 통신 실패");
//                        Toast.makeText(getApplicationContext(), "Response Fail", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(), "인증번호를 다시 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    //메일 보내는 쓰레드
    class MailTread extends Thread{

        public void run(){
            mailSender mailSender = new mailSender("ses_2021@naver.com", "duksungduksung");

            //인증코드
            GmailCode = mailSender.getEmailCode();

            try {
                mailSender.sendMail("덕성 회원가입 이메일 인증", "\n\n\n인증번호입니다\n\n" + GmailCode , emailText.getText().toString()+"@duksung.ac.kr");
            } catch (SendFailedException e) {

            } catch (MessagingException e) {
                System.out.println("인터넷 문제"+e);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //시간초가 카운트 되는 쓰레드
    class BackgrounThread extends Thread{
        //180초는 3분
        //메인 쓰레드에 value를 전달하여 시간초가 카운트다운 되게 한다.

        public void run(){
            //180초 보다 밸류값이 작거나 같으면 계속 실행시켜라
            while(true){
                value-=1;
                try{
                    Thread.sleep(1000);
                }catch (Exception e){

                }

                Message message = mainHandler.obtainMessage();
                //메세지는 번들의 객체 담아서 메인 핸들러에 전달한다.
                Bundle bundle = new Bundle();
                bundle.putInt("value", value);
                message.setData(bundle);

                //핸들러에 메세지 객체 보내기기

                mainHandler.sendMessage(message);

                if(value<=0){
                    GmailCode="";
                    break;
                }
            }



        }
    }

    //쓰레드로부터 메시지를 받아 처리하는 핸들러
    //메인에서 생성된 핸들러만이 Ui를 컨트롤 할 수 있다.
    class MainHandler extends Handler{
        @Override
        public void handleMessage(Message message){
            super.handleMessage(message);
            int min, sec;

            Bundle bundle = message.getData();
            int value = bundle.getInt("value");

            min = value/60;
            sec = value % 60;
            //초가 10보다 작으면 앞에 0이 더 붙어서 나오도록한다.
            if(sec<10){
                //텍스트뷰에 시간초가 카운팅
                emailCodeText.setHint("0"+min+" : 0"+sec);
            }else {
                emailCodeText.setHint("0"+min+" : "+sec);
            }
        }
    }
    //계정 10개 가져오기
    public void account_show(){

        Call<ResponseBody> call_accounts = service.getAccountList();
        call_accounts.enqueue(new Callback<ResponseBody>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                //성공했을 경우
                if (response.isSuccessful()) {//응답을 잘 받은 경우
                    JSONArray jsonArray = null;
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        jsonArray = jsonObject.getJSONArray("accounts");

                        JsonParser jsonParser = new JsonParser();
                        JsonArray jsonArray1 = (JsonArray) jsonParser.parse(String.valueOf(jsonArray));

                        for (int k = 0; k < jsonArray.length(); k++) {
                            JsonObject object = (JsonObject) jsonArray1.get(k);
                            String data = object.get("data").getAsString();
                            accounts[k]=data;
                        }

                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("10개 계정 가져오기 성공");

                } else {    //통신은 성공했지만 응답에 문제있는 경우
                    Toast.makeText(getApplicationContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {//통신 자체 실패
                System.out.println("통신 자체 실패...");
                Toast.makeText(getApplicationContext(), "Response Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
