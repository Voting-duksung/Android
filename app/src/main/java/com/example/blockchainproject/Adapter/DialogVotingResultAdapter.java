package com.example.blockchainproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blockchainproject.ListViewVoteResultDetail;
import com.example.blockchainproject.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DialogVotingResultAdapter extends RecyclerView.Adapter  {

    private Context context;
    private ArrayList<ListViewVoteResultDetail> listViewVoteResultDetails = new ArrayList<ListViewVoteResultDetail>();


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

        //후보자 투표
        int candidate_result_ratio=  item.getCandidateresult()/item.getStudentNum()*100;


        vh.candidateName.setText(item.getCandidateName());
        vh.tv_candidate_percent_detail.setText(String.valueOf(candidate_result_ratio)+"%");
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
        }
    }
}
