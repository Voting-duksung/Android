package com.example.blockchainproject.Model;

public class Vote {

    String placeid;
    int candidateNumber;
    String UserNumber;

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
