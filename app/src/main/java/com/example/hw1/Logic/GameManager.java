package com.example.hw1.Logic;

import android.content.Context;

import android.widget.Toast;

import com.example.hw1.Utilities.SignalGenerator;

import java.util.Random;

public class GameManager  {

    private int life;
    private int distance;
    private int crash;

    private Random random;

    public GameManager(int life) {
        this.life = life;
        this.crash = 0;
        this.distance = 0;
        this.random = new Random();
    }
    public int getDistance(){
        return this.distance;
    }

    public void addedDistance(int addedDistance){
        this.distance += addedDistance;
    }

    public int getLife() {
        return life;
    }

    public int getCrash() {
        return crash;
    }

    public boolean isGameEnded(){
        return life == crash;
    }

    public void isCrashed(Context context){
        SignalGenerator.getInstance().toast("crash!!!!",Toast.LENGTH_SHORT);
        crash++;
        SignalGenerator.vibrate(500);
    }

    public int newObstacle(){
        int new_lane = random.nextInt(5);
        return new_lane+1;
    }

    public boolean isCoin(){
        if(random.nextDouble()<0.2){
            return true;
        }
        return false;
    }

}
