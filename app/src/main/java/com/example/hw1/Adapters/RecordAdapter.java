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
import java.util.PrimitiveIterator;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordViewHolder> {


    private ArrayList<Record> records;

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
        holder.record_LBL_name.setText(record.getName());
        holder.record_LBL_score.setText(record.getScore() + "");
    }

    private Record getItem(int position){
        return this.records.get(position);
    }

    @Override
    public int getItemCount() {
        return this.records == null ? 0 : this.records.size();
    }

    public class RecordViewHolder extends  RecyclerView.ViewHolder{
        private TextView record_LBL_name;
        private TextView record_LBL_score;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            record_LBL_name = itemView.findViewById(R.id.record_LBL_name);
            record_LBL_score = itemView.findViewById(R.id.record_LBL_score);
        }
    }
}
