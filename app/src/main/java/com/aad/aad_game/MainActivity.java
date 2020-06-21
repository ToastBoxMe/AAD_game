package com.aad.aad_game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    //define variable media
    MediaPlayer mMediaPlayer;
   // ToggleButton toggleButton = new ToggleButton(this);

    //define Button variable
    private Button playButton;
    private Button howToButton;


    //define TextView variable
    private TextView ticTextView;
    private TextView tacTextView;
    private TextView toeTextView;
    private TextView multiplayerTextView;

    //define variable for layout
    RelativeLayout mainLayout;
    AnimationDrawable animationDrawable;

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assigning id to variable
        ticTextView = findViewById(R.id.tic_title);
        tacTextView = findViewById(R.id.tac_title);
        toeTextView = findViewById(R.id.toe_title);
        multiplayerTextView = findViewById(R.id.multiplayer_title);
        playButton = findViewById(R.id.start_play_button);
        howToButton = findViewById(R.id.how_to);

        play();
        animateBackground();
        animateTitle();

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
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGame();
            }
        });
        howToButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openHowTo();
            }
        });
    }

    //method
    public void openHowTo(){
        Intent intent = new Intent(this, HowToDialog.class);
        startActivity(intent);
    }
    public void openGame() {
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
    }

    public void animateTitle() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_title);
        Animation animationMultipalyer = AnimationUtils.loadAnimation(this, R.anim.anim_title_pvp);
        multiplayerTextView.startAnimation(animationMultipalyer);
        ticTextView.startAnimation(animation);
        tacTextView.startAnimation(animation);
        toeTextView.startAnimation(animation);
    }

    public void animateBackground() {
        mainLayout = findViewById(R.id.main_layout);
        animationDrawable = (AnimationDrawable) mainLayout.getBackground();
        animationDrawable.setEnterFadeDuration(4500);
        animationDrawable.setExitFadeDuration(4500);
        animationDrawable.start();
    }

    public void play() {
        if(mMediaPlayer == null){
            mMediaPlayer = MediaPlayer.create(this, R.raw.sneaky_snitch);
        }
        mMediaPlayer.start();
        mMediaPlayer.setLooping(true);
    }
    public void pause(){
        if(mMediaPlayer != null){
            mMediaPlayer.pause();
        }
    }
}


