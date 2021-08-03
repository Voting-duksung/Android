package com.example.blockchainproject.Model;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiInterface {
    //    @PUT("/retrofit/put/")
    @GET("/app/getStartedPlace")
    Call<PlaceInfo> getPlaceid(@Query("placeid") String placeid);
}

