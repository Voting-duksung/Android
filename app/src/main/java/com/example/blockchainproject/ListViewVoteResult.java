package com.example.blockchainproject;


//  소속대학이름을 클래스에 담기 위한 선언
public class ListViewVoteResult {
    private String placeName;
    private String start_regist_peroid;
    private String end_regist_period;
    private String candidateName;
    private int candidateresult;
    private int count;
    private int studentNum;
    private String placeid;

    public void setPlaceName(String placeName) {
        placeName = placeName;
    }

    public String getPlaceName() { return this.placeName;}

    public void setStart_regist_peroid(String start_regist_peroid) { start_regist_peroid = start_regist_peroid; }

    public String getStart_regist_peroid() { return this.start_regist_peroid; }

    public void setEnd_regist_period(String end_regist_period) { end_regist_period = end_regist_period; }

    public String getEnd_regist_period() { return this.end_regist_period; }

    public void setCandidateName(String candidateName) {candidateName=candidateName;}

    public String getCandidateName() { return this.candidateName; }

    public int getCandidateresult() {return this.candidateresult;}

    public void setCandidateresult(String candidateresult) { candidateresult = candidateresult;}

    public int getCount() {return this.count;}

    public void setCount(int count) {count=count;}

    public int getStudentNum() {return this.studentNum;}

    public void setStudentNum(String studentNum) {studentNum=studentNum;}

    public String getPlaceid() {return this.placeid;}

    public void setPlaceid(String placeid) {placeid=placeid;}

    public ListViewVoteResult(String placeName, String start_regist_peroid, String end_regist_period, String candidateName,int candidateresult, int count, int studentNum, String placeid) {
        this.placeName = placeName;
        this.start_regist_peroid = start_regist_peroid;
        this.end_regist_period = end_regist_period;
        this.candidateName = candidateName;
        this.candidateresult=candidateresult;
        this.count=count;
        this.studentNum=studentNum;
        this.placeid = placeid;
    }

}
