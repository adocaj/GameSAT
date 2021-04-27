package com.example.gamesat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
                Intent intent = new Intent(getApplicationContext(), PlayScreen.class);
                startActivity(intent);
                finish();
            }
        });
    //-----------------------------------------------------------------------------------

        buttonPassPlayExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameDbHelper.deleteUserLogin(gameDbHelper.getUserName());
                finishAffinity();
                System.exit(0);
            }
        });

    //-------------------------------------------------------------------

    }
}