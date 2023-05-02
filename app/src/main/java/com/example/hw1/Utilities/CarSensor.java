package com.example.hw1.Utilities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.hw1.Interfaces.StepCallback;

public class CarSensor {
    private Sensor sensor;
    private SensorManager sensorManager;

    private StepCallback stepCallback;
    private float currentX, currentY, currentZ;
    private float startX, startY, startZ;
    private long timestamp = 0;

    private int stepRight = 1, stepLeft = 0, stepCounterY = 0, stepCounterX =0;


    private SensorEventListener sensorEventListener;

    public CarSensor(Context context, StepCallback stepCallback) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        startX = startY = startZ = 0.0f;
        this.stepCallback = stepCallback;
        initEventListener();
    }

    private void initEventListener() {
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[1];

                if (startX == 0.0f && startY == 0.0f && startZ == 0.0f) {
                    startX = x;
                    startY = y;
                    startZ = z;
                }

                calculateStep(x, y);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // pass
            }
        };
    }

    private void calculateStep(float x, float y) {
        if (System.currentTimeMillis() - timestamp > 500) {
            timestamp = System.currentTimeMillis();
            if (x - startX < -1.5) {
                stepRight = 1;
                stepLeft = 0;
                stepCallback.stepX();
            }
            if (x - startX > 1.5) {
                stepRight = 0;
                stepLeft = 1;
                stepCallback.stepX();
            }
            if (y - startY > 4.0) {
                stepCounterY++;
                stepCallback.stepZ();
            }
            if (startY - y > 4.0) {
                stepCounterY++;
                stepCallback.stepZ();
            }
        }
    }

    public int getStepsY() {
        return this.stepCounterY;
    }

    public int getStepsX() {
        return this.stepCounterX;
    }

    public int getStepRight() {
        return this.stepRight;
    }

    public int getStepLeft() {
        return this.stepLeft;
    }

    public void start() {
        sensorManager.registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    public void stop() {
        sensorManager.unregisterListener(
                sensorEventListener,
                sensor
        );
    }

}
