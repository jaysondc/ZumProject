package com.shakeup.zumproject.map;

import android.support.annotation.NonNull;

import com.shakeup.zumproject.CustomCallbacks;
import com.shakeup.zumproject.model.PlaceDetails;
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
        CustomCallbacks.PlacesResultsCallback,
        CustomCallbacks.PlacesDetailsCallback{

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

    /**
     * Load the detail data
     * @param id ID of the place that was clicked
     */
    @Override
    public void onMarkerClick(String id) {
        // Launch the request for Places data
        RequestQueueSingleton rq =
                RequestQueueSingleton.getInstance(mMapView.getMapContext());

        rq.requestDetails(this);
    }

    @Override
    public void onPlacesResultsCallback(ArrayList<PlaceResult> places) {
        mMapView.placeMapMarkers(places);
    }

    @Override
    public void onPlacesErrorCallback() {
        // TODO: Show the user a network error
    }

    @Override
    public void onPlaceDetailsCallback(PlaceDetails place) {
        mMapView.loadPlaceDetails(place);
    }

    @Override
    public void onPlaceDetailsErrorCallback() {
        // TODO: Show the user a network error
    }
}
