package com.aad.aad_game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Game extends AppCompatActivity implements View.OnClickListener {
    private Button[][] mButton = new Button[3][3];
    private boolean playerTurn = true; //as soon as the game start player 1 will have the turn
    private int roundCount;

    private int p1PointCounter;
    private int p2PointCounter;

    private TextView scoreP1;
    private TextView scoreP2;

    private ImageView faceNormalP;
    private ImageView faceXP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
        //assign ID to variable
        scoreP1 = findViewById(R.id.player1_score);
        scoreP2 = findViewById(R.id.player2_score);
        faceNormalP = findViewById(R.id.face_p1);
        faceXP = findViewById(R.id.face_p2);

        //looping though all the array
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "b_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                mButton[i][j] = findViewById(resID);
                mButton[i][j].setOnClickListener(this);
            }
        }
        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
                resetBoard();
            }

        });
    }
    //onClick
    @Override
    public void onClick(View v) {
        //if button is not empty
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
            if (playerTurn) {
                ((Button) v).setText("X");
                vibration();
            } else {
                ((Button) v).setText("O");
                vibration();
            } roundCount++;


        if (checkForWin()) {
            //after check method, if it is player 1, means player 1 win
            if (playerTurn) {
                player1Wins();
                //else player 2 win
            } else {
                player2Wins();
            }
            //if the round reach 9 turn, the game draw
        } else if (roundCount == 9) {
            draw();
            //change value of player 1 into false (changing turn)
        } else {
            playerTurn = !playerTurn;
        }

    }

    //method to show toast ========================================================================================
    public void showToast(String text_to_player) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.activity_toast, (ViewGroup) findViewById(R.id.toast_layout));
        TextView toastText = layout.findViewById(R.id.toast_text);
        ImageView toastImage = layout.findViewById(R.id.toast_image);
        toastText.setText(text_to_player);
        toastImage.setImageResource(R.drawable.ic_toast_icon_win);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    //method on how points will be updated ===========================================================================
    @SuppressLint("SetTextI18n")
    private void updatePoint() {
        scoreP1.setText(""+ p1PointCounter);
        scoreP2.setText(""+ p2PointCounter);
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mButton[i][j].setText("");
            }
        }
        roundCount = 0;
        playerTurn = true;
    }

    private void resetGame() {
        vibration();
        p1PointCounter = 0;
        p2PointCounter = 0;
        updatePoint();
    }

    //method changes on winning or draw ==================================================================================
    private void player1Wins() {
        p1PointCounter = p1PointCounter + 100;
        //Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        showToast("Player 1 WIN!!!");
        updatePoint();
        animateEmojiP1();
        animateScoreP1();
        new CountDownTimer(200, 1000) {
            public void onFinish() {
                // When timer is finished
                resetBoard();
            }

            public void onTick(long millisUntilFinished) {
                // millisUntilFinished    The amount of time until finished.
            }
        }.start();

    }

    private void player2Wins() {
        p2PointCounter = p2PointCounter + 100;
        //Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        showToast("Player 2 WIN!!!");
        updatePoint();
        animateEmojiP2();
        animateScoreP2();
        new CountDownTimer(200, 1000) {
            public void onFinish() {
                // When timer is finished
                resetBoard();
            }

            public void onTick(long millisUntilFinished) {
                // millisUntilFinished    The amount of time until finished.
            }
        }.start();

    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        animateEmojiP2();
        animateScoreP2();
        animateEmojiP1();
        animateScoreP1();
        resetBoard();
    }

    //method algorithm logic for games ===================================================================================
    private boolean checkForWin() {
        String[][] field = new String[3][3];
        //check all button
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = mButton[i][j].getText().toString();
            }
        }
        //check all horizontal button, if match return true player 1 win
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals("")) {
                return true;
            }
        }
        //check all vertical button, if match return true player 1 win
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals("")) {
                return true;
            }
        }
        //check cross value from top left ro bottom right
        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")) {
            return true;
        }
        //check cross value from bottom left to top right
        if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("")) {
            return true;
        }
            return false;
    }

    //method to enable vibration=========================================================================
    private void vibration() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(50);
        }
    }
    //animation method
    public void animateEmojiP1() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_emoji);
        faceNormalP.startAnimation(animation);
    }
    public void animateEmojiP2() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_emoji);
        faceXP.startAnimation(animation);
    }
    public void animateScoreP1() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_score);
        scoreP1.startAnimation(animation);
    }
    public void animateScoreP2() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_score);
        scoreP2.startAnimation(animation);
    }

    //orientation method
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCount", roundCount);
        outState.putInt("p1PointCounter", p1PointCounter);
        outState.putInt("p2PointCounter", p2PointCounter);
        outState.putBoolean("playerTurn", playerTurn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundCount");
        p1PointCounter = savedInstanceState.getInt("p1PointCounter");
        p2PointCounter = savedInstanceState.getInt("p2PointCounter");
        playerTurn = savedInstanceState.getBoolean("playerTurn");
    }
}




