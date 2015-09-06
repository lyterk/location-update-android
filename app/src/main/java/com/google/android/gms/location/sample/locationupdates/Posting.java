package com.google.android.gms.location.sample.locationupdates;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Posting {

    String mLatitude;
    String mLongitude;
    String time;

    public Posting(Location currentLocation, String lastUpdateTime) {
        Location coordinate;
        coordinate = currentLocation;
        time = lastUpdateTime;

        mLatitude = Double.toString(coordinate.getLatitude());
        mLongitude = Double.toString(coordinate.getLongitude());
    }

    public static String POST(String url, String mLatitude, String mLongitude, String time) {

        InputStream mInputStream = null;
        String result = "";

        try {
            HttpClient mHttpClient = new DefaultHttpClient();
            HttpPost mHttpPost = new HttpPost(url);
            String json = "";

            JSONObject mJsonObject = new JSONObject();

            mJsonObject.accumulate("latitude", mLatitude);
            mJsonObject.accumulate("longitude", mLongitude);
            mJsonObject.accumulate("time", time);

            json = mJsonObject.toString();

            StringEntity se = new StringEntity(json);
            mHttpPost.setEntity(se);

            mHttpPost.setHeader("Accept", "application/json");
            mHttpPost.setHeader("Content-type", "application/json");

            HttpResponse httpResponse = mHttpClient.execute(mHttpPost);
            mInputStream = httpResponse.getEntity().getContent();

            if (mInputStream != null)
                result = convertInputStreamToString(mInputStream);
            else
                result = "Did not work";

        } catch (Exception e) {
            Log.d("posting.java.POST", e.getLocalizedMessage());
        }

        return result;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground (String... urls) {

        }
    }

    private boolean validate (Location coordinate) {
        if (coordinate != null)
            return true;
        return false;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }
}
