package com.example.hw1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.hw1.Fragments.High_score_table;
import com.example.hw1.Fragments.MapFragment;
import com.example.hw1.Interfaces.CallBack_SendClick;

public class LeaderboardsActivity extends AppCompatActivity {


    public static final String KEY_SCORE = "KEY_SCORE";
    private static High_score_table high_score_table;
    private MapFragment mapFragment;

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 123;

    private Button tier_BTN_button;

    private LocationManager locationManager;

    private LocationListener locationListener;
    private double latitude;
    private double longitude;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_tier);
        initFragments();
        findViews();
        returnToMenu();

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

        Intent prevIntent = getIntent();
        int score = prevIntent.getIntExtra(KEY_SCORE,0);
        if(score!=0)
            high_score_table.addRecord(score,latitude,longitude);
        Log.d("three",score+"  " + latitude + " " + longitude );

        beginTransactions();
    }

    @SuppressLint("MissingPermission")
    private void setupLocationListener() {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                high_score_table.updateLocation(latitude, longitude);
                mapFragment.updateLocation(latitude, longitude);
                Log.d("two","" +latitude +" " +longitude);
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
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission has been granted
                    setupLocationListener();
                } else {
                    // Permission has been denied
                    Toast.makeText(this, "Location permission is required", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    private void returnToMenu(){
        tier_BTN_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openMenu();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            setupLocationListener();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }
    private void openMenu(){
        Intent MenuIntent = new Intent(this,MenuActivity.class);
        startActivity(MenuIntent);
        finish();
    }


    private void findViews(){
        tier_BTN_button = findViewById(R.id.score_BTN_button);
    }
    private void initFragments() {
        high_score_table = new High_score_table();
        high_score_table.setCallBack(callBack_SendClick);
        mapFragment = new MapFragment();
    }
    private void beginTransactions() {
        getSupportFragmentManager().beginTransaction().add(R.id.tier_FRAME_list, high_score_table).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.tier_FRAME_map, mapFragment).commit();
    }

    CallBack_SendClick callBack_SendClick = new CallBack_SendClick() {
        @Override
        public void userNameChosen(double latitude, double longitude) {
            showUserLocation(latitude,longitude);
        }
    };

    private void showUserLocation(double latitude, double longitude) {
        mapFragment.zoomOnUser(latitude,longitude);
    }
}
