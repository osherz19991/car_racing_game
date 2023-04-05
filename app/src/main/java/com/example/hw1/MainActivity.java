package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hw1.Logic.GameManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public final int DELAY = 1000;

    private ShapeableImageView[] main_IMG_hearts;
    private ImageView[] main_IMG_lane_1;
    private ImageView[] main_IMG_lane_2;
    private ImageView[] main_IMG_lane_3;
    private FloatingActionButton main_FAB_right;
    private FloatingActionButton main_FAB_left;

    private int lastStoneLane = 0;
    private int carLane = 2;
    private final Random random = new Random();

    private int lane_of_obstacle = random.nextInt(3);
    private GameManager gameManager;
    private  Toast toast = null;
    private int newObject = 2;
    private final Handler handler1 = new Handler();



    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler1.postDelayed(this,DELAY);
            updateObstacleUI(main_IMG_lane_1, 1);
            updateObstacleUI(main_IMG_lane_2, 2);
            updateObstacleUI(main_IMG_lane_3, 3);
            if(newObject == 2)
                createNewObstacle();
            newObject++;
        }
    };


    private void updateObstacleUI(ImageView[] lane,int laneNumber) {
        int i;
        if(lane[lane.length-2].getVisibility() == View.VISIBLE){
            lane[lane.length-2].setVisibility(View.INVISIBLE);
            areViewOverLapping();
            lastStoneLane = 0;
        }
        for(i=0;i<lane.length-2;i++)
        {
            if(lane[i].getVisibility() == View.VISIBLE) {
                lane[i].setVisibility(View.INVISIBLE);
                i++;
                lane[i].setVisibility(View.VISIBLE);
                if(i==lane.length-2)
                    lastStoneLane = laneNumber;
            }}
    }

    private void createNewObstacle(){
        int new_lane = random.nextInt(3);

        while(new_lane == lane_of_obstacle)
            new_lane = random.nextInt(3);

        lane_of_obstacle = new_lane;

        if(lane_of_obstacle == 0)
            main_IMG_lane_1[0].setVisibility(View.VISIBLE);
        if(lane_of_obstacle == 1)
            main_IMG_lane_2[0].setVisibility(View.VISIBLE);
        if(lane_of_obstacle == 2)
            main_IMG_lane_3[0].setVisibility(View.VISIBLE);
        newObject = 0;
    }

    private void refreshUI(){
        if(gameManager.getCrash() != 0 && gameManager.getCrash()<=3)
            main_IMG_hearts[main_IMG_hearts.length - gameManager.getCrash()].setVisibility(View.INVISIBLE);
    }


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lanes);

        findViews();
        start();
        gameManager = new GameManager(main_IMG_hearts.length);

        moveCarClickListeners();
    }

    private void start() {
        handler1.postDelayed(runnable,DELAY);
    }

    private void moveCarClickListeners() {
        main_FAB_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(carLane == 1) {
                    main_IMG_lane_1[main_IMG_lane_1.length-1].setVisibility(View.INVISIBLE);
                    main_IMG_lane_2[main_IMG_lane_2.length-1].setImageResource(R.drawable.car_topview);
                    main_IMG_lane_2[main_IMG_lane_2.length-1].setVisibility(View.VISIBLE);
                    carLane = 2;
                    areViewOverLapping();
                }else if(carLane == 2) {
                    main_IMG_lane_2[main_IMG_lane_2.length-1].setVisibility(View.INVISIBLE);
                    main_IMG_lane_3[main_IMG_lane_3.length-1].setImageResource(R.drawable.car_topview);
                    main_IMG_lane_3[main_IMG_lane_3.length-1].setVisibility(View.VISIBLE);
                    carLane = 3;
                    areViewOverLapping();
                }

            }
        });
        main_FAB_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(carLane == 3) {
                    main_IMG_lane_3[main_IMG_lane_3.length-1].setVisibility(View.INVISIBLE);
                    main_IMG_lane_2[main_IMG_lane_2.length-1].setImageResource(R.drawable.car_topview);
                    main_IMG_lane_2[main_IMG_lane_2.length-1].setVisibility(View.VISIBLE);
                    carLane = 2;
                    areViewOverLapping();
                } else if(carLane == 2) {
                    main_IMG_lane_2[main_IMG_lane_2.length-1].setVisibility(View.INVISIBLE);
                    main_IMG_lane_1[main_IMG_lane_1.length-1].setImageResource(R.drawable.car_topview);
                    main_IMG_lane_1[main_IMG_lane_1.length-1].setVisibility(View.VISIBLE);
                    carLane = 1;
                    areViewOverLapping();
                }
            }
        });
    }

    protected void onStop () {
        super.onStop();
        handler1.removeCallbacks(runnable);
        if (toast != null) {
            toast.cancel();
        }
    }

    private boolean areViewOverLapping(){
            if(lastStoneLane == carLane) {
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                gameManager.isCrashed(getApplicationContext() ,v, toast);
                refreshUI();
                return true;
            }
            return false;
    }

    private void findViews() {
        main_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)};
        main_FAB_right = findViewById(R.id.main_BTN_right_arrow);
        main_FAB_left  = findViewById(R.id.main_BTN_left_arrow);
        main_IMG_lane_1 = new ImageView[]{
                findViewById(R.id.main_IMG_lane_1_1),
                findViewById(R.id.main_IMG_lane_1_2),
                findViewById(R.id.main_IMG_lane_1_3),
                findViewById(R.id.main_IMG_lane_1_4),
                findViewById(R.id.main_IMG_lane_1_5),
                findViewById(R.id.main_IMG_lane_1_6)
        };
        main_IMG_lane_2 = new ImageView[]{
                findViewById(R.id.main_IMG_lane_2_1),
                findViewById(R.id.main_IMG_lane_2_2),
                findViewById(R.id.main_IMG_lane_2_3),
                findViewById(R.id.main_IMG_lane_2_4),
                findViewById(R.id.main_IMG_lane_2_5),
                findViewById(R.id.main_IMG_lane_2_6)
        };
        main_IMG_lane_3 = new ImageView[]{
                findViewById(R.id.main_IMG_lane_3_1),
                findViewById(R.id.main_IMG_lane_3_2),
                findViewById(R.id.main_IMG_lane_3_3),
                findViewById(R.id.main_IMG_lane_3_4),
                findViewById(R.id.main_IMG_lane_3_5),
                findViewById(R.id.main_IMG_lane_3_6)
        };
        }
}