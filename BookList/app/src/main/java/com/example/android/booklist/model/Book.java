package com.example.android.booklist.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by theodosiostziomakas on 01/08/2017.
 */

public class Book implements Parcelable {

    private String title;
    private String author;
    private String imageUrl;
    private String publisher;
    private String publishedDate;
    private String language;
    private int pageCount;
    private double averageRating;
    private int ratingCount;
    private String textSnippet;

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getLanguage() {
        return language;
    }

    public int getPageCount() {
        return pageCount;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public String getTextSnippet() {
        return textSnippet;
    }

    public static Creator<Book> getCREATOR() {
        return CREATOR;
    }

    public Book(String title, String author, String imageUrl, String publishedDate, String language,double averageRating,int ratingCount,String textSnippet,int pageCount) {
        this.title = title;
        this.author = author;
        this.imageUrl = imageUrl;
        this.publishedDate = publishedDate;
        this.language = language;
        this.pageCount = pageCount;
        this.averageRating = averageRating;
        this.ratingCount = ratingCount;
        this.textSnippet = textSnippet;
    }

    protected Book(Parcel in) {
        title = in.readString();
        author = in.readString();
        imageUrl = in.readString();
        publisher = in.readString();
        publishedDate = in.readString();
        language = in.readString();
        pageCount = in.readInt();
        averageRating = in.readInt();
        ratingCount = in.readInt();
        textSnippet = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(author);
        parcel.writeString(imageUrl);
        parcel.writeString(publisher);
        parcel.writeString(publishedDate);
        parcel.writeString(language);
        parcel.writeInt(pageCount);
        parcel.writeDouble(averageRating);
        parcel.writeInt(ratingCount);
        parcel.writeString(textSnippet);
    }

}

