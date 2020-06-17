package com.aad.aad_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button playButton;
    private TextView ticTextView;
    private TextView tacTextView;
    private TextView toeTextView;
    private TextView multiplayerTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            ticTextView = findViewById(R.id.tic_title);
            tacTextView = findViewById(R.id.tac_title);
            toeTextView = findViewById(R.id.toe_title);
            multiplayerTextView = findViewById(R.id.multiplayer_title);

            animateTitle();

        //click play
        playButton = findViewById(R.id.start_play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openGame();
            }
        });
    }
    public void openGame () {
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
    }
    public void animateTitle (){
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_title);
        Animation animationMultipalyer = AnimationUtils.loadAnimation(this, R.anim.anim_title_multiplayer);
        multiplayerTextView.startAnimation(animationMultipalyer);
        ticTextView.startAnimation(animation);
        tacTextView.startAnimation(animation);
        toeTextView.startAnimation(animation);
    }
}
