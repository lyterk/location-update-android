package com.lyterk.location_updates;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class MainActivity extends ActionBarActivity {

    protected static final String TAG = "com.lyterk.location_updates.MainActivity";

    protected final static String REQUESTING_LOCATION_UPDATES_KEY = "requesting-location-updates-key";
    protected final static String LOCATION_KEY = "location-key";
    protected final static String LAST_UPDATED_TIME_STRING_KEY = "last-updated-time-string-key";
    private LocationController mController;
    private View view;
    private Ui ui;
    private Boolean mRequestingLocationUpdates;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Ui ui = new Ui(view);

        mController = new LocationController(savedInstanceState, ui);

        mController.buildGoogleApiClient();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mController.mGoogleApiClient.connect();
    }

    @Override
    public void onResume() {
        super.onResume();
        mRequestingLocationUpdates = ui.getRequestingLocationUpdates();
        if (mController.mGoogleApiClient.isConnected()
                && mRequestingLocationUpdates) {
            mController.startLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mController.mGoogleApiClient.isConnected()) {
            mController.stopLocationUpdates();
        }
    }

    @Override
    protected void onStop() {
        mController.mGoogleApiClient.disconnect();
        
        super.onStop();
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        mRequestingLocationUpdates = ui.getRequestingLocationUpdates();
        savedInstanceState.putBoolean
                (REQUESTING_LOCATION_UPDATES_KEY, mRequestingLocationUpdates);
        savedInstanceState.putParcelable(LOCATION_KEY, mController.mCurrentLocation);
        savedInstanceState.putString(LAST_UPDATED_TIME_STRING_KEY, mController.mLastUpdateTime);
        super.onSaveInstanceState(savedInstanceState);
    }


    public void stopUpdatesButtonHandler(View view) {

    }

    public void onClick(View view) {
        mController.clickHandler(view);
    }
}
