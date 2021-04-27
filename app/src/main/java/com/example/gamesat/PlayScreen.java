package com.example.gamesat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

public class PlayScreen extends AppCompatActivity {

    Button buttonBackPlay, buttonPlayWord, buttonPlayPassage, buttonPlayExit;
    Button bWordLeaderBoard, bPassLeaderBoard;

    String username;

    GameDbHelper gameDbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_screen);

        buttonBackPlay = findViewById(R.id.buttonBackPlay);
        buttonPlayWord = findViewById(R.id.buttonPlayWord);
        buttonPlayPassage = findViewById(R.id.buttonPlayPassage);
        buttonPlayExit = findViewById(R.id.buttonPlayExit);

        bWordLeaderBoard = findViewById(R.id.bWordLeaderBoard);
        bPassLeaderBoard = findViewById(R.id.bPassLeaderBoard);

        gameDbHelper = new GameDbHelper(this);
        //usernameP = getIntent().getStringExtra("userN");
        //passwordP = getIntent().getStringExtra("passW");
        //gameDbHelper.insertUserLogin(usernameP,passwordP);

        username = gameDbHelper.getUserName();
        buttonPlayPassage.setText(username);

    //----------------------------------------------------------------------------------
        buttonBackPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Welcome.class);
                startActivity(intent);
                finish();
            }
        });
    //-----------------------------------------------------------------------------------------

        buttonPlayWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WordPlayQuestion.class);
                intent.putExtra("usernameWP", username);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //finish();
            }
        });
    //-------------------------------------------------------------------------------------
        buttonPlayPassage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PassagePlayQuestion.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("usernamePP", username); // we need to record user's performance
                startActivity(intent);
                //finish();
            }
        });
    //---------------------------------------------------------------------------------
        buttonPlayExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                finishAffinity();
                System.exit(0);
            }
        });


    //------------------------------------------------------------------------------

        bWordLeaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LeaderBoard.class); // go to leaderboard
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("wordBoardSelect", 1);

                startActivity(intent);
                //finish();
            }
        });


    //-------------------------------------------------------------------

        bPassLeaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LeaderBoard.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("passBoardSelect", 1);

                startActivity(intent);
                //finish();
            }
        });

    //--------------------------------------------------------------

    }


}