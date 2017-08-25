package com.example.android.newsapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import com.example.android.newsapp.model.News;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by theodosiostziomakas on 08/08/2017.
 */

public class QueryUtils {
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();
    /**
     * Returns a new URL object from the given URL.
     */
    private static URL createUrl(String stringUrl){
        URL url = null;

        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
    private static String formatDate(String rawDate) {
        String jsonDatePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat jsonFormatter = new SimpleDateFormat(jsonDatePattern, Locale.US);
        try {
            Date parsedJsonDate = jsonFormatter.parse(rawDate);
            String finalDatePattern = "MMM d, yyy";
            SimpleDateFormat finalDateFormatter = new SimpleDateFormat(finalDatePattern, Locale.US);
            return finalDateFormatter.format(parsedJsonDate);
        } catch (ParseException e) {
            Log.e("QueryUtils", "Error parsing JSON date: ", e);
            return "";
        }
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
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the news JSON results.", e);
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
     * Return a list of {@link News} objects that have benn built up from
     * parsing the given JSON response.
     */
    private static List<News> extractFeatureFromJson(String newsJSON){

        if(TextUtils.isEmpty(newsJSON)){
            return null;
        }

        List<News> news = new ArrayList<>();

        try {

            JSONObject jsonResponse = new JSONObject(newsJSON);
            JSONObject jsonResults = jsonResponse.getJSONObject(Constants.RESPONSE);
            JSONArray resultsArray = jsonResults.getJSONArray(Constants.RESULTS);

            //For each news in the newsArray, create a {@link News} object
            for(int i = 0; i < resultsArray.length(); i++){

                //Get a single news article at position i within the list of news
                JSONObject currentNews = resultsArray.getJSONObject(i);

                // Extract the value called "webTitle"
                String title = currentNews.getString(Constants.WEB_TITLE);

                // Extract the value called "type"
                String type = currentNews.getString(Constants.TYPE);

                // Extract the value called "section"
                String section = currentNews.getString(Constants.SECTION_NAME);

                // Extract the value called "webPublicationDate"
                String date = currentNews.getString(Constants.WEB_PUBLICATION_DATE);
                date = formatDate(date);
                // Extract the value called "webUrl"
                String webUrl = currentNews.getString(Constants.WEB_URL);

                // Create a new{@link News} object with title, type, section, date
                // from the JSON response

                News n = new News(title,type,section,date,webUrl);

                // Add the new {@link News} to the list of news.
                news.add(n);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return news;

    }

    /**
     * Query the Guardian's api dataset and return a list of {@link News} objects.
     */
    public static List<News> fetchNewskeData(String requestUrl) {
        Log.v(LOG_TAG,"fetchNewskeData");
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link News}s
        List<News> news = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link News}
        return news;
    }

    /**
     * A method checking if there is an internet connection or not
     * @param context is the Activtity's context i.e. this.
     * @return is returning true i.e. there is an internet connection or fault which is the opposite.
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
