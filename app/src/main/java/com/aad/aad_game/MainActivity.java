package com.aad.aad_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //define Button variable
    private Button playButton;

    //define TextView variable
    private TextView ticTextView;
    private TextView tacTextView;
    private TextView toeTextView;
    private TextView multiplayerTextView;

    //define variable
    boolean pressed;

    //define variable for layout
    RelativeLayout mainLayout;
    AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assigning id to variable
        ticTextView = findViewById(R.id.tic_title);
        tacTextView = findViewById(R.id.tac_title);
        toeTextView = findViewById(R.id.toe_title);
        multiplayerTextView = findViewById(R.id.multiplayer_title);

        pressed = true;

        animateBackground();
        animateTitle();

        //assign ID to button
        playButton = findViewById(R.id.start_play_button);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGame();
            }
        });
    }

    //method
    public void openGame() {
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
    }

    public void animateTitle() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_title);
        Animation animationMultipalyer = AnimationUtils.loadAnimation(this, R.anim.anim_title_multiplayer);
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
}

