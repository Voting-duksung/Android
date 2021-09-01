package com.example.blockchainproject;

public class ListViewVoteResultDetail {
    private String candidateName;
    private int candidateresult;
    private int studentNum;


    public void setCandidateName(String candidateName) {candidateName=candidateName;}

    public String getCandidateName() { return this.candidateName; }

    public int getCandidateresult() {return this.candidateresult;}

    public void setCandidateresult(String candidateresult) { candidateresult = candidateresult;}

    public int getStudentNum() {return this.studentNum;}

    public void setStudentNum(String studentNum) {studentNum=studentNum;}

    public ListViewVoteResultDetail(String candidateName,int candidateresult, int studentNum) {
        this.candidateName = candidateName;
        this.candidateresult=candidateresult;
        this.studentNum=studentNum;
    }

}
