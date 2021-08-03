package com.example.blockchainproject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class VoteListRequest extends StringRequest {

    final static  private String URL="http://3.36.172.204/Voting_List.php";
    private Map<String, String> map;

    public VoteListRequest(String student_number,Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<String, String>();
        map.put("UserNumber", student_number);

    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
