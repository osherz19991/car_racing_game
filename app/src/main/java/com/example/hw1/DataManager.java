package com.example.hw1;

import android.util.Log;

import com.example.hw1.Models.Record;
import com.example.hw1.Utilities.MySPv3;
import com.google.gson.Gson;
import com.paz.prefy_lib.Prefy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DataManager {

    private static final String KEY_RECORDS = "records";

    private static final  int MAX_RECORDS = 10;

    public static ArrayList<Record> records = new ArrayList<>();


    public static void addRecord(int score) {
        Record record = new Record("osher",score,15, 15);
        Log.d("one",score+"");
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
        MySPv3.getInstance().putString(KEY_RECORDS, recordsJson);
    }

    public static void loadRecords() {
        String recordsJson = MySPv3.getInstance().getString(KEY_RECORDS, "");
        if (!recordsJson.isEmpty()) {
            Gson gson = new Gson();
            Record[] recordsArray = gson.fromJson(recordsJson, Record[].class);
            records = new ArrayList<>(Arrays.asList(recordsArray));
        }
    }
}





