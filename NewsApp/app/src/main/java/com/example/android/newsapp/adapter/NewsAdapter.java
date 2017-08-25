package com.example.android.newsapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.newsapp.R;
import com.example.android.newsapp.model.News;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by theodosiostziomakas on 07/08/2017.
 */

public class NewsAdapter extends ArrayAdapter<News>{

    public NewsAdapter(Context context, ArrayList<News> newsArrayList) {
        super(context,0, newsArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listViewItem = convertView;

        if(listViewItem == null){
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.news_item,parent,false);
        }

        News news = getItem(position);

        Date dateObject = new Date(news.getmDate());
        //Get the text view whose id is title
        TextView title = listViewItem.findViewById(R.id.title);
        title.setText(news.getmTitle());

        //Get the text view whose id is type
        TextView type = listViewItem.findViewById(R.id.type);
        type.setText(news.getmType());

        //Get the text view whose id is section
        TextView section = listViewItem.findViewById(R.id.section);
        section.setText(news.getmSectionName());

        //Get the text view whose id is date
        TextView date = listViewItem.findViewById(R.id.date);
        date.setText(news.getmDate());

        return listViewItem;
    }


}
