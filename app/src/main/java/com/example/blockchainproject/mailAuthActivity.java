package com.example.blockchainproject;

import android.content.Intent;
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
import androidx.appcompat.app.AppCompatActivity;


public class mailAuthActivity extends AppCompatActivity {


    MainHandler mainHandler;


    //인증코드
    String GmailCode;

    //인증번호 입력하는 곳
    EditText emailText, emailCodeText;
    Button sendButton, emailCodeButton;
    static int value;
    int mailSend=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mailauth);

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
                    Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);

                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "인증번호를 다시 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    //메일 보내는 쓰레드
    class MailTread extends Thread{

        public void run(){
            mailSender mailSender = new mailSender("ses_2021@naver.com", "duksung21!");

            //인증코드
            GmailCode = mailSender.getEmailCode();

            try {
                mailSender.sendMail("덕성 회원가입 이메일 인증", "인증번호입니다\n\n" + GmailCode , emailText.getText().toString()+"@duksung.ac.kr");
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

}
