package com.lyterk.location_updates;

// TODO Posts are duplicated. The first one in the 'batch' that was created is the only one sent. Figure out why.

// TODO Posting may be broken. Find out why.

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
    String mTime;

    private static final String TAG = "com.lyterk.location_updates.Posting";
    private static final String postUrl = "http://requestb.in/u6x89uu6";

    public Posting(LocationData location) {
        this.mLatitude = location.mLatitude;
        this.mLongitude = location.mLongitude;
        this.mTime = location.mLastUpdateTime;
    }

    public static String POST(String url, String latitude, String longitude, String time) {

        InputStream mInputStream;
        String result = "";

        try {
            HttpClient mHttpClient = new DefaultHttpClient();
            HttpPost mHttpPost = new HttpPost(url);
            String json;

            JSONObject mJsonObject = new JSONObject();

            mJsonObject.accumulate("latitude", latitude);
            mJsonObject.accumulate("longitude", longitude);
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
                result = "Input stream is null for some reason";

        } catch (Exception e) {
            Log.d("posting.java.POST", e.getLocalizedMessage());
        }

        return result;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground (String... urls) {
            return POST(urls[0], mLatitude, mLongitude, mTime);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d(TAG, "Data sent");
        }
    }

    public void postingClick() {
        if (!validate()) {
            Log.d(TAG, "Validate failed. No location data");
        }
        new HttpAsyncTask().execute(postUrl);
    }

    private boolean validate () {
        if (mLatitude == null || mLongitude == null || mTime == null) {
            return false;
        }
        else
            return true;
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