package com.example.hw1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

public class MenuActivity extends AppCompatActivity {

    private MaterialButton main_BTN_fast;

    private MaterialButton main_BTN_slow;
    private MaterialButton main_BTN_sensors;

    private MaterialButton main_BTN_leaderboards;



    private TextView main_TXT_Title;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        findViews();
        menuBottoms();
    }

    private void menuBottoms() {
        main_BTN_fast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGame(false,500);
            }

        });
        main_BTN_slow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGame(false,1000);
            }

        });
        main_BTN_sensors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {openGame(true,500);}
        });
        main_BTN_leaderboards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLeaderboards();
            }
        });
    }




    private void openGame(boolean useSensors,int speed) {
        Intent racingGame = new Intent(this, MainActivity.class);
        racingGame.putExtra("useSensors", useSensors);
        racingGame.putExtra("speed", speed);
        startActivity(racingGame);
        finish();
    }

    private void openLeaderboards() {
        Intent leaderboards = new Intent(this, LeaderboardsActivity.class);
        startActivity(leaderboards);
        finish();
    }

    private void findViews() {
        main_BTN_fast = findViewById(R.id.main_BTN_fast);
        main_BTN_slow = findViewById(R.id.main_BTN_slow);
        main_BTN_sensors = findViewById(R.id.main_BTN_option2);
        main_BTN_leaderboards = findViewById(R.id.main_BTN_option3);
        main_TXT_Title = findViewById(R.id.main_TXT_title);
    }
}
