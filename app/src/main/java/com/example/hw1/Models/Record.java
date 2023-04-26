package com.example.hw1.Models;

public class Record {

    private String name;
    private double score;
    private double longitude;
    private double latitude;


    public Record(String name, double score, double latitude, double longitude) {
        this.name = name;
        this.score = score;
        this.latitude = latitude;
        this.longitude= longitude;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

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
