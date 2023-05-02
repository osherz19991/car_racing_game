package com.example.hw1.Models;

public class Record {

    private double score;
    private double longitude;
    private double latitude;


    public Record(double score, double latitude, double longitude) {
        this.score = score;
        this.latitude = latitude;
        this.longitude= longitude;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getScore() {
        return score;
    }
}
