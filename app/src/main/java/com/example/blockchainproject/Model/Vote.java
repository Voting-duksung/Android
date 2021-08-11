package com.example.blockchainproject.Model;

public class Vote {

    String placeid;
    int candidateNumber;
    String UserNumber;

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

    public int getCandidateNumber() {return candidateNumber;}

    public void setCandidateNumber(int candidateNumber) {this.candidateNumber=candidateNumber;}

    public String getUserNumber() {return UserNumber;}

    public void setUserNumber(String UserNumber) {
        this.UserNumber=UserNumber;
    }
}
