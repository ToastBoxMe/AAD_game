package com.aad.aad_game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Game extends AppCompatActivity implements View.OnClickListener {
    //variables
    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true; //as soon as the game start player 1 will have the turn
    private int roundCount;

    private int player1Points;
    private int player2Points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //create method
        textViewPlayer1 = findViewById(R.id.player1);
        textViewPlayer2 = findViewById(R.id.player2);

        //looping though all the array
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }

        });
    }

    @Override
    public void onClick(View v) {
        if(!((Button) v).getText().toString().equals("")){
            return;
        }
        if (player1Turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }

        roundCount++;

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
        }else {
            player1Turn = !player1Turn;
        }

    }
    private boolean checkForWin(){
        String[][] field = new String[3][3];
        //check all button
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        //check all horizontal button, if match return true player 1 win
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && field[i][0].equals("")) {
                return true;
            }
        }
        //check all vertical button, if match return true player 1 win
        for (int i = 0; i < 3; i++){
            if (field[0][i].equals(field[1][i]) && field [0][i].equals(field[2][i]) && field[0][i].equals("")){
                return true;
            }
        }
        //check cross value from top left ro bottom right
        if (field[0][0].equals(field[1][1]) && field [0][0].equals(field[2][2]) && field[0][0].equals("")){
            return true;
        }
        //check cross value from bottom left to top right
        if (field[0][2].equals(field[1][1]) && field [0][2].equals(field[2][0]) && field[0][2].equals("")){
            return true;
        }
        return false;
    }

    private void player1Wins(){
        player1Points++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        updatePointText();
        resetBoard();
    }

    private void player2Wins(){
        player2Points++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        updatePointText();
        resetBoard();
    }

    private void draw(){
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointText(){
        textViewPlayer1.setText("Player 1= " + player1Points);
        textViewPlayer2.setText("Player 2= " + player2Points);
    }
    private void resetBoard(){
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++){
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        player1Turn = true;
    }
    private void resetGame(){
        player1Points = 0;
        player2Points = 1;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        savedInstanceState.putInt("roundCount", roundCount);
        savedInstanceState.putInt("player1Points", player1Points);
        savedInstanceState.putInt("player2Points", player2Points);
        savedInstanceState.putBoolean("player1Turn", player1Turn);
    }
}
