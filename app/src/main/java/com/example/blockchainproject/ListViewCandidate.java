package com.example.blockchainproject;


//  후보자를 클래스에 담기 위한 선언
public class ListViewCandidate {

    private String name;
//    private String start_regist_period;
//    private String end_regist_period;
    private int voteCount;
    private int candidateNumber;

    public void setName(String name) { name = name; }

    public String getName() { return this.name; }

    public void setVoteCount(int voteCount) {voteCount = voteCount; }

    public int getVoteCount() { return this.voteCount; }

    public void setCandidateNumber(int candidateNumber) {candidateNumber = candidateNumber; }

    public int getCandidateNumber() { return this.candidateNumber; }

//    public void setStart_regist_period(String start_regist_period) { start_regist_period = start_regist_period;}
//
//    public String getStart_regist_period() { return this.start_regist_period;}
//
//    public void setEnd_regist_period(String end_regist_period) { end_regist_period=end_regist_period;}
//
//    public String getEnd_regist_period() {retrun this.end_regist_period}

//    public ListViewCandidate(String name, String imgPath, String promisePath) {
//        this.name = name;
//        this.imgPath= imgPath;
//        this.promisePath = promisePath;
//    }

    public ListViewCandidate(String name, int voteCount, int candidateNumber) {
        this.name = name;
        this.voteCount = voteCount;
        this.candidateNumber= candidateNumber;
    }
}