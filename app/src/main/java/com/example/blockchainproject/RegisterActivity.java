package com.example.blockchainproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private EditText join_number, join_password, join_name, join_pwck;
    private Button btn_join, btn_number_check;
    private TextView tv_college;
    private AlertDialog dialog;
    private Spinner spinner_college;
    private String[] college = {"글로벌융합대학","과학기술대학","약학대학","Art & Design대학"};
    private boolean validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_join);

        //아이디값 찾아주기
        join_number = findViewById( R.id.join_number );
        join_password = findViewById( R.id.join_password );
        join_name = findViewById( R.id.join_name );
        join_pwck = findViewById(R.id.join_pwck);
        spinner_college = findViewById(R.id.spinner_college);
        tv_college = findViewById(R.id.tv_college);

        //학번 중복 체크
        btn_number_check = (Button) findViewById(R.id.btn_number_check);
        btn_number_check.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String UserNumber = join_number.getText().toString();
                if (validate) {
                    return; //검증 완료
                }

                if (UserNumber.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("학번를 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("덕성여자대학교 재학생입니다.").setNegativeButton("확인", null).create();
                                dialog.show();
                                validate = true; //검증 완료
                                join_number.setEnabled(false); //아이디값 고정
                                btn_number_check.setBackgroundColor(getResources().getColor(R.color.black));
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("덕성여자대학교 재학생이 아닙니다.").setPositiveButton("확인", null).create();
                                dialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest = new ValidateRequest(UserNumber, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);
            }
        });

        //ArrayAdapter 객체 생성.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,college);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_college.setAdapter(adapter);

        //소속대학 선택
        spinner_college.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //메소드에서 position이 선택되었는지.
                //getItemAtPosition(position)을 통해 값을 받아올 수 있음.
                tv_college.setText(college[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tv_college.setText("");
            }
        });



        //회원가입 버튼 클릭 시 수행
        btn_join = findViewById( R.id.btn_join );
        btn_join.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String UserNumber = join_number.getText().toString();
                final String UserPwd = join_password.getText().toString();
                final String UserName = join_name.getText().toString();
                final String PassCk = join_pwck.getText().toString();
                final String UserCollege = spinner_college.getSelectedItem().toString();    //선택된 값 가져오기.


                //아이디 중복체크 했는지 확인
                if (!validate) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("학번이 중복인지 확인해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

                //한 칸이라도 입력 안했을 경우
                if (UserNumber.equals("") || UserPwd.equals("") || UserName.equals("") || UserCollege.equals("소속대학을 선택하세요.")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("모두 입력해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            boolean success = jsonObject.getBoolean( "success" );
                            //회원가입 성공시
                            if(UserPwd.equals(PassCk)) {
                                if (success) {
                                    Toast.makeText(getApplicationContext(), String.format("%s님 가입을 환영합니다.", UserName), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);

                                    //회원가입 실패시
                                } else {
                                    Toast.makeText(getApplicationContext(), "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("비밀번호가 동일하지 않습니다.").setNegativeButton("확인", null).create();
                                dialog.show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                //서버로 Volley를 이용해서 요청
                RegisterRequest registerRequest = new RegisterRequest(UserNumber, UserPwd, UserName, UserCollege, responseListener);
                RequestQueue queue = Volley.newRequestQueue( RegisterActivity.this );
                queue.add( registerRequest );
            }
        });
    }
}