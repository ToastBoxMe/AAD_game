package com.aad.aad_game;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Game extends AppCompatActivity implements View.OnClickListener {
    //variables
    private Button[][] mButton = new Button[3][3];
    private boolean player1Turn = true; //as soon as the game start player 1 will have the turn
    private int roundCount;

    private int player1Points;
    private int player2Points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
        //assign ID
        textViewPlayer1 = findViewById(R.id.player1_score);
        textViewPlayer2 = findViewById(R.id.player2_score);

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


    @Override
    public void onClick(View v) {
        //if button is not empty
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
            if (player1Turn) {
                ((Button) v).setText("X");
                vibration();
            } else {
                ((Button) v).setText("O");
                vibration();
            } roundCount++;


        if (checkForWin()) {
            //after check method, if it is player 1, means player 1 win
            if (player1Turn) {
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
            player1Turn = !player1Turn;
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
        textViewPlayer1.setText(""+player1Points);
        textViewPlayer2.setText(""+player2Points);
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mButton[i][j].setText("");
            }
        }
        roundCount = 0;
        player1Turn = true;
    }

    private void resetGame() {
        vibration();
        player1Points = 0;
        player2Points = 0;
        updatePoint();
    }

    //method changes on winning or draw ==================================================================================
    private void player1Wins() {
        player1Points = player1Points + 100;
        //Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        showToast("Player 1 WIN!!!");
        updatePoint();
        new CountDownTimer(3000, 1000) {
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
        player2Points = player2Points + 100;
        //Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        showToast("Player 2 WIN!!!");
        updatePoint();
        new CountDownTimer(3000, 1000) {
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

}
