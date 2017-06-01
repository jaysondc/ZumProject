package com.shakeup.zumproject.model;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by Jayson on 5/31/2017.
 */

public class PlaceResult {

    private final String LOG_TAG = this.getClass().getSimpleName();

    private String id;
    private String name;
    private String address;
    private String lat;
    private String lng;
    private double rating;

    // Public constructor to map a Song JSONObject to our fields
    public PlaceResult(JSONObject result) {
        try {
            this.id = result.getString("id");
            this.name = result.getString("name");
            this.address = result.getString("vicinity");
            this.rating = result.getDouble("rating");
            this.lat = result.getJSONObject("geometry")
                    .getJSONObject("location")
                    .getString("lat");
            this.lng = result.getJSONObject("geometry")
                    .getJSONObject("location")
                    .getString("lng");

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

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public double getRating() {
        return rating;
    }
}
