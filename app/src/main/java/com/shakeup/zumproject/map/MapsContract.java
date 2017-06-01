package com.shakeup.zumproject.map;

import android.content.Context;

import com.shakeup.zumproject.model.PlaceDetails;
import com.shakeup.zumproject.model.PlaceResult;

import java.util.ArrayList;

/**
 * Created by Jayson on 6/1/2017.
 *
 * Specifies the interface between the MapActivity and MapPresenter
 */

public interface MapsContract {

    interface View{
        void placeMapMarkers(ArrayList<PlaceResult> places);

        void loadPlaceDetails(PlaceDetails place);

        void showLoading();

        void hideLoading();

        Context getMapContext();
    }

    interface Presenter{
        void onMapReady();

        void onMarkerClick(String id);
    }

}
