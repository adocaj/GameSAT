package com.example.gamesat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PassageTrainQuestion extends AppCompatActivity {

    Button buttonBackPassTrain, buttonPassTrainExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passage_train_question);


        buttonBackPassTrain = (Button) findViewById(R.id.buttonBackPassTrain);
        buttonPassTrainExit = (Button) findViewById(R.id.buttonPassTrainExit);
    //------------------------------------------------------------------------------
        buttonBackPassTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Welcome.class);
                startActivity(intent);
                finish();
            }
        });
    //-------------------------------------------------------------------------------

        buttonPassTrainExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

    //---------------------------------------------------------------------------------

    }
}