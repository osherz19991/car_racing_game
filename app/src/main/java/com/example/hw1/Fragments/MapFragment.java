package com.example.hw1.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.hw1.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap googleMap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.score_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment)  getChildFragmentManager().findFragmentById(R.id.map_frag_container);
        mapFragment.getMapAsync(this);
        return view;
    }

    public void zoomOnUser(double latitude,double longitude) {
        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15f));
        LatLng userLocation = new LatLng(latitude, longitude);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f));

        MarkerOptions markerOptions = new MarkerOptions()
                .position(userLocation)
                .title("User Location");
        googleMap.addMarker(markerOptions);
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
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        googleMap = null;
    }


}
