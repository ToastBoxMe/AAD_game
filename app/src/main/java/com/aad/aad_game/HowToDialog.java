package com.aad.aad_game;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class HowToDialog extends AppCompatDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        //add pop up dialod that show the rule of the game
        dialog.setTitle("Rules")
                .setMessage("The object of Tic Tac Toe is to get three in a row."+"\n\n"+"You play on a three by three game board."
                        +"\n\n"+"The first player is known as X and the second is O."+"\n\n"+
                        "Players alternate placing Xs and Os on the game board until either oppent has three in a row or all nine squares are filled."
                +"\n\n"+"X always goes first, and in the event that no one has three in a row, the stalemate is called a cat game.");
        return dialog.create();
    }
}
