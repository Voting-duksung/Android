package com.example.blockchainproject;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


public class RegisterRequest extends StringRequest {

    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://3.36.172.204/Register.php";
    private Map<String, String> map;
    //private Map<String, String>parameters;

    public RegisterRequest(String UserNumber, String UserPwd, String UserName, String UserCollege, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("UserNumber", UserNumber);
        map.put("UserPwd", UserPwd);
        map.put("UserName", UserName);
        map.put("UserCollege", UserCollege);
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}