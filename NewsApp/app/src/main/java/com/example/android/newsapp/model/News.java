package com.example.android.newsapp.model;

/**
 * Created by theodosiostziomakas on 07/08/2017.
 */

public class News {

    public String getmTitle() {
        return mTitle;
    }

    public String getmType() {
        return mType;
    }

    public String getmSectionName() {
        return mSectionName;
    }

    public String getmDate() {
        return mDate;
    }



    private String mTitle;
    private String mType;
    private String mSectionName;
    private String mDate;

    public String getmUrl() {
        return mUrl;
    }

    private String mUrl;

    public News(String mTitle, String mType, String mSectionName, String mDate, String mUrl) {
        this.mTitle = mTitle;
        this.mType = mType;
        this.mSectionName = mSectionName;
        this.mDate = mDate;
        this.mUrl = mUrl;

    }


}
