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
public class CafesFragment extends Fragment {


    public CafesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.city_list, container, false);

        final ArrayList<City> city = new ArrayList<>();
        city.add(new City("Bruno","Ηρ. Πολυτεχνείου και Φαρσάλων",R.mipmap.ic_cafe1));
        city.add(new City("Bruno","Φαρσάλων 130",R.mipmap.ic_cafe2));
        city.add(new City("Bruno","Μεγάλου Αλεξάνδρου και Πατρόκλου",R.mipmap.ic_cafe3));
        city.add(new City("Mikel","Πανός 1",R.mipmap.ic_cafe4));
        city.add(new City("Mikel","Ηρ. Πολυτεχνείου 44",R.mipmap.ic_cafe5));
        city.add(new City("Mikel","Βόλου 50",R.mipmap.ic_cafe6));
        city.add(new City("Mikel","Γεωργιάδου 37",R.mipmap.ic_cafe7));

        CityAdapter adapter = new CityAdapter(getActivity(),city,R.color.category_cafes);

        ListView listView = rootView.findViewById(R.id.listView);

        listView.setAdapter(adapter);

        return rootView;
    }

}
