package com.example.smartparking;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng vit = new LatLng(12.972546, 79.159574);
        LatLng slot1 = new LatLng(12.972600, 79.159600);
        LatLng slot2 = new LatLng(12.972650, 79.159650);
        LatLng slot3 = new LatLng(12.972700, 79.159700);
        LatLng slot4 = new LatLng(12.972750, 79.159750);
        LatLng slot5 = new LatLng(12.972800, 79.159800);
        LatLng slot6 = new LatLng(12.972850, 79.159850);

        mMap.addMarker(new MarkerOptions().position(vit).title("Smart Parking"));
        mMap.addMarker(new MarkerOptions().position(slot1).title("Slot1"));
        mMap.addMarker(new MarkerOptions().position(slot2).title("Slot 2"));
        mMap.addMarker(new MarkerOptions().position(slot3).title("Slot 3"));
        mMap.addMarker(new MarkerOptions().position(slot4).title("Slot 4"));
        mMap.addMarker(new MarkerOptions().position(slot5).title("Slot 5"));
        mMap.addMarker(new MarkerOptions().position(slot6).title("Slot 6"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(vit));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(vit,20.0f));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(MapsActivity.this,DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}
