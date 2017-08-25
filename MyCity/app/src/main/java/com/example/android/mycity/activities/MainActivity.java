package com.example.android.mycity.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.android.mycity.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView alcazarCategory = (TextView)findViewById(R.id.alcazar);
        alcazarCategory.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   Intent i = new Intent(MainActivity.this,AlcazarActivity.class);
                                                   startActivity(i);
                                               }
                                           }
        );

        TextView sportsCategory = (TextView)findViewById(R.id.sports);
        sportsCategory.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   Intent i = new Intent(MainActivity.this,SportsActivity.class);
                                                   startActivity(i);
                                               }
                                           }
        );
    }
}
