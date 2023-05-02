package com.example.hw1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hw1.Interfaces.StepCallback;
import com.example.hw1.Logic.GameManager;
import com.example.hw1.Utilities.CarSensor;
import com.example.hw1.Utilities.SignalGenerator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity  {

    public int DELAY;

    private CarSensor carSensor;
    private ShapeableImageView[] main_IMG_hearts;
    private MediaPlayer mediaPlayer;
    private ImageView[][] main_IMG_lanes;
    private FloatingActionButton main_FAB_right;
    private FloatingActionButton main_FAB_left;
    private TextView odometerTxt;
    private int lastStoneLane = 0;
    private int carLane = 3;
    private int lane_of_obstacle = 9;
    private int coinLane = 7;
    private GameManager gameManager;
    private  Toast toast = null;
    private int newObject = 2;
    private final Handler handler1 = new Handler();
    boolean isCoin = false;
    private boolean useSensors;



    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler1.postDelayed(this,DELAY);
            for(int i = 0;i<5;i++){
                updateObstacleUI(main_IMG_lanes[i], i+1);
            }
            if(newObject == 2)
                createNewObstacle();
            newObject++;
            updateDistance(10);
        }
    };

    private void updateObstacleUI(@NonNull ImageView[] lane, int laneNumber) {
        int i;
        if(lane[lane.length-2].getVisibility() == View.VISIBLE) {
            lane[lane.length - 2].setVisibility(View.INVISIBLE);
            if(lane[lane.length-2].getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.rock).getConstantState())){
                lastStoneLane = 0;
            }else {
                coinLane = 0;
            }
        }


        for(i=0;i<lane.length-2;i++)
        {
            if(lane[i].getVisibility() == View.VISIBLE) {
                lane[i].setVisibility(View.INVISIBLE);
                i++;
                if(lane[i-1].getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.rock).getConstantState())) {
                    lane[i].setImageResource(R.drawable.rock);
                    lane[i].setVisibility(View.VISIBLE);
                    if(i==lane.length-2){
                        lastStoneLane = laneNumber;
                        areViewOverLapping();
                    }
                }else{
                    lane[i].setImageResource(R.drawable.coin_icon);
                    lane[i].setVisibility(View.VISIBLE);
                    if(i==lane.length-2){
                        coinLane = laneNumber;
                        Log.d("coin carlane: updateObstacleUI:",carLane +"carlane");
                        Log.d("coin coinLane: updateObstacleUI:",coinLane +"coinLane");
                        areViewOverLapping();
                    }
                }}}
    }

    private void createNewObstacle(){
        isCoin = gameManager.isCoin();

        int new_lane = gameManager.newObstacle();

        if(!isCoin) {
            while (new_lane == lane_of_obstacle)
                new_lane = gameManager.newObstacle();

            //lane_of_obstacle = new_lane;
            main_IMG_lanes[new_lane-1][0].setImageResource(R.drawable.rock);
            main_IMG_lanes[new_lane-1][0].setVisibility(View.VISIBLE);
        }else{
            while (new_lane == coinLane)
                new_lane = gameManager.newObstacle();

            //coinLane = new_lane;
            Log.d("coin createNewObstacle:",coinLane + "coinLane");
            main_IMG_lanes[new_lane-1][0].setImageResource(R.drawable.coin_icon);
            main_IMG_lanes[new_lane-1][0].setVisibility(View.VISIBLE);
        }

        newObject = 0;
    }

    private void refreshUI(){
        if(gameManager.getCrash() != 0 && gameManager.getCrash()<=3)
            main_IMG_hearts[main_IMG_hearts.length - gameManager.getCrash()].setVisibility(View.INVISIBLE);
        if(gameManager.isGameEnded())
            openScoreScreen(gameManager.getDistance());
    }

    private void openScoreScreen(int score) {
        Intent scoreIntent = new Intent(this, LeaderboardsActivity.class);
        scoreIntent.putExtra(LeaderboardsActivity.KEY_SCORE, score);
        startActivity(scoreIntent);
        finish();
    }


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lanes);
        findViews();
        mediaPlayer = MediaPlayer.create(this,R.raw.crash_sound);
        DELAY = getIntent().getIntExtra("speed",0);
        useSensors = getIntent().getBooleanExtra("useSensors", true);
            if (useSensors) {
                sensors();
            } else {
                main_FAB_left.setVisibility(View.VISIBLE);
                main_FAB_right.setVisibility(View.VISIBLE);
            }
        SignalGenerator.init(this);
        gameManager = new GameManager(main_IMG_hearts.length);
        moveCarClickListeners();
        }

    private void sensors(){
        main_FAB_left.setVisibility(View.GONE);
        main_FAB_right.setVisibility(View.GONE);
        carSensor = new CarSensor(this, new StepCallback() {
            @Override
            public void stepX() {
                if (carSensor.getStepRight() == 1) {
                    moveCarRight();
                } else if (carSensor.getStepLeft() == 1) {
                    moveCarLeft();
                }
            }
            @Override
            public void stepY() {

            }
            @Override
            public void stepZ() {

            }
        });
    }
    private void moveCarLeft() {
        if (carLane > 1) {
            main_IMG_lanes[carLane-1][main_IMG_lanes[carLane-1].length-1].setVisibility(View.INVISIBLE);
            main_IMG_lanes[carLane-2][main_IMG_lanes[carLane-2].length-1].setImageResource(R.drawable.car_topview);
            main_IMG_lanes[carLane-2][main_IMG_lanes[carLane-2].length-1].setVisibility(View.VISIBLE);
            carLane--;
            Log.d("coin carlane: moveCarLeft:",carLane +"carLane");
            Log.d("coin coinLane: moveCarLeft:",coinLane +"coinLane");
            areViewOverLapping();
        }
    }

    private void moveCarRight() {
        if (carLane < 5) {
            main_IMG_lanes[carLane-1][main_IMG_lanes[carLane-1].length-1].setVisibility(View.INVISIBLE);
            main_IMG_lanes[carLane][main_IMG_lanes[carLane].length-1].setImageResource(R.drawable.car_topview);
            main_IMG_lanes[carLane][main_IMG_lanes[carLane].length-1].setVisibility(View.VISIBLE);
            carLane++;
            Log.d("coin carlane: moveCarRight:",carLane +"carlane");
            Log.d(" coin coinLane: moveCarRight:",coinLane +"coinLane");

            areViewOverLapping();
        }
    }


    protected void onResume() {
        super.onResume();
        handler1.postDelayed(runnable, DELAY);
        if (carSensor != null) {
            carSensor.start();
        }    }

    private void moveCarClickListeners() {
        main_FAB_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               moveCarRight();
            }
        });
        main_FAB_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               moveCarLeft();
            }
        });
    }

    protected void onPause () {
        super.onPause();
        handler1.removeCallbacks(runnable);
        if (toast != null) {
            toast.cancel();
        }
        if (carSensor != null) {
            carSensor.stop();
        }
    }
    protected void onStop () {
        super.onStop();
        handler1.removeCallbacks(runnable);
        if (toast != null) {
            toast.cancel();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    protected void onRestart() {
        super.onRestart();
    }

    private void areViewOverLapping(){
            if(coinLane == carLane){
                gameManager.addedDistance(50);
                Log.d("coin encauter","lapping");
                SignalGenerator.vibrate(500);
                refreshUI();
                coinLane = 0;
            }
            if(lastStoneLane == carLane) {
                gameManager.isCrashed(getApplicationContext());
                refreshUI();
                lastStoneLane = 0;
                this.mediaPlayer.start();
            }
    }

    private void updateDistance(int addDistance){
        gameManager.addedDistance(addDistance);
        String distanceStr = String.format("%d km", gameManager.getDistance());
        odometerTxt.setText(distanceStr);
    }



    private void findViews() {
        odometerTxt = findViewById(R.id.main_TXT_odometer);
        main_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)};
        main_FAB_right = findViewById(R.id.main_BTN_right_arrow);
        main_FAB_left  = findViewById(R.id.main_BTN_left_arrow);
        main_IMG_lanes = new ImageView[5][9];
        main_IMG_lanes[0] = new ImageView[]{
                findViewById(R.id.main_IMG_lane_1_1),
                findViewById(R.id.main_IMG_lane_1_2),
                findViewById(R.id.main_IMG_lane_1_3),
                findViewById(R.id.main_IMG_lane_1_4),
                findViewById(R.id.main_IMG_lane_1_5),
                findViewById(R.id.main_IMG_lane_1_6),
                findViewById(R.id.main_IMG_lane_1_7),
                findViewById(R.id.main_IMG_lane_1_8),
                findViewById(R.id.main_IMG_lane_1_9)
        };
        main_IMG_lanes[1] = new ImageView[]{
                findViewById(R.id.main_IMG_lane_2_1),
                findViewById(R.id.main_IMG_lane_2_2),
                findViewById(R.id.main_IMG_lane_2_3),
                findViewById(R.id.main_IMG_lane_2_4),
                findViewById(R.id.main_IMG_lane_2_5),
                findViewById(R.id.main_IMG_lane_2_6),
                findViewById(R.id.main_IMG_lane_2_7),
                findViewById(R.id.main_IMG_lane_2_8),
                findViewById(R.id.main_IMG_lane_2_9)
        };
        main_IMG_lanes[2] = new ImageView[]{
                findViewById(R.id.main_IMG_lane_3_1),
                findViewById(R.id.main_IMG_lane_3_2),
                findViewById(R.id.main_IMG_lane_3_3),
                findViewById(R.id.main_IMG_lane_3_4),
                findViewById(R.id.main_IMG_lane_3_5),
                findViewById(R.id.main_IMG_lane_3_6),
                findViewById(R.id.main_IMG_lane_3_7),
                findViewById(R.id.main_IMG_lane_3_8),
                findViewById(R.id.main_IMG_lane_3_9)
        };
        main_IMG_lanes[3] = new ImageView[]{
                findViewById(R.id.main_IMG_lane_4_1),
                findViewById(R.id.main_IMG_lane_4_2),
                findViewById(R.id.main_IMG_lane_4_3),
                findViewById(R.id.main_IMG_lane_4_4),
                findViewById(R.id.main_IMG_lane_4_5),
                findViewById(R.id.main_IMG_lane_4_6),
                findViewById(R.id.main_IMG_lane_4_7),
                findViewById(R.id.main_IMG_lane_4_8),
                findViewById(R.id.main_IMG_lane_4_9)
        };
        main_IMG_lanes[4] = new ImageView[]{
                findViewById(R.id.main_IMG_lane_5_1),
                findViewById(R.id.main_IMG_lane_5_2),
                findViewById(R.id.main_IMG_lane_5_3),
                findViewById(R.id.main_IMG_lane_5_4),
                findViewById(R.id.main_IMG_lane_5_5),
                findViewById(R.id.main_IMG_lane_5_6),
                findViewById(R.id.main_IMG_lane_5_7),
                findViewById(R.id.main_IMG_lane_5_8),
                findViewById(R.id.main_IMG_lane_5_9)
        };
        }


}