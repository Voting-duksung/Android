package com.example.blockchainproject.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.example.blockchainproject.ListViewCandidate;
import com.example.blockchainproject.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ListViewVotingNowAdapter extends RecyclerView.Adapter{

    private Context context;
    private ArrayList<ListViewCandidate> listViewCandidateList = new ArrayList<ListViewCandidate>();
    private int selectedPosition = -1;
    boolean included = false;

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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        Log.d("position", position + "");
        ListViewVotingNowAdapter.VH vh = (ListViewVotingNowAdapter.VH)holder;


        ListViewCandidate item = listViewCandidateList.get(position);
        vh.candidate_name.setText(item.getName());

//        if (position == selectedPosition){
//            vh.box1.setSelected(true);
//        }
//        else{
//            vh.box1.setSelected(false);
//        }
//
//        vh.box1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectedPosition = holder.getAdapterPosition();
//                notifyDataSetChanged();
//                vh.box1.setBackgroundColor(ContextCompat.getColor(context, R.color.vote_select_on1));
//
//            }
//        });

        //vh.candidate_number.setText("기호 "+item.getCandidateNumber());

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
        TextView candidate_number;
        LinearLayout box1;
        LinearLayout box2;

        public VH(@NonNull View itemView) {
            super(itemView);

            candidate_name=itemView.findViewById(R.id.tv_candidate);
            candidate_number=itemView.findViewById(R.id.tv_candidate_number);
            box1 = itemView.findViewById(R.id.box1);
            box2 = itemView.findViewById(R.id.box2);

            box1.setClickable(true);
            box1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        box1.setBackgroundColor(Color.parseColor("#CCDFF9"));
                    }
                }
            });

        }
    }





//    public class MyViewHolder extends RecyclerView.ViewHolder {
//        LinearLayout box1;
//        LinearLayout box2;
//
//        public MyViewHolder(View itemView) {
//            super(itemView);
//            box1 = itemView.findViewById(R.id.box1);
//            box2 = itemView.findViewById(R.id.box2);
//        }
//        void bindItem(int pos) {
//            String txt = list.get(pos);
//            box1.setText(txt);
//
//            if(indexOfColoredItem==pos){
//                box1.setBackgroundColor(ContextCompat.getColor(context, R.color.vote_select_on1));
//            } else{
//                box1.setBackgroundColor(ContextCompat.getColor(context, R.color.vote_select_off1));
//            }
//        }
//    }


}


