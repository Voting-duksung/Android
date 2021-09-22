package com.example.blockchainproject;


//  후보자를 클래스에 담기 위한 선언
public class ListViewCandidate {

    private String candidateid;
    private String name;
    private String campname;
    private String slogan;
    private String promise;
    private String colleage;
    private String wantvote;
    private String candidateresult;

    private String start_regist_period;
    private String end_regist_period;
    private String placeid;
    private int voteCount;
    private int candidateNumber;


//
    public void setCandidateid(String candidateid) { candidateid = candidateid; }
    public String getCandidateid() { return this.candidateid; }

    public void setName(String name) { name = name; }
    public String getName() { return this.name; }

    public void setCampname(String campname) { campname = campname; }
    public String getCampname() { return this.campname; }

    public void setSlogan(String slogan) { slogan = slogan; }
    public String getSlogan() { return this.slogan; }

    public void setPromise(String promise) { promise = promise; }
    public String getPromise() { return this.promise; }

    public void setColleage(String colleage) { colleage = colleage; }
    public String getColleage() { return this.colleage; }

    public void setWantvote(String wantvote) { wantvote = wantvote; }
    public String getWantvote() { return this.wantvote; }

    public void setCandidateresult(String candidateresult) { candidateresult = candidateresult; }
    public String getCandidateresult() { return this.candidateresult; }

    public void setStart_regist_period(String start_regist_period) { start_regist_period = start_regist_period;}
    public String getStart_regist_period() { return this.start_regist_period;}

    public void setEnd_regist_period(String end_regist_period) { end_regist_period=end_regist_period;}
    public String getEnd_regist_period() {return this.end_regist_period;}

    public void setPlaceid(String placeid) { placeid=placeid;}
    public String getPlaceid() {return this.placeid;}



//DB 바꾸기 전 set,get (DB완성되면 삭제할 것)
//    public void setVoteCount(int voteCount) {voteCount = voteCount; }
//
//    public int getVoteCount() { return this.voteCount; }
//
//    public void setCandidateNumber(int candidateNumber) {candidateNumber = candidateNumber; }
//
//    public int getCandidateNumber() { return this.candidateNumber; }


//    public ListViewCandidate(String name, String imgPath, String promisePath) {
//        this.name = name;
//        this.imgPath= imgPath;
//        this.promisePath = promisePath;
//    }

    public ListViewCandidate(String candidateid, String name, String campname,String slogan, String promise,String colleage,String wantvote,String candidateresult) {
        this.candidateid = candidateid;
        this.name = name;
        this.campname=campname;
        this.slogan = slogan;
        this.promise = promise;
        this.colleage = colleage;
        this.wantvote = wantvote;
        this.candidateresult = candidateresult;
    }
}