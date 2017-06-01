package com.shakeup.zumproject.model;

import android.net.Uri;
import android.util.Log;

import com.shakeup.zumproject.BuildConfig;

import org.json.JSONObject;

/**
 * Created by Jayson on 5/31/2017.
 *
 * PlaceDegtails is a java object containing a single Place returned by the
 * Google Places Details API
 */

public class PlaceDetails {

    private final String LOG_TAG = this.getClass().getSimpleName();

    /*
     * Required Parameters
     * Name
     * Formatted Address
     * Phone/Email
     * Photo
     * Rating
     * List of Reviews
     */
    private String id;
    private String name;
    private String address;
    private String phone;
    // Single photo. Would be an ArrayList if I had more time
    private String photoUrl;
    // Reviews placeholder. I don't have time fo integrate reviews
    private double rating;

    // Public constructor to map a Song JSONObject to our fields
    PlaceDetails(JSONObject result) {
        try {
            this.id = result.getString("place_id");
            this.name = result.getString("name");
            this.address = result.getString("formatted_address");
            this.phone = result.getString("formatted_phone_number");
            this.rating = result.getDouble("rating");
            String photoReference = result.getJSONArray("photos")
                    .getJSONObject(0)
                    .getString("photo_reference");
            // Format URL
            // Photo url example
            // https://maps.googleapis.com/maps/api/place/photo?parameters
            // This URL works but the returned image indicated I'm past my API quota
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https")
                    .authority("maps.googleapis.com")
                    .appendPath("maps")
                    .appendPath("api")
                    .appendPath("place")
                    .appendPath("photo")
                    .appendQueryParameter("key", BuildConfig.GOOGLE_PLACES_API_KEY)
                    .appendQueryParameter("photoreference", photoReference)
                    .appendQueryParameter("maxWidth", "600");
            // This URL works but the returned image indicated I'm past my API quota
            this.photoUrl =  builder.build().toString();

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

    public String getPhone() {
        return phone;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public double getRating() {
        return rating;
    }
}
