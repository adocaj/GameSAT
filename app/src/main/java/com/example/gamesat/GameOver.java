package com.example.gamesat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameOver extends AppCompatActivity {

    Button buttonBackGameOver, buttonExitGameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        buttonBackGameOver = (Button) findViewById(R.id.buttonBackGameOver);
        buttonExitGameOver = (Button) findViewById(R.id.buttonExitGameOver);

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
}