//package com.example.blockchainproject.Model;
//
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class ApiClient {
//
//    private static final String URL = "http://3.36.172.204/";
//    private static Retrofit retrofit;
//
//    public static Retrofit getApiClient(){
//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();
//
//        if (retrofit == null){
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(URL)
//                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .build();
//        }
//
//        return retrofit;
//    }
//}
