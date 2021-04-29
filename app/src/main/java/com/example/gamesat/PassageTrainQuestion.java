package com.example.gamesat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PassageTrainQuestion extends AppCompatActivity {

    Button buttonBackPassTrain, buttonPassTrainExit;
    GameDbHelper gameDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passage_train_question);


        buttonBackPassTrain = (Button) findViewById(R.id.buttonBackPassTrain);
        buttonPassTrainExit = (Button) findViewById(R.id.buttonPassTrainExit);

        gameDbHelper = new GameDbHelper(this);
    //------------------------------------------------------------------------------
        buttonBackPassTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //-------------------------------------------------------------
                AlertDialog alertDialog = new AlertDialog.Builder(PassageTrainQuestion.this)

                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Warning")
                        .setMessage("Are you sure you want to leave training?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what would happen when positive button is clicked
                                Intent intent = new Intent(getApplicationContext(), TrainScreen.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what should happen when negative button is clicked
                                Toast.makeText(getApplicationContext(),"Resuming Training.",Toast.LENGTH_LONG).show();
                            }
                        })
                        .show();

                //---------------------------------------------------------------


            }
        });
    //-------------------------------------------------------------------------------

        buttonPassTrainExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.exit(0);
            }
        });

    //---------------------------------------------------------------------------------

    }
}