package com.shakeup.zumproject;

import com.shakeup.zumproject.model.PlaceDetails;
import com.shakeup.zumproject.model.PlaceResult;

import java.util.ArrayList;

/**
 * Created by Jayson on 6/1/2017.
 */

public interface CustomCallbacks {

    interface PlacesResultsCallback {

        void onPlacesResultsCallback(ArrayList<PlaceResult> places);

        void onPlacesErrorCallback();

    }

    interface PlacesDetailsCallback {

        void onPlaceDetailsCallback(PlaceDetails place);

        void onPlaceDetailsErrorCallback();

    }
}
