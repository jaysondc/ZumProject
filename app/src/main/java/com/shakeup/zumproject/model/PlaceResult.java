package com.shakeup.zumproject.model;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by Jayson on 5/31/2017.
 *
 * PlaceResult is a java object containing a single Place result returned by the
 * Google Places Search API
 */

public class PlaceResult {

    private final String LOG_TAG = this.getClass().getSimpleName();

    private String id;
    private String name;
    private String address;
    private double lat;
    private double lng;
    private double rating;

    // Public constructor to map a Song JSONObject to our fields
    PlaceResult(JSONObject result) {
        try {
            this.id = result.getString("place_id");
            this.name = result.getString("name");
            this.address = result.getString("vicinity");
            this.rating = result.getDouble("rating");
            this.lat = result.getJSONObject("geometry")
                    .getJSONObject("location")
                    .getDouble("lat");
            this.lng = result.getJSONObject("geometry")
                    .getJSONObject("location")
                    .getDouble("lng");

        } catch (Exception e) {
            Log.d(LOG_TAG, "There was an error parsing the Song JSONObject.");
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public double getRating() {
        return rating;
    }
}
