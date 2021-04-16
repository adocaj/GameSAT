package com.example.gamesat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

public class PlayScreen extends AppCompatActivity {

    Button buttonBackPlay, buttonPlayWord, buttonPlayPassage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_screen);

        buttonBackPlay = (Button) findViewById(R.id.buttonBackPlay);
        buttonPlayWord = (Button) findViewById(R.id.buttonPlayWord);
        buttonPlayPassage = (Button) findViewById(R.id.buttonPlayPassage);


        buttonBackPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Welcome.class);
                startActivity(intent);
                finish();
            }
        });


        buttonPlayWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WordPlayQuestion.class);
                startActivity(intent);
                finish();
            }
        });

        buttonPlayPassage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PassagePlayQuestion.class);
                startActivity(intent);
                finish();
            }
        });

    }
}