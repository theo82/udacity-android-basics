package com.example.android.booklist.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.booklist.R;
import com.example.android.booklist.model.Book;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;

import static android.R.attr.resource;

/**
 * Created by theodosiostziomakas on 01/08/2017.
 */

/**
 * {@link BookAdapter} is an {@link ArrayAdapter} that can provide the layout for each list item
 * based on a data source, which is a list of {@link Book} objects.
 */
public class BookAdapter extends ArrayAdapter<Book> {
    private int mListColor;
    /**
     * Create a new {@link BookAdapter} object.
     *
     * @param context is the current context (i.e. Activity).
     * @param booksArrayList is the list of {@link Book}s to be displayed.
     */
    public BookAdapter(Context context, ArrayList<Book> booksArrayList, int mListColor) {
        super(context, 0, booksArrayList);
        this.mListColor = mListColor;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = convertView;

        if(listItem == null){
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.book_list,parent,false);
        }

        Book currentBook = getItem(position);

        ImageView imageView = listItem.findViewById(R.id.image);
        //display the book picture of the current book in the ImageView
        Picasso.with(getContext()).load(currentBook.getImageUrl()).into(imageView);

        //new DownloadImageAsyncTask(imageView).execute(currentBook.getImageUrl());

        //Find the TextView with the ID title_text_view
        TextView titleTextView = listItem.findViewById(R.id.title_text_view);
        titleTextView.setText(currentBook.getTitle());

        //Find the TextView with ID author_text_view
        TextView authorTextView = listItem.findViewById(R.id.author_text_view);
        authorTextView.setText("Written by " + currentBook.getAuthor());

        //Find the TextView with ID language_text_view
        TextView languageTextView = listItem.findViewById(R.id.language_text_view);
        languageTextView.setText("Language " + currentBook.getLanguage());


        //Find the TextView with ID date_text_view
        TextView dateTextView = listItem.findViewById(R.id.date_text_view);
        dateTextView.setText("Released " + currentBook.getPublishedDate());

        // Set the theme color for the list item
        View textContainer = listItem.findViewById(R.id.text_container);
        // Find the color that the resource ID maps to
        int color = ContextCompat.getColor(getContext(), mListColor);
        // Set the background color of the text container View
        textContainer.setBackgroundColor(color);

        // Return the whole list item layout so that it can be shown in
        // the ListView.


        return listItem;
    }
    /*
    /**
     * Another AsyncTask which is used to download the images from the url.

    private class DownloadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

        ImageView bmImage;

        public DownloadImageAsyncTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];

            Bitmap bitmap = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
            bmImage.setVisibility(View.VISIBLE);
        }
    }
    */

}
