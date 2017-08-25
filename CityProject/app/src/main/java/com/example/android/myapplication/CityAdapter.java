package com.example.android.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by theodosiostziomakas on 28/07/2017.
 */

public class CityAdapter extends ArrayAdapter<City> {

    int mColorResourceID;

    /**
     *
     * @param context
     * @param city
     * @param mColorResourceID
     */
    CityAdapter(Context context, ArrayList<City> city, int mColorResourceID){
        super(context,0,city);
        this.mColorResourceID = mColorResourceID;
    }

    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {

        View listView = convertView;

        if(listView == null){
            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        City city = getItem(position);

        TextView nameTextView = listView.findViewById(R.id.name_text_view);
        nameTextView.setText(city.getTitle());

        TextView addressTextView = listView.findViewById(R.id.address_text_view);
        addressTextView.setText(city.getAddress());

        ImageView imageView = listView.findViewById(R.id.image);
        imageView.setImageResource(city.getImageResourceID());

        View textContainer = listView.findViewById(R.id.text_container);

        int color = ContextCompat.getColor(getContext(),mColorResourceID);

        textContainer.setBackgroundColor(color);

        return listView;
    }
}
