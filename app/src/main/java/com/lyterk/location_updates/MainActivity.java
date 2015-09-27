package com.lyterk.location_updates;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    protected static final String TAG = "com.lyterk.location_updates.MainActivity";

    protected final static String REQUESTING_LOCATION_UPDATES_KEY = "requesting-location-updates-key";
    protected final static String LOCATION_KEY = "location-key";
    protected final static String LAST_UPDATED_TIME_STRING_KEY = "last-updated-time-string-key";
    private LocationController mController;
    private View view;
    private Ui ui;
    private Boolean mRequestingLocationUpdates;

    private Button mStartUpdatesButton;
    private Button mStopUpdatesButton;
    private TextView mLatitudeTextView;
    private TextView mLongitudeTextView;
    private TextView mLastUpdateTimeTextView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        findUi();
        ui = new Ui(mStartUpdatesButton,
                mStopUpdatesButton,
                mLatitudeTextView,
                mLongitudeTextView,
                mLastUpdateTimeTextView);

        mController = new LocationController(savedInstanceState, ui, this);

        mController.buildGoogleApiClient(this);
    }

    private void findUi() {
        mStartUpdatesButton = (Button) findViewById(R.id.start_updates_button);
        mStopUpdatesButton = (Button) findViewById(R.id.stop_updates_button);
        mLatitudeTextView = (TextView) findViewById(R.id.latitude_text);
        mLongitudeTextView = (TextView) findViewById(R.id.longitude_text);
        mLastUpdateTimeTextView = (TextView) findViewById(R.id.last_update_time_text);
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

    public void onClick(View view) {
        mController.clickHandler(view);
    }
}
