package com.example.blockchainproject.Adapter;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ProgressBar;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.example.blockchainproject.ListViewCandidate;
import com.example.blockchainproject.ListViewVoteResult;
import com.example.blockchainproject.ListViewVoteResultDetail;
import com.example.blockchainproject.LoginActivity;
import com.example.blockchainproject.R;
import com.example.blockchainproject.VoteListActivity;
import com.example.blockchainproject.VotingResultDetailActivity;
import com.example.blockchainproject.VotingStateActivity;


import java.util.ArrayList;

import jnr.ffi.annotations.In;

public class ListViewVotingStateAdapter extends RecyclerView.Adapter {

    Context context;
    private ArrayList<ListViewVoteResult> listViewVoteResultsList = new ArrayList<ListViewVoteResult>();

    public String placeid;
    public String place_name;
    public String start_period;
    public String end_period;
    public int studentNum;
    public int count;
    public int candidateResult;
    TextView tv_voting_percent_detail;
    CircleProgressBar circleProgressBar;
    ProgressBar progressBar;

    //ListViewAdapter의 생성자
    public ListViewVotingStateAdapter (Context context, ArrayList<ListViewVoteResult> listViewVoteResultsList) {
        this.context = context;
        this.listViewVoteResultsList = listViewVoteResultsList;
    }
    //plz

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.listview_voting_state,parent,false);

        ListViewVotingStateAdapter.VH holder = new ListViewVotingStateAdapter.VH(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ListViewVotingStateAdapter.VH vh = (ListViewVotingStateAdapter.VH)holder;

        ListViewVoteResult item = listViewVoteResultsList.get(position);

        //후보자 투표
//        int candidate_result_ratio=  item.getCandidateresult()/item.getStudentNum()*100;
        //최종 투표율
        //float vote_result_ratio = item.getCount()/item.getStudentNum() * 100;

        vh.candidateName.setText(item.getCandidateName());
        vh.start_regist_period.setText(item.getStart_regist_peroid()+"~"+item.getEnd_regist_period());
        vh.placeName.setText(item.getPlaceName());

//        vh.tv_candidate_ratio.setText(String.valueOf(candidate_result_ratio));
        vh.tv_voting_percent.setText(String.valueOf(item.getCount()/item.getStudentNum() * 100));


        place_name = item.getPlaceName();
        start_period = item.getStart_regist_peroid();
        end_period = item.getEnd_regist_period();
        placeid = item.getPlaceid();
        studentNum = item.getStudentNum();
        count = item.getCount();
        candidateResult = item.getCandidateresult();

//
//        System.out.println("COUNt 어절절  " + count);
//        System.out.println("studentnum 어절절  " + studentNum);
//        System.out.println("vote_result 어절절  " + vote_result_ratio);
        circleProgressBar.setProgress(item.getCandidateresult()/item.getStudentNum()*100);


//        onButtonDetail();

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(context, VotingResultDetailActivity.class);
//                intent.putExtra("placeid", placeid);
//                intent.putExtra("place_name", place_name);
//                intent.putExtra("start_period", start_period);
//                intent.putExtra("end_period", end_period);
//                intent.putExtra("studentNum", studentNum);
////                intent.putExtra("vote_result_ratio",vote_result_ratio);
//                intent.putExtra("count",count);
////
//                ((Activity)context).startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//
//            }
//        });

    }

    //지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return (listViewVoteResultsList == null) ? 0 : listViewVoteResultsList.size();
    }


    class VH extends RecyclerView.ViewHolder {

        TextView candidateName;
        TextView placeName;
        TextView start_regist_period;
        TextView candidate_voteCount;
        TextView tv_voting_percent;
        TextView tv_candidate_percent_detail;
        TextView tv_candidate_ratio;
        TextView tv_voting_percent_detail;


        public VH(@NonNull View itemView) {
            super(itemView);

            candidateName=itemView.findViewById(R.id.tv_candidate_name);
            placeName = itemView.findViewById(R.id.tv_vote_name);
            start_regist_period = itemView.findViewById(R.id.tv_vote_start_period);
            candidate_voteCount=itemView.findViewById(R.id.tv_voting_percent);
            tv_voting_percent=itemView.findViewById(R.id.tv_voting_percent);
//            tv_candidate_ratio=itemView.findViewById(R.id.tv_candidate_ratio);
            tv_voting_percent_detail = itemView.findViewById(R.id.tv_voting_percent_detail);
            circleProgressBar=itemView.findViewById(R.id.cpb_circlebar);
            progressBar = itemView.findViewById(R.id.candidate_progress);
            tv_voting_percent_detail = itemView.findViewById(R.id.tv_voting_percent_detail);

            itemView.setClickable(true);
            tv_voting_percent_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(context, VotingResultDetailActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("placeid", placeid);
                        intent.putExtra("place_name", place_name);
                        intent.putExtra("start_period", start_period);
                        intent.putExtra("end_period", end_period);
                        intent.putExtra("studentNum", studentNum);
//                intent.putExtra("vote_result_ratio",vote_result_ratio);
                        intent.putExtra("count",count);

                        context.startActivity(intent);
//
//                        ((Activity)context).startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                }
            });

        }

    }

//    public void onButtonDetail() {
//        tv_voting_percent_detail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(context, VotingResultDetailActivity.class);
//                intent.putExtra("placeid", placeid);
//                intent.putExtra("place_name", place_name);
//                intent.putExtra("start_period", start_period);
//                intent.putExtra("end_period", end_period);
//                intent.putExtra("studentNum", studentNum);
//
//                context.startActivity(intent);
//                System.out.println("꺄아");
//            }
//        });
//    }
}


