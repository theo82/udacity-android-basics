package com.example.android.booklist.utils;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.booklist.model.Book;

import java.util.List;

/**
 * Created by theodosiostziomakas on 02/08/2017.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {


    private String mUrl;

    public BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {

        if(mUrl == null){
            return null;
        }

        List<Book> books = QueryUtils.fetchBookData(mUrl);
        return books;
    }

}
