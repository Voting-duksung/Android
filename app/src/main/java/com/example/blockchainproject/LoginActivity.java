package com.example.blockchainproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText login_number, login_password;
    private Button login_button, join_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        login_number = findViewById( R.id.login_number );
        login_password = findViewById( R.id.login_password );

        join_button = findViewById( R.id.join_button );
        join_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( LoginActivity.this, RegisterActivity.class );
                startActivity(intent);
            }
        });

        login_button = findViewById( R.id.login_button );
        login_button.setClickable(true);
        login_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("로그인버튼 눌림");
                String UserNumber = login_number.getText().toString();
                String UserPwd = login_password.getText().toString();
                System.out.println(UserNumber+UserPwd+"학번이랑 패스워드는 들어감");

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println(response+"로그인 값 받아옴");
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean( "success" );

                            if(success) {//로그인 성공시
                                int Userid = jsonObject.getInt("Userid");
                                String UserNumber = jsonObject.getString( "UserNumber" );
                                String UserPwd = jsonObject.getString( "UserPwd" );
                                String UserName = jsonObject.getString( "UserName" );
                                int UserVoteState = jsonObject.getInt( "UserVoteState" );

                                Toast.makeText( getApplicationContext(), String.format("%s님 환영합니다.", UserName), Toast.LENGTH_SHORT ).show();
                                Intent intent = new Intent(getApplicationContext(), VoteListActivity.class );

                                intent.putExtra("Userid", Userid);
                                intent.putExtra( "UserName", UserName );
                                intent.putExtra( "UserNumber", UserNumber );
                                intent.putExtra( "UserVoteState", UserVoteState);

                                startActivity(intent);

                            } else {//로그인 실패시
                                Toast.makeText( getApplicationContext(), "로그인에 실패하셨습니다.", Toast.LENGTH_SHORT ).show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };


                LoginRequest loginRequest = new LoginRequest( UserNumber, UserPwd, responseListener );
                RequestQueue queue = Volley.newRequestQueue( LoginActivity.this );
                queue.add( loginRequest );

            }
        });
    }
}
