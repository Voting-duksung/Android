
package com.example.blockchainproject.Model;

import retrofit2.Call;

import retrofit2.http.GET;

import retrofit2.http.Query;

public interface ApiInterface {
    //    @PUT("/retrofit/put/")
    @GET("/app/getStartedPlace")
    Call<PlaceInfo> getPlaceid(@Query("placeid") String placeid, @Query("isStarted") String isStarted);

//    @FormUrlEncoded
//    @POST("/app/setVote")
//    Call<Vote> postFunc(@Field("Placeid") String Placeid, @Field("candidateid") String candidateid,@Field("UserNumber") String UserNumber );


    //    @FormUrlEncoded
    @GET("/app/setVote")
    Call<Vote> getVote(@Query("placeid") String placeid, @Query("candidateid") int candidateid, @Query("UserNumber") String UserNumber );

    @GET("/app/account")
    Call<UserAccount> getAccount(@Query("Userid") int Userid);


}
