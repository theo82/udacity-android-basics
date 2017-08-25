package com.example.android.mycity.activities;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.mycity.R;

import java.util.ArrayList;

import adapter.CityAdapter;
import model.City;

public class AlcazarActivity extends AppCompatActivity {
    AudioManager mAudioManager;
    MediaPlayer mediaPlayer = null;

    /**
     * This listener get triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };
    /**
     * This listener gets triggered when the audio focus has changed.
     */
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            /**
             * AUDIOFOCUS_LOSS_TRANSIENT: Means that we lost focus for a short period of time.
             * AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK: Focus is not lost but the sound is playing with a lower volume.
             * AUDIOFOCUS_GAIN: Focus is regained and restrarted.
             * AUDIOFOCUS_LOSS: Focus is lost meaning that all audio resources(MediaPlayer,AudioFocus),are cleared.
             */
            if(i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }else if(i ==  AudioManager.AUDIOFOCUS_GAIN){
                mediaPlayer.start();
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
        city.add(new City("Alcazar Park song1","Larissa City",R.mipmap.ic_alcazar1,R.raw.song1));
        city.add(new City("Alcazar Park song2","Larissa City",R.mipmap.ic_alcazar2,R.raw.song2));
        city.add(new City("Alcazar Park song3","Larissa City",R.mipmap.ic_alcazar3,R.raw.song3));
        city.add(new City("Alcazar Park song4","Larissa City",R.mipmap.ic_alcazar4,R.raw.song4));
        city.add(new City("Alcazar Park song5","Larissa City",R.mipmap.ic_alcazar5,R.raw.song5));
        city.add(new City("Alcazar Park song6","Larissa City",R.mipmap.ic_alcazar6,R.raw.song6));
        city.add(new City("Alcazar Park song7","Larissa City",R.mipmap.ic_alcazar7,R.raw.song1));


        ListView list = (ListView)findViewById(R.id.listView);

        CityAdapter adapter = new CityAdapter(this,city,R.color.category_alcazar);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                releaseMediaPlayer();
                City c = city.get(i);

                Log.v("AlcazarActivity","Current row:" + c);

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(AlcazarActivity.this, c.getmAudioResourceId());
                    mediaPlayer.start();

                    mediaPlayer.setOnCompletionListener(mCompletionListener);

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
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;

            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
