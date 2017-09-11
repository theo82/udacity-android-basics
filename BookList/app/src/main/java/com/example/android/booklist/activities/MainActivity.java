package com.example.android.booklist.activities;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.booklist.R;
import com.example.android.booklist.adapter.BookAdapter;
import com.example.android.booklist.model.Book;
import com.example.android.booklist.utils.QueryUtils;

import java.util.ArrayList;
import java.util.List;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class MainActivity extends AppCompatActivity{
    public static final String BOOK_URL = "https://www.googleapis.com/books/v1/volumes?";
    public static final String LOG_TAG = MainActivity.class.getName();
    BookAdapter adapter;
    EditText searchText;
    Button searchButton;
    String search;
    ListView listview;
    ArrayList<Book> bookList = new ArrayList<>();
    TextView mEmptyStateTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v(LOG_TAG,"onCreate()called ...");

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        mEmptyStateTextView.setText(R.string.welcoming);

        final ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (savedInstanceState == null ||!savedInstanceState.containsKey("keyBookList"))
        {
            bookList = new ArrayList<>();
            searchText = (EditText) findViewById(R.id.search_text);
            searchButton = (Button) findViewById(R.id.search_button);
            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    search = searchText.getText().toString();

                    search = search.replace(" ","+");

                    //String url = URL + search + "&maxResults=15";

                    String urlString = urlofSearch();

                    if (networkInfo != null && networkInfo.isConnected()) {
                        BookAsyncTask bookAsyncTask = new BookAsyncTask();
                        bookAsyncTask.execute(urlString);
                        mEmptyStateTextView.setVisibility(View.GONE);
                    }else{
                        // Update empty state with no connection error message
                        mEmptyStateTextView.setText(R.string.error_no_internet);
                    }
                    adapter = new BookAdapter(getApplicationContext(), bookList, R.color.list_color);

                    listview = (ListView) findViewById(R.id.list);
                    listview.setEmptyView(mEmptyStateTextView);
                    listview.setAdapter(adapter);

                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            Book book = bookList.get(i);

                            String image = book.getImageUrl();
                            String title = book.getTitle();
                            String author = book.getAuthor();
                            String date = book.getPublishedDate();
                            double averageRating = book.getAverageRating();
                            int ratingCount = book.getRatingCount();
                            int pageCount = book.getPageCount();
                            String subtitle = book.getTextSnippet();

                            Intent intent = new Intent(MainActivity.this,DetailedBook.class);
                            intent.putExtra("image",image);
                            intent.putExtra("title",title);
                            intent.putExtra("author",author);
                            intent.putExtra("publishedDate",date);
                            intent.putExtra("averageRating",averageRating);
                            intent.putExtra("ratingCount",ratingCount);
                            intent.putExtra("subtitle",subtitle);
                            intent.putExtra("pageCount",pageCount);

                            startActivity(intent);

                        }
                    });
                }
            });

        }else {
            bookList = savedInstanceState.getParcelableArrayList("keyBookList");
            adapter = new BookAdapter(getApplicationContext(), bookList, R.color.list_color);

            listview = (ListView) findViewById(R.id.list);
            listview.setAdapter(adapter);
            Log.v(LOG_TAG, String.valueOf(bookList.size()));
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.v(LOG_TAG,"onSaveInstanceState() called...");
        outState.putParcelableArrayList("keyBookList",bookList);
        super.onSaveInstanceState(outState);
    }


    private class BookAsyncTask extends AsyncTask<String,Void,List<Book>>{

        @Override
        protected List<Book> doInBackground(String... urls) {
            Log.v(LOG_TAG,"doInBackground() called...");
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<Book> result = QueryUtils.fetchBookData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<Book> books) {
            adapter.clear();
            Log.v(LOG_TAG,"onPostExecute() called...");
            if(books != null && !books.isEmpty()){
                adapter.addAll(books);
                mEmptyStateTextView.setVisibility(View.GONE);
            }
        }
    }


    public String urlofSearch(){
        Uri baseUri = Uri.parse(BOOK_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("q", search);
        uriBuilder.appendQueryParameter("maxResults", "15");

        return  uriBuilder.toString();
    }
}
