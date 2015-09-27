package com.lyterk.location_updates;

import android.widget.Button;
import android.widget.TextView;
import android.view.View;

public class Ui {
    
    private Button mStartUpdatesButton;
    private Button mStopUpdatesButton;
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


    public Ui (Button startUB, Button stopUB, TextView laTV, TextView loTV, TextView lutTV) {
        this.mStartUpdatesButton = startUB;
        this.mStopUpdatesButton = stopUB;
        this.mLatitudeTextView = laTV;
        this.mLongitudeTextView = loTV;
        this.mLastUpdateTimeTextView = lutTV;
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
