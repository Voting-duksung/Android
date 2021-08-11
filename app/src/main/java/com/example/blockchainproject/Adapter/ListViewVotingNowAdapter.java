package com.example.blockchainproject.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.blockchainproject.ListViewCandidate;
import com.example.blockchainproject.R;

import java.util.ArrayList;

public class ListViewVotingNowAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<ListViewCandidate> listViewCandidateList = new ArrayList<ListViewCandidate>();


    //ListViewAdapter의 생성자
    public ListViewVotingNowAdapter (Context context, ArrayList<ListViewCandidate> listViewCandidateList) {
        this.context = context;
        this.listViewCandidateList = listViewCandidateList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.listview_voting_now,parent,false);

        ListViewVotingNowAdapter.VH holder = new ListViewVotingNowAdapter.VH(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ListViewVotingNowAdapter.VH vh = (ListViewVotingNowAdapter.VH)holder;

        ListViewCandidate item = listViewCandidateList.get(position);
        vh.candidate_name.setText(item.getName());
        //vh.candidate_number.setText("기호 "+item.getCandidateNumber()+". "+item.getName());

//        Glide.with(context).load(item.getImgPath()).into(vh.candidate_image);
    }

    //지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return (listViewCandidateList == null) ? 0 : listViewCandidateList.size();
    }

    class VH extends RecyclerView.ViewHolder {

        TextView candidate_name;
        ImageView candidate_image;
        TextView candidate_number;

        public VH(@NonNull View itemView) {
            super(itemView);

            candidate_name=itemView.findViewById(R.id.tv_text1);
            candidate_image=itemView.findViewById(R.id.img_candidate);
            //candidate_number=itemView.findViewById(R.id.tv_candidate_number);
        }
    }


}

