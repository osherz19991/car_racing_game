package com.example.hw1.Fragments;




import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.hw1.DataManager;
import com.example.hw1.Models.Record;
import com.example.hw1.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private TextView map_LBL_title;
    private GoogleMap googleMap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.score_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment)  getChildFragmentManager().findFragmentById(R.id.map_frag_container);
        mapFragment.getMapAsync(this);
        findViews(view);
        return view;
    }
    private void findViews(View view) {
        map_LBL_title = view.findViewById(R.id.map_LBL_title);
    }

    public void zoomOnUser(double latitude,double longitude) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15f));
        Log.d("four","" +latitude +" " +longitude);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (googleMap != null) {
            googleMap.clear();
            // Add your map markers and other customization here
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Clean up your map resources here
        googleMap = null;
    }

    public void updateLocation(double latitude, double longitude) {
        int lastIndex = DataManager.records.size() - 1;
        Record lastRecord = DataManager.records.get(lastIndex);
        lastRecord.setLatitude(latitude);
        lastRecord.setLongitude(longitude);
        DataManager.records.set(lastIndex,lastRecord);
    }
}
