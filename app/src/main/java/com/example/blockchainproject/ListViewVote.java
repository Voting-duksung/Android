package com.example.blockchainproject;


//  소속대학이름을 클래스에 담기 위한 선언
public class ListViewVote {
    private String college;
    private String UserNumber;

    public void setCollege(String college) {
        college = college;
    }

    public String getCollege() { return this.college;}

    public ListViewVote(String college) {
        this.college = college;
    }

    public void setUserNumber(String UserNumber){ UserNumber = UserNumber; }

    public String getUserNumber(){ return this.UserNumber;}

//    public ListViewVote(String UserNumber) {this.UserNumber = UserNum; }


}
