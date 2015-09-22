package com.lyterk.location_updates;

import android.widget.Button;
import android.widget.TextView;
import android.view.View;

public class Ui {
    
    protected Button mStartUpdatesButton;
    protected Button mStopUpdatesButton;
    protected Button mPostingButton;
    protected TextView mLatitudeTextView;
    protected TextView mLongitudeTextView;
    protected TextView mLastUpdateTimeTextView;   

    private Boolean mRequestingLocationUpdates;

    public Ui () {
        setContentView(R.layout.main_activity);
        findUi();
    }

    public void findUi() {
        mStartUpdatesButton = (Button) findViewById(R.id.start_updates_button);
        mStopUpdatesButton = (Button) findViewById(R.id.stop_updates_button);
        mPostingButton = (Button) findViewById(R.id.post_button);
        mLatitudeTextView = (TextView) findViewById(R.id.latitude_text);
        mLongitudeTextView = (TextView) findViewById(R.id.longitude_text);
        mLastUpdateTimeTextView = (TextView) findViewById(R.id.last_update_time_text);
    }

    public void setButtonsEnabledState() {
        if (mRequestingLocationUpdates) {
            mStartUpdatesButton.setEnabled(false);
            mStopUpdatesButton.setEnabled(true);
        } else {
            mStartUpdatesButton.setEnabled(true);
            mStopUpdatesButton.setEnabled(false);
        }
    }

    public void updateUI(LocationData locationData) {
        if (locationData.mCurrentLocation != null) {
            mLatitudeTextView.setText(locationData.mLatitude);
            mLongitudeTextView.setText(locationData.mLongitude);
            mLastUpdateTimeTextView.setText(locationData.mLastUpdateTime);
        }
    }

    public void startUpdatesButtonHandler(View view) {
        if (!mRequestingLocationUpdates) {
            mRequestingLocationUpdates = true;
            setButtonsEnabledState();
            startLocationUpdates();
        }
    }

    public void stopUpdatesButtonHandler(View view) {
        if (mRequestingLocationUpdates) {
            mRequestingLocationUpdates = false;
            setButtonsEnabledState();
            stopLocationUpdates();
        }
    }
}
