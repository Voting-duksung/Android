package com.example.blockchainproject.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blockchainproject.R;
import com.example.blockchainproject.VoteData;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {

    // adapter에 들어갈 list 입니다.
    private ArrayList<VoteData> listData = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }


    public void addItem(VoteData data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout linearItem;
        private TextView textView1;
        private TextView textView2;
        private ImageView imageView;
        private VoteData data;

        ItemViewHolder(View itemView) {
            super(itemView);

            //linearItem = itemView.findViewById(R.id.linearItem);
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            imageView = itemView.findViewById(R.id.imageView);
        }

        void onBind(VoteData data) {
            this.data = data;

            textView1.setText(data.getTitle());
            textView2.setText(data.getContent());
            imageView.setImageResource(data.getResId());

            itemView.setOnClickListener(this);
            textView1.setOnClickListener(this);
            textView2.setOnClickListener(this);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
//                case R.id.linearItem:
//                    Toast.makeText(v.getContext(), "TITLE : " + data.getTitle() + "\nContent : " + data.getContent(), Toast.LENGTH_SHORT).show();
//                    break;
                case R.id.textView1:
                    Toast.makeText(v.getContext(), data.getTitle(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.textView2:
                    Toast.makeText(v.getContext(), data.getContent(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.imageView:
                    Toast.makeText(v.getContext(), data.getTitle() + " 이미지 입니다.", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
