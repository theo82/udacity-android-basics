package com.example.android.myapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantsFragment extends Fragment {


    public RestaurantsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.city_list, container, false);

        final ArrayList<City> city = new ArrayList<>();
        city.add(new City("Akamatra","Βύρωνος 5",R.mipmap.ic_restaurant1));
        city.add(new City("Bukowski Eatery & Drink","Πανός 11",R.mipmap.ic_restaurant2));
        city.add(new City("Nonna Rossa","Σκυλοσόφου 9",R.mipmap.ic_restaurant3));
        city.add(new City("Posto Publico","Φιλελλήνων 50",R.mipmap.ic_restaurant4));
        city.add(new City("Bier Center","Ανθίμου Γαζή 41",R.mipmap.ic_restaurant5));
        city.add(new City("S'IL VOUS PLAIT","Πατρόκλου 14",R.mipmap.ic_restaurant6));
        city.add(new City("Lucullus","Βενιζέλου 129",R.mipmap.ic_restaurant7));

        CityAdapter adapter = new CityAdapter(getActivity(),city,R.color.category_restaurants);

        ListView listView = rootView.findViewById(R.id.listView);

        listView.setAdapter(adapter);

        return rootView;
    }

}
