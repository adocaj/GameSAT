package com.example.gamesat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PassagePlayQuestion extends AppCompatActivity {

    Button buttonBackPassPlay, buttonPassPlayExit;
    GameDbHelper gameDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passage_play_question);

        buttonBackPassPlay = (Button) findViewById(R.id.buttonBackPassPlay);
        buttonPassPlayExit = (Button) findViewById(R.id.buttonPassPlayExit);

        gameDbHelper = new GameDbHelper(this);
    //-----------------------------------------------------------------------------------
        buttonBackPassPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //-------------------------------------------------------------
                AlertDialog alertDialog = new AlertDialog.Builder(PassagePlayQuestion.this)

                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Warning")
                        .setMessage("Are you sure you want to leave play?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what would happen when positive button is clicked
                                Intent intent = new Intent(getApplicationContext(), PlayScreen.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what should happen when negative button is clicked
                                Toast.makeText(getApplicationContext(),"Resuming Play.",Toast.LENGTH_LONG).show();
                            }
                        })
                        .show();

                //---------------------------------------------------------------


            }
        });
    //-----------------------------------------------------------------------------------

        buttonPassPlayExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.exit(0);
            }
        });

    //-------------------------------------------------------------------

    }
}