package com.example.gamesat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

public class GameOver extends AppCompatActivity {

    Button buttonBackGameOver, buttonExitGameOver;
    private int gameScore;
    private int wordScoreExists;
    private int passScoreExists;
    private TextView textViewGameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        buttonBackGameOver = (Button) findViewById(R.id.buttonBackGameOver);
        buttonExitGameOver = (Button) findViewById(R.id.buttonExitGameOver);
        textViewGameOver = findViewById(R.id.textViewGameOver);

        wordScoreExists = getIntent().getIntExtra("wScoreExists", 0);
        passScoreExists = getIntent().getIntExtra("pScoreExists", 0);

        if (wordScoreExists > 0){
            gameScore = getIntent().getIntExtra("gameScoreW",0); // either get a word score
        } else {
            gameScore = getIntent().getIntExtra("gameScoreP", 0); // or a passage score
        }

        showMessage(gameScore); // display game ending message to the screen


        buttonBackGameOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Welcome.class); // go to welcome screen
                startActivity(intent);
                finish();
            }
        });

        buttonExitGameOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }

    private void showMessage(int gameScore){
        if (gameScore == 0){
            textViewGameOver.setText("Game Over.");
        } else {

            textViewGameOver.setText("Congratulations! You won.");
        }
    }

}