package com.example.blockchainproject;


//  소속대학이름을 클래스에 담기 위한 선언
public class ListViewVote {
    private String contents;
    private String UserNumber;
    private String start_regist_peroid;
    private String end_regist_period;
    private int count;
    private int ratios;
    private String placeid;

    public void setContents(String contents) {
        contents = contents;
    }

    public String getContents() { return this.contents;}

    public ListViewVote(String contents, String start_regist_peroid, String end_regist_period, int count, int ratios, String placeid) {
        this.contents = contents;
        this.start_regist_peroid = start_regist_peroid;
        this.end_regist_period = end_regist_period;
        this.count = count;
        this.ratios = ratios;
        this.placeid = placeid;
    }

    public void setUserNumber(String UserNumber){ UserNumber = UserNumber; }

    public String getUserNumber(){ return this.UserNumber;}

//    public ListViewVote(String UserNumber) {this.UserNumber = UserNum; }

    public void setStart_regist_peroid(String start_regist_peroid) { start_regist_peroid = start_regist_peroid; }

    public String getStart_regist_peroid() { return this.start_regist_peroid; }

    public void setEnd_regist_period(String end_regist_period) { end_regist_period = end_regist_period; }

    public String getEnd_regist_period() { return this.end_regist_period; }

    public void setCount(int count) { count = count;}

    public int getCount() {return this.count;}

    public void setRatio(int ratios) {ratios=ratios;}

    public int getRatio() { return this.ratios;}

    public void setPlaceid(float placeid) {placeid=placeid;}

    public String getPlaceid() { return this.placeid;}



}
