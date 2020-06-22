package com.aad.aad_game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
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
    //declare button
    private Button[][] mButton = new Button[3][3];
    //declare player turn (X)
    private boolean playerTurn = true;
    //count the round
    private int roundCount;
    //point counter
    private int p1PointCounter;
    private int p2PointCounter;
    //view to keep the data of the score (bank)
    private TextView scoreP1;
    private TextView scoreP2;
    //declare emoji
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

        //using sensor
        SensorManager sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            //choosing sensor
            Sensor proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            SensorEventListener sensorEventListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    if (event.values[0] > 1) {
                        //if the sensor is more than 1 cm reset the board
                        resetBoard();
                    }
                }
                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                }
            };
            sensorManager.registerListener(sensorEventListener, proximitySensor, SensorManager.SENSOR_DELAY_FASTEST);
        }

        //looping though all the array
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "b_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                //assigning ID to each button
                mButton[i][j] = findViewById(resID);
                //make them all on lick listener
                mButton[i][j].setOnClickListener(this);
            }
        }
        //implementing reset method
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
            //first turn will always be player 1 and X
            if (playerTurn) {
                ((Button) v).setText("X");
                //implement vibration everytime player add value to the board
                vibration();
            } else {
                ((Button) v).setText("O");
                //implement vibration everytime player add value to the board
                vibration();
                //counting round so that not more than 9
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

    //method to custom show toast
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

    //method on how points will be updated
    @SuppressLint("SetTextI18n")
    private void updatePoint() {
        //store the score in variable
        scoreP1.setText(""+ p1PointCounter);
        scoreP2.setText(""+ p2PointCounter);
    }
    //method to reset board
    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                //set all the board into empty string
                mButton[i][j].setText("");
            }
        }
        roundCount = 0;
        playerTurn = true;
    }
    //method to reset game
    private void resetGame() {
        //vibrate when button is pressed, reset score and point
        vibration();
        p1PointCounter = 0;
        p2PointCounter = 0;
        updatePoint();
    }

    //method changes on winning or draw
    private void player1Wins() {
        p1PointCounter = p1PointCounter + 100;
        //animate the emoji and score
        showToast("Player 1 WIN!!!");
        updatePoint();
        animateEmojiP1();
        animateScoreP1();
        //provide delay so that the last X or O will stay for a while
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
        showToast("Player 2 WIN!!!");
        updatePoint();
        animateEmojiP2();
        animateScoreP2();
        //time in milli second (delay for 0.2 second)
        new CountDownTimer(200, 1000) {
            public void onFinish() {
                resetBoard();
            }

            public void onTick(long millisUntilFinished) {
            }
        }.start();
    }
    //method to check draw
    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        //do animation when draw
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

    //method to enable vibration
    private void vibration() {
        //get service define v as vibrator
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26 - small vibration
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
    //save data when device any orientation state
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCount", roundCount);
        outState.putInt("p1PointCounter", p1PointCounter);
        outState.putInt("p2PointCounter", p2PointCounter);
        outState.putBoolean("playerTurn", playerTurn);
    }

    @Override
    //return the data when the device is rotated
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundCount");
        p1PointCounter = savedInstanceState.getInt("p1PointCounter");
        p2PointCounter = savedInstanceState.getInt("p2PointCounter");
        playerTurn = savedInstanceState.getBoolean("playerTurn");
    }
}




