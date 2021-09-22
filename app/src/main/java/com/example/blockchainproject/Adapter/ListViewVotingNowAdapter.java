package com.example.blockchainproject.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.example.blockchainproject.CandidateListActivity;
import com.example.blockchainproject.ListViewCandidate;
import com.example.blockchainproject.R;
import com.example.blockchainproject.VoteActivity;
import com.example.blockchainproject.VoteListActivity;
import com.example.blockchainproject.VotingResultDetailActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;


public class ListViewVotingNowAdapter extends RecyclerView.Adapter{

    private Context context;
    private ArrayList<ListViewCandidate> listViewCandidateList = new ArrayList<ListViewCandidate>();
    private RadioButton lastChecked = null;

    public String candidateid;
    public String campname;


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
        vh.candidate_number.setText(item.getCandidateid());

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
        RadioButton rb_check;

        public VH(@NonNull View itemView) {
            super(itemView);

            candidate_name=itemView.findViewById(R.id.tv_candidate);
            candidate_number=itemView.findViewById(R.id.tv_candidate_number);
            box1 = itemView.findViewById(R.id.box1);
            box2 = itemView.findViewById(R.id.box2);
            rb_check = itemView.findViewById(R.id.rb_check);

            //itemView.setClickable(true);
//            box1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int pos = getAdapterPosition();
//
//                    if(pos!=RecyclerView.NO_POSITION) {
//                        box1.setBackgroundResource(R.drawable.edge_candidate_check1);
//                        box2.setBackgroundResource(R.drawable.edge_candidate_check2);
//                    }
//                    }
//                }
//            );

            rb_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            int pos = getAdapterPosition();

                            //radioButton 선택 시 색 변화
                            //다른 항목 선택 시 Background 원상태로 되진 않음
                            if(pos!=RecyclerView.NO_POSITION){
                                box1.setBackgroundResource(R.drawable.edge_candidate_check1);
                                box2.setBackgroundResource(R.drawable.edge_candidate_check2);

                                if(lastChecked != null){
                                    lastChecked.setChecked(false);
                                }
                                lastChecked = rb_check;

                                String candidateid1 = listViewCandidateList.get(pos).getCandidateid();
                                System.out.println("여기 어뎁터 "+candidateid1);
                                //찍으면 잘 나옴

                                Intent pushedIntent = new Intent(context, VoteActivity.class);
                                pushedIntent.putExtra("name_clicked", listViewCandidateList.get(pos).getName());
                                pushedIntent.putExtra("candidateid_clicked", listViewCandidateList.get(pos).getCandidateid());
                                pushedIntent.putExtra("campname_clicked", listViewCandidateList.get(pos).getCampname());
                                context.startActivity(pushedIntent);

                            }


                        }
                    }
            );


        }
    }

}


