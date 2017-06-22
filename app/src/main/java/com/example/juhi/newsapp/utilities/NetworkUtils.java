package com.example.juhi.newsapp.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static android.content.ContentValues.TAG;

/**
 * Created by juhi on 6/20/17.
 */

public class NetworkUtils {

    // declaring constants
    final static String BASE_URL="https://newsapi.org/v1/articles";
    final static String SORT_PARAM = "sortBy";
    final static String API_PARAM = "apiKey";
    final static String QUERY_PARAM = "source";

    //assigning values of params
    final static String api_key = "f436e71026e74ceeb4971162f542881f";
    final static String sortBy = "latest";


    //static url for now
    public static URL buildUrl(){
        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM,"the-next-web")
                .appendQueryParameter(SORT_PARAM,sortBy)
                .appendQueryParameter(API_PARAM,api_key)
                .build();

        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    // get data from URL
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
