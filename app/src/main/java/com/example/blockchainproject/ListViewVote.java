package com.example.blockchainproject;


//  소속대학이름을 클래스에 담기 위한 선언
public class ListViewVote {
    private String college;
    private String UserNumber;
    private String startDate;
    private String endDate;

    public void setCollege(String college) {
        college = college;
    }

    public String getCollege() { return this.college;}

    public ListViewVote(String college, String startDate, String endDate) {
        this.college = college;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setUserNumber(String UserNumber){ UserNumber = UserNumber; }

    public String getUserNumber(){ return this.UserNumber;}

//    public ListViewVote(String UserNumber) {this.UserNumber = UserNum; }

    public void setStartDate(String startDate) { startDate = startDate; }

    public String getStartDate() { return this.startDate; }

    public void setEndDateDate(String endDate) { endDate = endDate; }

    public String getEndDate() { return this.endDate; }


}
