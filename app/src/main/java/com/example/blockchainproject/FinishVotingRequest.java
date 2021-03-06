package com.example.blockchainproject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class FinishVotingRequest extends StringRequest {

    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://3.36.172.204/FinishVoting.php";
    private Map<String, String> map;
    //private Map<String, String>parameters;

    public FinishVotingRequest(String candidateresult, int UserVoteState, String candidateid, String UserNumber,String placeid, int count, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("candidateresult", candidateresult);
        map.put("UserVoteState", String.valueOf(UserVoteState));
        map.put("candidateid", candidateid);
        map.put("UserNumber", UserNumber);
        map.put("placeid", placeid);
        map.put("count", String.valueOf(count));
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }

}
