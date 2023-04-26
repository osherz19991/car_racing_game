package com.example.hw1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hw1.Fragments.High_score_table;
import com.example.hw1.Fragments.MapFragment;

public class LeaderboardsActivity extends AppCompatActivity {


    public static final String KEY_SCORE = "KEY_SCORE";
    private static High_score_table high_score_table;
    private MapFragment mapFragment;

    private Button tier_BTN_button;



    private void showUserLocation(String name) {
        mapFragment.zoomOnUser(name);
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_tier);
        initFragments();
        findViews();
        returnToMenu();
        Intent prevIntent = getIntent();
        int score = prevIntent.getIntExtra(KEY_SCORE,0);
        if(score!=0)
            high_score_table.addRecord(score);
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
        mapFragment = new MapFragment();
    }
    private void beginTransactions() {
        getSupportFragmentManager().beginTransaction().add(R.id.tier_FRAME_list, high_score_table).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.tier_FRAME_map, mapFragment).commit();
    }
}
