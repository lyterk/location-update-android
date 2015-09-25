package com.lyterk.location_updates;

import android.content.Context;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

public class Ui {
    
    private Button mStartUpdatesButton;
    private Button mStopUpdatesButton;
    private Button mPostingButton;
    private TextView mLatitudeTextView;
    private TextView mLongitudeTextView;
    private TextView mLastUpdateTimeTextView;

    private Boolean mRequestingLocationUpdates = false;

    public Boolean getRequestingLocationUpdates() {
        return this.mRequestingLocationUpdates;
    }

    public void setRequestingLocationUpdates(Boolean rlu) {
        this.mRequestingLocationUpdates = rlu;
    }


    public Ui (View view) {
        findUi(view);
    }

    private void findUi(View view) {
        mStartUpdatesButton = (Button) view.findViewById(R.id.start_updates_button);
        mStopUpdatesButton = (Button) view.findViewById(R.id.stop_updates_button);
        mPostingButton = (Button) view.findViewById(R.id.post_button);
        mLatitudeTextView = (TextView) view.findViewById(R.id.latitude_text);
        mLongitudeTextView = (TextView) view.findViewById(R.id.longitude_text);
        mLastUpdateTimeTextView = (TextView) view.findViewById(R.id.last_update_time_text);
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

    public void updateUI(LocationData mLocationData) {
        if (mLocationData.mCurrentLocation != null) {
            mLatitudeTextView.setText(mLocationData.mLatitude);
            mLongitudeTextView.setText(mLocationData.mLongitude);
            mLastUpdateTimeTextView.setText(mLocationData.mLastUpdateTime);
        }
    }
}
