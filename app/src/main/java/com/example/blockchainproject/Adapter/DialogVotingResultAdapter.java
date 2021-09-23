package com.example.blockchainproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ProgressBar;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blockchainproject.ListViewVoteResultDetail;
import com.example.blockchainproject.R;
import com.example.blockchainproject.VotingResultDetailActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DialogVotingResultAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<ListViewVoteResultDetail> listViewVoteResultDetails = new ArrayList<ListViewVoteResultDetail>();
    ProgressBar progress;

    public DialogVotingResultAdapter (Context context, ArrayList<ListViewVoteResultDetail> listViewVoteResultDetails) {
        this.context = context;
        this.listViewVoteResultDetails = listViewVoteResultDetails;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.listview_voting_result,parent,false);

        DialogVotingResultAdapter.VH holder = new DialogVotingResultAdapter.VH(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        DialogVotingResultAdapter.VH vh = (DialogVotingResultAdapter.VH)holder;

        ListViewVoteResultDetail item = listViewVoteResultDetails.get(position);

        int candidateresult = item.getCandidateresult();
        //후보자 투표
        double candidate_result_ratio=  item.getCandidateresult()/(double)item.getStudentNum()*100;
        int finalValue = (int) candidate_result_ratio;

        vh.candidateName.setText(item.getCandidateName());
        vh.tv_candidate_percent_detail.setText((candidate_result_ratio)+"%");

        progress.setProgress(finalValue);
        System.out.println("dialogadapter candidateresult " + candidateresult);
        System.out.println("dialogadapter candidateresultratio " + candidate_result_ratio);
    }

    @Override
    public int getItemCount() {
        return (listViewVoteResultDetails == null) ? 0 : listViewVoteResultDetails.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class VH extends RecyclerView.ViewHolder {

        TextView candidateName;
        TextView tv_candidate_percent_detail;

        public VH(@NonNull View itemView) {
            super(itemView);

            candidateName=itemView.findViewById(R.id.tv_candidate_name);
            tv_candidate_percent_detail=itemView.findViewById(R.id.tv_candidate_percent_detail);
            progress = itemView.findViewById(R.id.candidate_progress);

        }
    }
}
