package com.lyterk.location_updates;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;

public class LocationController
    implements ConnectionCallbacks,
               OnConnectionFailedListener,
               LocationListener {

    private final static String TAG = "com.lyterk.location_updates.LocationController";
    
    private Ui ui;

    // For updating from bundle
    protected final static String REQUESTING_LOCATION_UPDATES_KEY = "requesting-location-updates-key";
    protected final static String LOCATION_KEY = "location-key";
    protected final static String LAST_UPDATED_TIME_STRING_KEY = "last-updated-time-string-key";

    // Setting frequency of location updates
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    
    public GoogleApiClient mGoogleApiClient;
    private Boolean mRequestingLocationUpdates;
    protected LocationRequest mLocationRequest;
    protected Location mCurrentLocation;
    protected String mLastUpdateTime;
    private Posting mPosting;
    private Context ctx;
    private Bundle savedInstanceState;
    private Dao dao;

    public LocationController(Bundle bundle, Ui ui, Dao dao, Context ctx) {
        this.savedInstanceState = bundle;
        this.ui = ui;
        this.ctx = ctx;
        this.dao = dao;

        updateValuesFromBundle(savedInstanceState);
    }

    protected void updateValuesFromBundle(Bundle savedInstanceState) {        
        if (savedInstanceState != null) {
            if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(
                        REQUESTING_LOCATION_UPDATES_KEY);
                ui.setButtonsEnabledState();
            }

            if (savedInstanceState.keySet().contains(LOCATION_KEY)) {
                // Since LOCATION_KEY was found in the Bundle, we can be sure that mCurrentLocation
                // is not null.
                mCurrentLocation = savedInstanceState.getParcelable(LOCATION_KEY);
            }

            if (savedInstanceState.keySet().contains(LAST_UPDATED_TIME_STRING_KEY)) {
                mLastUpdateTime = savedInstanceState.getString(LAST_UPDATED_TIME_STRING_KEY);
            }

            LocationData ld = new LocationData(mCurrentLocation);
            dao.insertLocation(ld);

            mPosting = new Posting(ld);
            // mPosting.onClick(mPostingButton);
        }
    }

    protected synchronized void buildGoogleApiClient(Context ctx) {
        mGoogleApiClient = new GoogleApiClient.Builder(ctx)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public void startLocationUpdates() {
        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    public void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }
    
    @Override
    public void onLocationChanged(Location location) {
        mLocationData = new LocationData(location);

        ui.updateUI(mLocationData);
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(TAG, "Connected to GoogleApiClient");
        if (mCurrentLocation == null) {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        } else {
            LocationData ld = new LocationData(mCurrentLocation);
            dao.insertLocation(ld);
            // mPosting = new Posting(mLocationData);
            ui.updateUI(ld);
        }
        mRequestingLocationUpdates = ui.getRequestingLocationUpdates();
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    public void clickHandler(View v) {

        switch (v.getId()) {
            case R.id.start_updates_button:
                startUpdateClick();
                break;
            case R.id.stop_updates_button:
                stopUpdateClick();
                break;
            case R.id.post_button:
                if (mPosting != null) {
                    mPosting.postingClick();
                }
                else {
                    Log.d(TAG, "No connection, Posting class is null");
                }
                break;
        }
    }

    public void startUpdateClick() {
        mRequestingLocationUpdates = ui.getRequestingLocationUpdates();
        if (!mRequestingLocationUpdates) {
            ui.setRequestingLocationUpdates(true);
            ui.setButtonsEnabledState();
            startLocationUpdates();
        }
    }

    public void stopUpdateClick() {
        mRequestingLocationUpdates = ui.getRequestingLocationUpdates();
        if (mRequestingLocationUpdates) {
            ui.setRequestingLocationUpdates(false);
            mRequestingLocationUpdates = ui.getRequestingLocationUpdates();
            Log.d(TAG, "Requesting location updates is " + mRequestingLocationUpdates.toString());
            ui.setButtonsEnabledState();
            stopLocationUpdates();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }
}
