package com.example.android.newsapp.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.example.android.newsapp.model.News;
import com.example.android.newsapp.utils.QueryUtils;

import java.util.List;

/**
 * Created by theodosiostziomakas on 08/08/2017.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    private static final String LOG_TAG = NewsLoader.class.getName();

    private String mUrl;

    public NewsLoader(Context context, String ulr){

        super(context);
        mUrl = ulr;

    }

    @Override
    protected void onStartLoading() {
        Log.v(LOG_TAG,"onStartLoading");
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {

        List<News> news = QueryUtils.fetchNewskeData(mUrl);
        return news;
    }
}
