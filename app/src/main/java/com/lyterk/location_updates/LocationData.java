package com.lyterk.location_updates;

import android.location.Location;

public class LocationData {
    Location mCurrentLocation;
    String mLastUpdateTime;

    String mLatitude;
    String mLongitude;

    public LocationData(Location location, String time) {
        mLatitude = Double.toString(location.getLatitude());
        mLongitude = Double.toString(location.getLongitude());

        this.mCurrentLocation = location;
        this.mLastUpdateTime = time;
    }
}