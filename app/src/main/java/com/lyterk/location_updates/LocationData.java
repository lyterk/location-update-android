package com.lyterk.location_updates;

import android.location.Location;

import java.text.DateFormat;
import java.util.Date;

import java.sql.Timestamp;

public class LocationData {

    public final Location mCurrentLocation;
    public final String mLastUpdateTime;
    public final String mLatitude;
    public final String mLongitude;
    public final Timestamp mSqlDate;

    public LocationData(Location location) {
        this.mCurrentLocation = location;

        mLatitude = Double.toString(location.getLatitude());
        mLongitude = Double.toString(location.getLongitude());

        final Date mUtilDate = new java.util.Date();
        mLastUpdateTime = DateFormat.getDateTimeInstance().format(mUtilDate);
        mSqlDate = new Timestamp(mUtilDate.getTime());
    }
}
