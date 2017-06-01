package com.shakeup.zumproject.map;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.shakeup.zumproject.R;
import com.shakeup.zumproject.model.PlaceResult;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity
        implements OnMapReadyCallback,
        MapsContract.View{

    private MapsContract.Presenter mMapPresenter;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        // Create our presenter
        mMapPresenter = new MapsPresenter(this);

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
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        // Let the presenter know the map is ready
        mMapPresenter.onMapReady();
    }

    /**
     * Place markers on the map for all received places
     * @param places a list of PlaceResult objects
     */
    @Override
    public void placeMapMarkers(ArrayList<PlaceResult> places) {

        LatLng initial = new LatLng(
                places.get(0).getLat(),
                places.get(0).getLng()
        );

        for (PlaceResult place : places) {
            double lat = place.getLat();
            double lng = place.getLng();
            LatLng marker = new LatLng(lat, lng);

            mMap.addMarker(new MarkerOptions().position(marker)
            .title(place.getName()));
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(initial));

    }

    /**
     * Show the loading icon
     */
    @Override
    public void showLoading() {

    }

    /**
     * Hide the loading icon
     */
    @Override
    public void hideLoading() {

    }

    /**
     * Returns the Activity's Context
     * @return the Context of the activity
     */
    @Override
    public Context getMapContext() {
        return getApplicationContext();
    }
}
