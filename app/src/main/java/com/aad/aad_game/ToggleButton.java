package com.aad.aad_game;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ToggleButton extends AppCompatActivity {
    MediaPlayer mMediaPlayer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.widget.ToggleButton toggle = (android.widget.ToggleButton) findViewById(R.id.mute_button);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    play();
                } else {
                    pause();
                }
            }
        });
    }
    public void play() {
        if(mMediaPlayer == null){
            mMediaPlayer = MediaPlayer.create(this, R.raw.sneaky_snitch);
        }
        mMediaPlayer.start();
        mMediaPlayer.setLooping(true);
    }

    private void pause(){
        if(mMediaPlayer != null){
            mMediaPlayer.pause();
        }
    }
}

