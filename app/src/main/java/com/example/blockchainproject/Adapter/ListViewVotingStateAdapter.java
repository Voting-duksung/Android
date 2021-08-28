package com.example.blockchainproject.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blockchainproject.ListViewCandidate;
import com.example.blockchainproject.ListViewVoteResult;
import com.example.blockchainproject.R;

import org.w3c.dom.Text;
import org.web3j.abi.datatypes.Int;

import java.util.ArrayList;

public class ListViewVotingStateAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<ListViewVoteResult> listViewVoteResultsList = new ArrayList<ListViewVoteResult>();

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
        int candidate_result_ratio=  item.getCandidateresult()/item.getStudentNum()*100;
        //최종 투표율
       int vote_result_ratio = item.getCount()/item.getStudentNum()*100;

        vh.candidateName.setText(item.getCandidateName());
        vh.start_regist_period.setText(item.getStart_regist_peroid()+"~"+item.getEnd_regist_period());
        vh.placeName.setText(item.getPlaceName());

        System.out.println(candidate_result_ratio);
        vh.tv_candidate_ratio.setText(String.valueOf(candidate_result_ratio));
        vh.tv_voting_percent.setText(String.valueOf(vote_result_ratio));
        //vh.candidate_voteCount.setText(""+item.getVoteCount()+1);
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
        TextView tv_candidate_ratio;

        public VH(@NonNull View itemView) {
            super(itemView);

            candidateName=itemView.findViewById(R.id.tv_candidate_name);
            placeName = itemView.findViewById(R.id.tv_vote_name);
            start_regist_period = itemView.findViewById(R.id.tv_vote_start_period);
            candidate_voteCount=itemView.findViewById(R.id.tv_voting_percent);
            tv_voting_percent=itemView.findViewById(R.id.tv_voting_percent);
            tv_candidate_ratio=itemView.findViewById(R.id.tv_candidate_ratio);

        }
    }

}

