//
// Name                 Ivo Ivanov
// Student ID           S1504801
// Programme of Study   Games Programing(Software Development)

package com.example.ivo_ivanov_s1504801_mpd;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String Location;
    float Magnitude;
    int Depth;
    float Long;
    float Lat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent intent = getIntent();
        Location = intent.getStringExtra("Location");
        Magnitude = intent.getFloatExtra("Magnitude", 0);
        Depth = intent.getIntExtra("Depth", 0);
        Long = intent.getFloatExtra("Long", 0);
        Lat = intent.getFloatExtra("Lat", 0);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(Lat, Long);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Location: " + Location + " Magnitude: " + Magnitude + " Depth: " + Depth));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
