package com.example.android.myapplication;

/**
 * Created by theodosiostziomakas on 28/07/2017.
 */

public class City {

    private int imageResourceID;
    private int colorResourceID;
    private String title;
    private String address;

    public City(String title,String address,int imageResourceID){
        this.title = title;
        this.address = address;
        this.imageResourceID = imageResourceID;
    }

    public int getImageResourceID() {
        return imageResourceID;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public int getColorResourceID() {return colorResourceID;}

    @Override
    public String toString() {
        return "City{" +
                "mImageResourceID=" + imageResourceID +
                ", title='" + title + '\'' +
                ", address='" + address + '\'' +
                '}';
    }


}
