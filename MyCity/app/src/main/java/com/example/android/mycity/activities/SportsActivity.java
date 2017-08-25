package com.example.android.mycity.activities;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.mycity.R;

import java.util.ArrayList;

import adapter.CityAdapter;
import model.City;

public class SportsActivity extends AppCompatActivity {
    AudioManager mAudioManager;
    MediaPlayer mMediaPlayer;

    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
                  releaseMediaPlayer();
        }
    };

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
                  if(i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                      mMediaPlayer.pause();
                      mMediaPlayer.seekTo(0);
                  }else if(i == AudioManager.AUDIOFOCUS_GAIN){
                      mMediaPlayer.start();
                  }else if(i == AudioManager.AUDIOFOCUS_LOSS){
                      releaseMediaPlayer();
                  }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_list);

        mAudioManager = (AudioManager)getSystemService(AUDIO_SERVICE);

        final ArrayList<City> city = new ArrayList<>();
        city.add(new City("Sports song1","Larissa City",R.mipmap.ic_sports1,R.raw.song1));
        city.add(new City("Sports song2","Larissa City",R.mipmap.ic_sports2,R.raw.song2));
        city.add(new City("Sports song3","Larissa City",R.mipmap.ic_sports3,R.raw.song3));
        city.add(new City("Sports song4","Larissa City",R.mipmap.ic_sports4,R.raw.song4));
        city.add(new City("Sports song5","Larissa City",R.mipmap.ic_sports5,R.raw.song5));
        city.add(new City("Sports song6","Larissa City",R.mipmap.ic_sports6,R.raw.song6));
        city.add(new City("Sports song7","Larissa City",R.mipmap.ic_sports7,R.raw.song1));
        ListView list = (ListView)findViewById(R.id.listView);

        CityAdapter adapter = new CityAdapter(this,city,R.color.category_sports);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                releaseMediaPlayer();
                City c = city.get(i);

                Log.v("Activity","Current row:" + c);

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(SportsActivity.this, c.getmAudioResourceId());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        releaseMediaPlayer();
    }
    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
