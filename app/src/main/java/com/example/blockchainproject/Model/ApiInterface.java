
package com.example.blockchainproject.Model;

import okhttp3.ResponseBody;
import retrofit2.Call;

import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    //    @PUT("/retrofit/put/")
    @GET("/app/getStartedPlace")
    Call<PlaceInfo> getPlaceid(@Query("placeid") String placeid, @Query("isStarted") String isStarted);

    @GET("/app/setVote")
    Call<Vote> getVote(@Query("placeid") String placeid, @Query("candidateid") int candidateid, @Query("UserNumber") String UserNumber );

    @GET("/app/account/")
    Call<ResponseBody> getAccount(@Query("Userid") String Userid);

    @GET("/app/getAccount")
    Call<ResponseBody> getAccountList();


}
