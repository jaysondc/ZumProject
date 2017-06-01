package com.shakeup.zumproject.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shakeup.zumproject.BuildConfig;
import com.shakeup.zumproject.map.MapsContract;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jayson on 5/17/2017.
 * <p>
 * Request Queue Singleton example is here:
 * https://developer.android.com/training/volley/requestqueue.html
 */

public class RequestQueueSingleton {
    private static RequestQueueSingleton mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private Context mCtx;
    private static String LOG_TAG = RequestQueueSingleton.class.getSimpleName();

    private RequestQueueSingleton(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static synchronized RequestQueueSingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new RequestQueueSingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    /**
     * Requests data from the server and parses it into an ArrayList of results for each restaurant.
     * This request is simply searches for sushi restaurants in San Francisco
     */
    public void requestResults(final MapsContract.PlacesResultsCallback placesResultsCallback) {

        // Lat/Long of San Francisco - 37.7577,-122.4376

        // Create a request for data
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("maps.googleapis.com")
                .appendPath("maps")
                .appendPath("api")
                .appendPath("place")
                .appendPath("nearbysearch")
                .appendPath("json")
                .appendQueryParameter("key", BuildConfig.GOOGLE_PLACES_API_KEY)
                .appendQueryParameter("location", "37.7577,-122.4376")
                .appendQueryParameter("keyword", "sushi")
                .appendQueryParameter("rankby", "distance");
        String url = builder.build().toString();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Print the response
                        Log.d(LOG_TAG, "Response Received! Message: " + response);

                        try{
                            JSONObject jsonObj = new JSONObject(response);

                            JSONArray results = jsonObj.getJSONArray("results");

                            // Store guides in an array
                            ArrayList<PlaceResult> resultsArray = new ArrayList<>();

                            for(int i = 0; i<results.length(); i++){
                                // Convert the JSON Object to a PlaceResult object
                                PlaceResult placeResult =
                                        new PlaceResult((JSONObject) results.get(i));
                                resultsArray.add(placeResult);
                            }

                            Log.d(LOG_TAG, "JSON Parsed! Found " + resultsArray.size() + " results!");

                            // Send the parsed data to the presenter
                            placesResultsCallback.onPlacesResultsCallback(resultsArray);

                        } catch(Exception e){
                            Log.d(LOG_TAG, "There was an error parsing the response.");
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(LOG_TAG, "There was a network error.");

                // Let the calling presenter know there was a network error
                placesResultsCallback.onPlacesErrorCallback();
            }
        });

        // Add a request to your RequestQueue.
        addToRequestQueue(stringRequest);
    }

}
