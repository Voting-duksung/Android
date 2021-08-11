package com.example.blockchainproject.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceInfo {

    @Expose
    @SerializedName("placeid") private String placeid;

//    @Expose
//    @SerializedName("name") private String name;
//
//    @Expose
//    @SerializedName("period") private String period;
//
//    @Expose
//    @SerializedName("contents") private String contents;
//
//    @Expose
//    @SerializedName("isStarted") private String isStarted;
//
//    @Expose
//    @SerializedName("imageURL") private String imageURL;

    public String getPlaceid() {
        return placeid;
    }

    public void setPlaceid(String placeid){
        this.placeid = placeid;
    }


}

