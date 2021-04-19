package com.example.gamesat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WordTrainQuestion extends AppCompatActivity {

    Button buttonBackWordTrain, buttonWordTrainExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_train_question);


        buttonBackWordTrain = (Button) findViewById(R.id.buttonBackWordTrain);
        buttonWordTrainExit = (Button) findViewById(R.id.buttonWordTrainExit);
    //---------------------------------------------------------------------------------
        buttonBackWordTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Welcome.class);
                startActivity(intent);
                finish();
            }
        });
    //----------------------------------------------------------------------------

        buttonWordTrainExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

    //------------------------------------------------------------------------

    }
}