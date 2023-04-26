package com.example.hw1;

import android.app.Application;

import com.example.hw1.Utilities.MySPv3;
import com.example.hw1.Utilities.SignalGenerator;
import com.paz.prefy_lib.Prefy;

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        MySPv3.init(this);
        Prefy.init(this,true);
        SignalGenerator.init(this);
        DataManager.loadRecords();

    }
}
