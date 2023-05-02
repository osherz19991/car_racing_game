package com.example.hw1;

import android.util.Log;

import com.example.hw1.Models.Record;
import com.example.hw1.Utilities.MySP;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DataManager {

    private static final String KEY_RECORDS = "records";

    private static final  int MAX_RECORDS = 10;

    public static ArrayList<Record> records = new ArrayList<>();


    public static void addRecord(int score,double latitude,double longitude) {
        Record record = new Record(score,latitude, longitude);
        Log.d("one",score+"  " + latitude + " " + longitude );
        records.add(record);
        Collections.sort(records, ((r1, r2) -> (int) (r2.getScore() - r1.getScore())));
        if (records.size() > MAX_RECORDS) {
            records.remove(records.size() - 1);
        }
        saveRecords();
    }

    public static ArrayList<Record> getRecords(){
  //      loadRecords();
        return records;
    }

    private static void saveRecords() {
        String recordsJson = new Gson().toJson(records);
        MySP.getInstance().putString(KEY_RECORDS, recordsJson);
    }

    public static void loadRecords() {
        String recordsJson = MySP.getInstance().getString(KEY_RECORDS, "");
        if (!recordsJson.isEmpty()) {
            Gson gson = new Gson();
            Record[] recordsArray = gson.fromJson(recordsJson, Record[].class);
            records = new ArrayList<>(Arrays.asList(recordsArray));
        }
    }
}





