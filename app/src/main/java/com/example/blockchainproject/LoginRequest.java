package com.example.blockchainproject;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://3.36.172.204/Login.php";
    private Map<String, String> map;

    public LoginRequest(String UserNumber, String UserPwd, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("UserNumber", UserNumber);
        map.put("UserPwd", UserPwd);

        System.out.println(map+"1");
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        System.out.println(map+"2");
        return map;
    }
}