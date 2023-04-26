package com.example.hw1.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hw1.Adapters.RecordAdapter;
import com.example.hw1.DataManager;
import com.example.hw1.Interfaces.CallBack_SendClick;
import com.example.hw1.MenuActivity;
import com.example.hw1.Models.Record;
import com.example.hw1.R;

import java.util.ArrayList;


public class High_score_table extends Fragment {
    private RecyclerView score_LST_records;
    private CallBack_SendClick callBack_SendClick;


    public void setCallBack(CallBack_SendClick callBack_SendClick) {
        this.callBack_SendClick = callBack_SendClick;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.high_score_table, container, false);
        findViews(view);
        initView();
        return view;
    }

    private void findViews(View view) {
        score_LST_records = view.findViewById(R.id.score_LST_records);
    }


    private void initView() {
        RecordAdapter recordAdapter = new RecordAdapter(DataManager.getRecords());
        score_LST_records.setAdapter(recordAdapter);
        score_LST_records.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void addRecord(int score){
        DataManager.addRecord(score);
    }




}
