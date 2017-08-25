package com.example.android.newsapp.activity;


import android.app.LoaderManager;
import android.content.Context;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.newsapp.model.News;
import com.example.android.newsapp.adapter.NewsAdapter;
import com.example.android.newsapp.loader.NewsLoader;
import com.example.android.newsapp.R;
import com.example.android.newsapp.utils.QueryUtils;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.newsapp.utils.QueryUtils.LOG_TAG;


public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<News>>, SwipeRefreshLayout.OnRefreshListener {

    public static final int NEWS_LOADER_ID = 1;
    ConnectivityManager connMgr;

    NetworkInfo networkInfo;
    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;

    NewsAdapter adapter;
    SwipeRefreshLayout swipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(LOG_TAG,"onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v(LOG_TAG,"initLoader");


        swipe = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipe.setOnRefreshListener(this);
        swipe.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        // Find a reference to the {@link ListView} in the layout
        ListView newsListView = (ListView) findViewById(R.id.list_view);



        adapter = new NewsAdapter(this,new ArrayList<News>());



        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Find the current news item that was clicked on
                News currentNews = adapter.getItem(i);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsApi = Uri.parse(currentNews.getmUrl());

                // Create a new intent to view the news URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsApi);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        newsListView.setAdapter(adapter);


        // If there is a network connection, fetch data
        if (QueryUtils.isNetworkAvailable(this)) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            // Otherwise, display error


            Toast.makeText(this,"No internet connection",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public android.content.Loader<List<News>> onCreateLoader(int id, Bundle args){
        Log.v(LOG_TAG,"onCreateLoader");

        String url = getUri();

        return new NewsLoader(this,url);
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<News>> loader, List<News> news) {
        // Clear the adapter of previous news data
        Log.v(LOG_TAG,"onLoadFinished");

        swipe.setRefreshing(false);

        

        adapter.clear();

        // If there is a valid list of {@link News}, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (news != null && !news.isEmpty()) {
            adapter.setNotifyOnChange(false);
            adapter.clear();
            adapter.setNotifyOnChange(true);
            adapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<News>> loader) {
        adapter.clear();
    }


    @Override
    public void onRefresh() {
        getLoaderManager().restartLoader(NEWS_LOADER_ID, null, this);
    }

    /**
     * A method that builds the Uri.
     * @return is the returned Uri.
     */
    public String getUri(){

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .encodedAuthority("content.guardianapis.com")
                .appendPath("search")
                .appendQueryParameter("q","debates")
                .appendQueryParameter("api-key","test");

        String url = builder.toString();

        return url.toString();
    }
}
