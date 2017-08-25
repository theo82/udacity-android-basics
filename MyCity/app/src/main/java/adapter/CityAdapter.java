package adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.mycity.R;

import java.util.ArrayList;
import java.util.List;

import model.City;

/**
 * Created by theodosiostziomakas on 26/07/2017.
 */

public class CityAdapter extends ArrayAdapter<City> {
    int mColorResourceId;
    public CityAdapter(Context context, ArrayList<City> cityList,int mColorResourceId) {
        super(context, 0, cityList);
        this.mColorResourceId = mColorResourceId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listView = convertView;

        if(listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        City currentSite = getItem(position);

        TextView titleTextView = listView.findViewById(R.id.title_text_view);
        titleTextView.setText(currentSite.getTitle());

        TextView locationTextView = listView.findViewById(R.id.location_text_view);
        locationTextView.setText(currentSite.getLocation());

        ImageView imgageView = listView.findViewById(R.id.image);
        imgageView.setImageResource(currentSite.getmImage());

        //Set the color for the list item
        View textContainer = listView.findViewById(R.id.text_container);

        //Find the color that the resource ID maps to.
        int color = ContextCompat.getColor(getContext(),mColorResourceId);

        //Set the background color to the container view.
        textContainer.setBackgroundColor(color);
        return listView;
    }
}
