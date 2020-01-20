package com.example.whitelake.pkgServices;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DistanceGetService extends AsyncTask<String, Void, String> {
    private static final String URL = "/MongoSchupfer/webresources/distance";
    private static String ipHost = null;

    public static void setIpHost(String ip) {
        ipHost = ip;
    }

    @Override
    protected String doInBackground(String... params) {
        boolean isError = false;
        java.net.URL url;
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String content;

        try {
            url = new URL(ipHost + URL + "?shipname=" + params[0] + "&long=" + params[1] + "&lat=" + params[2]);
            conn = (HttpURLConnection) url.openConnection();
            if (!conn.getResponseMessage().contains("OK")) {
                isError = true;
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            }
            //get data from server
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            content = sb.toString();
            if (isError) throw new Exception(conn.getResponseCode() + "; " + content);
        } catch (Exception e) {
            content = e.getMessage();
        } finally {
            try {
                reader.close();
                conn.disconnect();
            } catch (Exception ex) {
            }
        }
        return content;
    }
}
