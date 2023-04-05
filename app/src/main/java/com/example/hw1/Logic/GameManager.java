package com.example.hw1.Logic;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hw1.R;

public class GameManager  {

    private int life;

    private int crash;


    public GameManager(int life) {
        this.life = life;
        this.crash = 0;
    }

    public int getLife() {
        return life;
    }

    public int getCrash() {
        return crash;
    }

    public boolean isLose(){
        return life == crash;
    }

    public void isCrashed(Context context, Vibrator v,Toast toast){
        toast = Toast.makeText(context,"crash!!!",Toast.LENGTH_LONG);
        toast.show();
        crash++;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }

}
