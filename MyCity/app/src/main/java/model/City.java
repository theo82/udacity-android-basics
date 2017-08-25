package model;

/**
 * Created by theodosiostziomakas on 26/07/2017.
 */

public class City {



    private String mTitle;
    private String mLocation;
    private int mImageResourceId;
    private int mAudioResourceId;




    /**
     * A constructor holding three instance variables.
     * @param title
     * @param location
     * @param mImageResourceId
     */
    public City(String title,String location,int mImageResourceId,int mAudioResourceId){
        this.mTitle = title;
        this.mLocation = location;
        this.mImageResourceId = mImageResourceId;
        this.mAudioResourceId = mAudioResourceId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getLocation() {
        return mLocation;
    }

    public int getmImage() {
        return mImageResourceId;
    }

    public int getmAudioResourceId() {return mAudioResourceId;}

    @Override
    public String toString() {
        return "City{" +
                "mTitle='" + mTitle + '\'' +
                ", mLocation='" + mLocation + '\'' +
                ", mImageResourceId=" + mImageResourceId +
                ", mAudioResourceId=" + mAudioResourceId +
                '}';
    }

}
