package com.example.blockchainproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.blockchainproject.CandidateListActivity;
import com.example.blockchainproject.ListViewVote;
import com.example.blockchainproject.R;

import java.text.BreakIterator;
import java.util.ArrayList;

public class ListViewVoteAdapter extends RecyclerView.Adapter {

    public String UserNumber;
    private Context tv_vote_name;
    private String tv_vote_college;
    private int Userid;
    TextView votePeriod;


    //Adapter에 들어갈 list
    private ArrayList<ListViewVote> listViewVoteList = new ArrayList<ListViewVote>();

    //ListViewAdapter의 생성자
    public ListViewVoteAdapter (Context tv_vote_name, ArrayList<ListViewVote> listViewVoteList, String UserNumber, int Userid) {
        this.tv_vote_name = tv_vote_name;
        this.listViewVoteList = listViewVoteList;
        this.UserNumber = UserNumber;
        this.Userid=Userid;
    }

    //LayoutInflater를 이용하여 전 단계에서 만들었던 listview_vote.xml을 inflate 시킨다.
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(tv_vote_name);
        View itemView = inflater.inflate(R.layout.listview_vote,parent,false);

        VH holder = new VH(itemView);
        return holder;
    }

    //item을 하나하나 bind 되는 함수
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH)holder;

        ListViewVote item = listViewVoteList.get(position);
        vh.tv_vote_college.setText(item.getCollege());
        vh.tv_vote_period.setText("투표기간   "+item.getStartDate()+" ~ "+item.getEndDate());

    }

    //지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }

    //RecyclerView의 총 개수
    @Override
    public int getItemCount() {
        return (listViewVoteList == null) ? 0 : listViewVoteList.size();
    }

    class VH extends RecyclerView.ViewHolder {

        TextView tv_vote_college;
        TextView tv_vote_period;
        Button btn_lets_vote;

        public VH(@NonNull View itemView) {
            super(itemView);

            tv_vote_college=itemView.findViewById(R.id.tv_vote_college);
            tv_vote_period=itemView.findViewById(R.id.tv_vote_period);
            btn_lets_vote=itemView.findViewById(R.id.btn_lets_vote);

            btn_lets_vote.setOnClickListener(new View.OnClickListener() {

                //recycler의 item이 클릭되면 실행
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION){
                        Intent intent = new Intent(tv_vote_name, CandidateListActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        intent.putExtra("college", listViewVoteList.get(position).getCollege());
                        intent.putExtra("startDate", listViewVoteList.get(position).getStartDate());
                        intent.putExtra("EndDate", listViewVoteList.get(position).getEndDate());
                        intent.putExtra("UserNumber", UserNumber);
                        intent.putExtra("Userid",Userid);
                        //앞에서 UserNumber 받은 적이 없으니 안됨

                        tv_vote_name.startActivity(intent);
                    }
                }
            });
        }
    }
}

