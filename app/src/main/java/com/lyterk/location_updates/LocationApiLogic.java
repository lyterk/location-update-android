package com.lyterk.location_updates;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;

public class LocationApiLogic {

    private Bundle savedInstanceState;
    private Ui ui;

    public LocationApiLogic(Bundle bundle, Ui mainUiInstance) {
        this.savedInstanceState = bundle;
        this.ui = mainUiInstance;
    }

    private final static String TAG = "com.lyterk.location_updates.LocationApiLogic";

    // For updating from bundle
    protected final static String REQUESTING_LOCATION_UPDATES_KEY = "requesting-location-updates-key";
    protected final static String LOCATION_KEY = "location-key";
    protected final static String LAST_UPDATED_TIME_STRING_KEY = "last-updated-time-string-key";

    // Setting frequency of location updates
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    private GoogleApiClient mGoogleApiClient;

    private Boolean mRequestingLocationUpdates = false;

    protected LocationRequest mLocationRequest;

    protected Location mCurrentLocation;

    protected String mLastUpdateTime;

    protected void updateValuesFromBundle(Bundle savedInstanceState) {
        Log.i(TAG, "Updating values from bundle");
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

            LocationData locationData = new LocationData(mCurrentLocation, mLastUpdateTime);
            Posting posting = new Posting(locationData);
            posting.onClick(mPostingButton);

            updateUI(locationData);
        }
    }
}
