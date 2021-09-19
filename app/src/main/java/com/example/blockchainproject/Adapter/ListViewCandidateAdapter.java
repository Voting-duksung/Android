package com.example.blockchainproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blockchainproject.ListViewCandidate;
import com.example.blockchainproject.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ListViewCandidateAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<ListViewCandidate> listViewCandidateList = new ArrayList<ListViewCandidate>();

    //ListViewAdapter의 생성자
    public ListViewCandidateAdapter (Context context, ArrayList<ListViewCandidate> listViewCandidateList) {
        this.context = context;
        this.listViewCandidateList = listViewCandidateList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater infalter = LayoutInflater.from(context);
        View itemView = infalter.inflate(R.layout.listview_candidate,parent,false);

        ListViewCandidateAdapter.VH holder = new ListViewCandidateAdapter.VH(itemView);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ListViewCandidateAdapter.VH vh = (ListViewCandidateAdapter.VH)holder;

        ListViewCandidate item = listViewCandidateList.get(position);

        //vh.candidate_number.setText("기호 "+item.getCandidateNumber());
        vh.candidate_name.setText(item.getName());
        vh.tv_candidate_campname.setText(item.getCampname());
        vh.tv_candidate_slogan.setText(item.getSlogan());



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
        TextView tv_candidate_campname;
        TextView tv_candidate_slogan;

        Button btn_promise;

        public VH(@NonNull View itemView) {
            super(itemView);

            tv_candidate_campname = itemView.findViewById(R.id.tv_candidate_campname);
            candidate_name=itemView.findViewById(R.id.tv_candidate);
            tv_candidate_slogan = itemView.findViewById(R.id.tv_candidate_slogan);
        }
    }
}
