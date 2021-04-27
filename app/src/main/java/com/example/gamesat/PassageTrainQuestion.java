package com.example.gamesat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
                Intent intent = new Intent(getApplicationContext(), TrainScreen.class);
                startActivity(intent);
                finish();
            }
        });
    //-------------------------------------------------------------------------------

        buttonPassTrainExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameDbHelper.deleteUserLogin(gameDbHelper.getUserName());
                finishAffinity();
                System.exit(0);
            }
        });

    //---------------------------------------------------------------------------------

    }
}