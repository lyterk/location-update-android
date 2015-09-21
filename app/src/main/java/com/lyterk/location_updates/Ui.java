package com.lyterk.location_updates;

import android.widget.Button;
import android.widget.TextView;

public class Ui {

    protected Button mStartUpdatesButton;
    protected Button mStopUpdatesButton;
    protected Button mPostingButton;
    protected TextView mLatitudeTextView;
    protected TextView mLongitudeTextView;
    protected TextView mLastUpdateTimeTextView;

    protected void findUis() {
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

    protected void updateUI(LocationData location_data) {
        if (mCurrentLocation != null) {
            mLatitudeTextView.setText(location_data.mLatitude);
            mLongitudeTextView.setText(location_data.mLongitude);
            mLastUpdateTimeTextView.setText(location_data.mLastUpdateTime);
        }
    }
}
