package com.example.blockchainproject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class VotingStateRequest extends StringRequest {

    final static  private String URL="http://3.36.172.204/Voting_State.php";
    private Map<String, String> map;

    public VotingStateRequest(String placeid, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<String, String>();
        System.out.println(placeid+"wjoddisifjse");
        map.put("placeid", placeid);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
