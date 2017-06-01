package com.shakeup.zumproject.map;

import android.support.annotation.NonNull;

import com.shakeup.zumproject.model.PlaceResult;
import com.shakeup.zumproject.model.RequestQueueSingleton;

import java.util.ArrayList;

/**
 * Created by Jayson on 6/1/2017.
 *
 * Presenter for the MapActivity. Handles user input and interaction with the Model.
 */

public class MapsPresenter
        implements MapsContract.Presenter,
        MapsContract.PlacesResultsCallback{

    private MapsContract.View mMapView;

    /**
     * Presenter constructor receives the fragment to be attached
     *
     * @param mapView is the fragment we're going to control
     */
    public MapsPresenter(
            @NonNull MapsContract.View mapView) {
        mMapView = mapView;
    }

    @Override
    public void onMapReady() {
        // Launch the request for Places data
        RequestQueueSingleton rq =
                RequestQueueSingleton.getInstance(mMapView.getMapContext());

        rq.requestResults(this);
    }

    @Override
    public void onPlacesResultsCallback(ArrayList<PlaceResult> places) {
        mMapView.placeMapMarkers(places);
    }

    @Override
    public void onPlacesErrorCallback() {
        // Show the user a network error
    }
}
