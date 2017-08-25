package com.example.android.booklist.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.android.booklist.model.Book;

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
import java.util.Iterator;
import java.util.List;

/**
 * Created by theodosiostziomakas on 02/08/2017.
 */

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    public QueryUtils(){

    }
    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createURL(String stringUrl){
        URL url = null;

        try{
            url = new URL(stringUrl);
        }catch (MalformedURLException e){

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
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
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
     * Return a list of {@link Book} objects that has been built up from
     * parsing the given JSON response.
     */
    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();

        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();

            while(line != null){
                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();
    }

    public static String formatListOfAuthors(JSONArray authorsList) throws JSONException {

        String authorsListInString = null;

        if (authorsList.length() == 0) {
            return null;
        }

        for (int i = 0; i < authorsList.length(); i++){
            if (i == 0) {
                authorsListInString = authorsList.getString(0);
            } else {
                authorsListInString += ", " + authorsList.getString(i);
            }
        }

        return authorsListInString;
    }
    private static List<Book> extractFeatureFromJson(String bookJson){

        if(TextUtils.isEmpty(bookJson)){
            return null;
        }
        // Create an empty ArrayList that we can start adding books to
        List<Book> books = new ArrayList<>();
        String thumbnail=null;
        try {
            JSONObject baseJSON = new JSONObject(bookJson);

            JSONArray itemsJsonArray = baseJSON.getJSONArray("items");
            int pageCount=0;
            double averageRating = 0.0;
            int ratingsCount = 0;
            String authors = null;
            String date = null;
            String textSnippet = null;
            for(int i = 0;i<itemsJsonArray.length(); i++){
                JSONObject item = itemsJsonArray.getJSONObject(i);
                JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                String title = volumeInfo.getString("title");

                if(volumeInfo.has("authors")) {
                    JSONArray authorsArray = volumeInfo.getJSONArray("authors");
                    authors = formatListOfAuthors(authorsArray);
                }

                String language = volumeInfo.getString("language");

                if (volumeInfo.has("publishedDate")) {
                    date = volumeInfo.getString("publishedDate");
                }


                if (volumeInfo.has("pageCount")) {
                    pageCount = volumeInfo.getInt("pageCount");
                }
                if (volumeInfo.has("averageRating")) {
                    averageRating = volumeInfo.getInt("averageRating");
                }

                if (volumeInfo.has("ratingsCount")) {
                    ratingsCount = volumeInfo.getInt("ratingsCount");
                }

                if(volumeInfo.has("imageLinks")){

                    JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                    thumbnail = imageLinks.getString("smallThumbnail");
                }
                if(volumeInfo.has("subtitle")){

                    textSnippet = volumeInfo.getString("subtitle");

                }else{
                    textSnippet = "N/A";
                }

                Book b = new Book(title,authors,thumbnail,date,language,averageRating,ratingsCount,textSnippet,pageCount);

                books.add(b);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return books;
    }


    /**
     * Query the dataset and return a list of {@link Book} objects.
     */
    public static List<Book> fetchBookData(String requestUrl){
        Log.v(LOG_TAG,"fetchBookData() called...");
        URL url = createURL(requestUrl);

        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Book> books = extractFeatureFromJson(jsonResponse);

        return books;
    }


}


