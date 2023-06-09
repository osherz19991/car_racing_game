package com.example.hw1.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hw1.Models.Record;
import com.example.hw1.R;

import java.util.ArrayList;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordViewHolder> {
    private ArrayList<Record> records;

    private OnItemClickListener listener;
    public RecordAdapter(ArrayList<Record> records){ this.records = records; }




    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_item,parent,false);
        RecordViewHolder recordViewHolder = new RecordViewHolder(view);
        return recordViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        Record record = getItem(position);
        holder.record_LBL_score.setText("score:" + record.getScore());
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public interface OnItemClickListener {
        void onItemClick(Record record);
    }



    private Record getItem(int position){
        return this.records.get(position);
    }

    @Override
    public int getItemCount() {
        return this.records == null ? 0 : this.records.size();
    }

    public class RecordViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView record_LBL_score;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            record_LBL_score = itemView.findViewById(R.id.record_LBL_score);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(records.get(position));
                }
            }
        }
    }
}
