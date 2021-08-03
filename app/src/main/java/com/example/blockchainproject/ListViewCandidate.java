package com.example.blockchainproject;


//  후보자를 클래스에 담기 위한 선언
public class ListViewCandidate {

    private String name;
    private String imgPath;
    private String promisePath;
    private int voteCount;
    private int candidateNumber;

    public void setName(String name) { name = name; }

    public String getName() { return this.name; }

    public void setImgPath(String imgPath){ imgPath = imgPath; }

    public String getImgPath() { return this.imgPath; }

    public void setPromisePath(String promisePath) { promisePath = promisePath; }

    public String getPromisePath() { return this.promisePath; }

    public void setVoteCount(int voteCount) {voteCount = voteCount; }

    public int getVoteCount() { return this.voteCount; }

    public void setCandidateNumber(int candidateNumber) {candidateNumber = candidateNumber; }

    public int getCandidateNumber() { return this.candidateNumber; }

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