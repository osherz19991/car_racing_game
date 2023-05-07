package com.example.hw1;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.example.hw1.Fragments.High_score_table;
import com.example.hw1.Fragments.MapFragment;
import com.example.hw1.Interfaces.CallBack_SendClick;

public class LeaderboardsActivity extends AppCompatActivity {


    public static final String KEY_SCORE = "KEY_SCORE";
    public static final String LATITUDE_KEY = "LATITUDE_KEY";
    public static final String LONGITUDE_KEY = "LONGITUDE_KEY";
    private static High_score_table high_score_table;
    private MapFragment mapFragment;
    private Button tier_BTN_button;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_tier);
        initFragments();
        findViews();
        returnToMenu();
        Intent prevIntent = getIntent();
        int score = prevIntent.getIntExtra(KEY_SCORE,0);
        if(score!=0) {
            high_score_table.addRecord(score, prevIntent.getDoubleExtra(LATITUDE_KEY,0), prevIntent.getDoubleExtra(LONGITUDE_KEY,0));
        }

        beginTransactions();
    }



    private void returnToMenu(){
        tier_BTN_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openMenu();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
    private void openMenu(){
        Intent MenuIntent = new Intent(this,MenuActivity.class);
        startActivity(MenuIntent);
        finish();
    }


    private void findViews(){
        tier_BTN_button = findViewById(R.id.score_BTN_button);
    }
    private void initFragments() {
        high_score_table = new High_score_table();
        high_score_table.setCallBack(callBack_SendClick);
        mapFragment = new MapFragment();
    }
    private void beginTransactions() {
        getSupportFragmentManager().beginTransaction().add(R.id.tier_FRAME_list, high_score_table).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.tier_FRAME_map, mapFragment).commit();
    }

    CallBack_SendClick callBack_SendClick = new CallBack_SendClick() {
        @Override
        public void userLocation(double latitude, double longitude) {
            showUserLocation(latitude,longitude);
        }
    };

    private void showUserLocation(double latitude, double longitude) {
        mapFragment.zoomOnUser(latitude,longitude);
    }
}
