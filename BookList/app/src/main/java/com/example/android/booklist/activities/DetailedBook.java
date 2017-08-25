package com.example.android.booklist.activities;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.booklist.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import static android.R.attr.rating;
import static com.example.android.booklist.R.id.imageView;

public class DetailedBook extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_book);

        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        String image = b.getString("image");
        String desc = b.getString("desc");
        String title = b.getString("title");
        String author = b.getString("author");
        String date = b.getString("publishedDate");
        double ratingAverage = b.getDouble("averageRating");
        int ratingCount = b.getInt("ratingCount");
        int pageCount = b.getInt("pageCount");
        String textSnippet = b.getString("subtitle");

        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        Picasso.with(getApplicationContext()).load(image).into(imageView);

        TextView titleTextView = (TextView)findViewById(R.id.title_text_view);
        titleTextView.setText(title);


        TextView authorTextView = (TextView)findViewById(R.id.author_text_view);
        authorTextView.setText("Written by " + author);

        TextView dateTextView = (TextView)findViewById(R.id.date_text_view);
        dateTextView.setText("Published " + date);

        TextView ratingAverageTextView = (TextView)findViewById(R.id.average_rating_text_view);
        ratingAverageTextView.setText("Average rating " + ratingAverage);

        TextView ratingCountTextView = (TextView)findViewById(R.id.rating_count_text_view);
        ratingCountTextView.setText("Rating count " + ratingCount);

        TextView snippetCountTextView = (TextView)findViewById(R.id.snippet_text_view);
        snippetCountTextView.setText(textSnippet);

        TextView pagesCountTextView = (TextView)findViewById(R.id.page_count_text_view);
        pagesCountTextView.setText("Pages " + pageCount);

    }
}
