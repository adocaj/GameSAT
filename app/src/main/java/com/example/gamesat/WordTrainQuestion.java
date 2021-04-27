package com.example.gamesat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WordTrainQuestion extends AppCompatActivity {

    Button buttonBackWordTrain, buttonWordTrainExit;
    GameDbHelper gameDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_train_question);


        buttonBackWordTrain = (Button) findViewById(R.id.buttonBackWordTrain);
        buttonWordTrainExit = (Button) findViewById(R.id.buttonWordTrainExit);

        gameDbHelper = new GameDbHelper(this);
    //--------------------------------------------------------------------------------
        buttonBackWordTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TrainScreen.class);
                startActivity(intent);
                finish();
            }
        });
    //----------------------------------------------------------------------------

        buttonWordTrainExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameDbHelper.deleteUserLogin(gameDbHelper.getUserName());
                finishAffinity();
                System.exit(0);
            }
        });

    //------------------------------------------------------------------------

    }
}