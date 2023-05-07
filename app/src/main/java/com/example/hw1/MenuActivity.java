package com.example.hw1;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;

public class MenuActivity extends AppCompatActivity {

    private MaterialButton main_BTN_fast;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 123;
    private MaterialButton main_BTN_slow;
    private MaterialButton main_BTN_sensors;
    private MaterialButton main_BTN_leaderboards;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private double latitude;
    private double longitude;
    private TextView main_TXT_Title;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        findViews();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            // Permission has been granted
            setupLocationListener();
        } else {
            // Request location permissions
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
        }
    }


    private void setupLocationListener() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                updateLocation(latitude,longitude);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (lastKnownLocation != null) {
            double latitude = lastKnownLocation.getLatitude();
            double longitude = lastKnownLocation.getLongitude();
            updateLocation(latitude,longitude);
        }

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


    @Override
    protected void onResume() {
        super.onResume();
        if(locationListener !=  null) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                setupLocationListener();
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, setup location listener
                    setupLocationListener();
                }
                return;

    }

    protected void onPause() {
        super.onPause();
        if(locationListener !=  null)
            locationManager.removeUpdates(locationListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(locationListener !=  null)
            locationManager.removeUpdates(locationListener);
    }
    private void updateLocation(double latitude,double longitude){
        this.latitude = latitude;
        this.longitude = longitude;

    }

    private void openGame(boolean useSensors,int speed) {
        Intent racingGame = new Intent(this, MainActivity.class);

        racingGame.putExtra("useSensors", useSensors);
        racingGame.putExtra("speed", speed);
        racingGame.putExtra("latitude", this.latitude);
        racingGame.putExtra("longitude", this.longitude);

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
