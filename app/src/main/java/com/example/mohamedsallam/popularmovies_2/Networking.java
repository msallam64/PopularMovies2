package com.example.mohamedsallam.popularmovies_2;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed Sallam on 17-Feb-18.
 */

 class Networking {
    private static final String LOG_TAG = Networking.class.getSimpleName();
    private static final String IMAGE_URL = "http://image.tmdb.org/t/p/";
    private static final String SIZE_PARAM = "w185";

    protected static URL makeUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Throw an Exception in makeUrl", e);
        }
        return url;
    }

    protected static String makeHttpRequest(URL url) throws IOException {
        String jsonReponse = null;
        if (url == null) {
            return null;
        }
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonReponse = readFromStream(inputStream);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Throw an Exception in makeHttpRequest", e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return jsonReponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static URL buildUrl(String posterUrl) {
        Uri builtUri = Uri.parse(IMAGE_URL).buildUpon()
                .appendEncodedPath(SIZE_PARAM)
                .appendEncodedPath(posterUrl)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Throw an Exception in buildUrl", e);
        }
        return url;
    }

    public static List<Movie> extractMovieData(String movieJson) {
        if (TextUtils.isEmpty(movieJson)) {
            return null;
        }
         List<Movie> movies = new ArrayList<>();

        String posterPath, description, title, releaseDate;
        String id;
        double voteAverage;
        try {
            JSONObject root = new JSONObject(movieJson);
            JSONArray resultArray = root.getJSONArray("results");
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject resultObject = resultArray.getJSONObject(i);
                id=resultObject.getString("id");
                posterPath = resultObject.getString("poster_path");
                title = resultObject.getString("original_title");
                releaseDate = resultObject.getString("release_date");
                description = resultObject.getString("overview");
                voteAverage = resultObject.getDouble("vote_average");
                posterPath = buildUrl(posterPath).toString();
                Movie movie = new Movie(title, posterPath, releaseDate, voteAverage, description,id);
                movies.add(movie);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Throw an Exception in extractMovieData", e);
        }

        return movies;
    }

    public static List<Movie> fetchMovieAppData(String requestUrl) {
        URL url = makeUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);

            Log.e("karim","result"+jsonResponse);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Throw an Exception in fetchMovieAppData", e);
        }
        return extractMovieData(jsonResponse);
    }



}

