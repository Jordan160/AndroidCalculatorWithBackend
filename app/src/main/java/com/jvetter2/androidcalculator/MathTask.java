package com.jvetter2.androidcalculator;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MathTask extends AsyncTask<String, Void, String> {
    private static String result;

    @Override
    protected String doInBackground(String... urls) {
        String result = "";
        URL url;
        HttpURLConnection urlConnection;

        try {
            url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            int data = reader.read();

            while(data != -1) {
                char current = (char) data;
                result += current;
                data = reader.read();
            }

            return  result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getMathCall(String url) {
        MathTask task = new MathTask();
        try {
            result = task.execute(url).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}