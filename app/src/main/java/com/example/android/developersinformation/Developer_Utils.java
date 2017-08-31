package com.example.android.developersinformation;


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

public class Developer_Utils {

    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = Developer_Utils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link Developer_Utils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private Developer_Utils() {
    }

    /**
     * Query the USGS dataset and return a list of {@link DeveloperLagos} objects.
     */
    public static List<DeveloperLagos> fetchDeveloperLagosData(String requestUrl) {

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<DeveloperLagos> developerLagos = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return developerLagos;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
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

    /**
     * Return a list of {@link DeveloperLagos} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<DeveloperLagos> extractFeatureFromJson(String lagosDeveloperJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(lagosDeveloperJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<DeveloperLagos> lagosDeveloper = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(lagosDeveloperJSON);

            // Extract the JSONArray associated with the key called "items",
            // which represents a list of items (or lagosDeveloper).
            JSONArray lagosDeveloperArray = baseJsonResponse.getJSONArray("items");

            // For each lagosDeveloper in the lagosDeveloperArray, create an {@link DeveloperLagos} object
            for (int i = 0; i < lagosDeveloperArray.length(); i++) {

                // Get a single lagosDeveloper at position i within the list of lagosDevelopers
                JSONObject currentDeveloper = lagosDeveloperArray.getJSONObject(i);

                // Extract the value for the key called "login"
                String username = currentDeveloper.getString("login");

                // Extract the value for the key called "html_url"
                String developer_url = currentDeveloper.getString("html_url");

                // Extract the value for the key called "avatar_url"
                String image_url = currentDeveloper.getString("avatar_url");

                // Create a new {@link DeveloperLagos} object with username, developer_url and image_url
                // from the JSON response.
                DeveloperLagos developer = new DeveloperLagos(username, developer_url, image_url);

                // Add the new {@link Earthquake} to the list of earthquakes.
                lagosDeveloper.add(developer);
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            e.printStackTrace();
        }

        // Return the list of lagosDeveloper
        return lagosDeveloper;
    }
}