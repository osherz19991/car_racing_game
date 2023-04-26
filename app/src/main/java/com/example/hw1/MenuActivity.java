package com.example.hw1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

public class MenuActivity extends AppCompatActivity {

    private MaterialButton main_BTN_option1;

    private MaterialButton main_BTN_option2;

    private MaterialButton main_BTN_option3;



    private TextView main_TXT_Title;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        findViews();
        menuBottoms();
    }

    private void menuBottoms() {
        main_BTN_option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGame();
            }
        });
        main_BTN_option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLeaderboards();
            }
        });
    }




    private void openGame() {
        Intent racingGame = new Intent(this, MainActivity.class);
        startActivity(racingGame);
        finish();
    }

    private void openLeaderboards() {
        Intent leaderboards = new Intent(this, LeaderboardsActivity.class);
        startActivity(leaderboards);
        finish();
    }

    private void findViews() {
        main_BTN_option1 = findViewById(R.id.main_BTN_option1);
        main_BTN_option2 = findViewById(R.id.main_BTN_option2);
        main_BTN_option3 = findViewById(R.id.main_BTN_option3);
        main_TXT_Title = findViewById(R.id.main_TXT_title);
    }
}
