package com.example.gamesat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LeaderBoard extends AppCompatActivity {

    Button buttonExitLeaderBoard, buttonBackLeaderBoard;
    private int wBoardSelect, pBoardSelect;
    GameDbHelper gameDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        buttonExitLeaderBoard = findViewById(R.id.buttonExitLeaderBoard);
        buttonBackLeaderBoard = findViewById(R.id.buttonBackLeaderBoard);

        wBoardSelect = getIntent().getIntExtra("wordBoardSelect", 0);
        pBoardSelect = getIntent().getIntExtra("passBoardSelect", 0);

        gameDbHelper = new GameDbHelper(this);

        //-------------------------------------------------------

        buttonBackLeaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlayScreen.class); // go back where I came from
                startActivity(intent);
                finish();
            }
        });

        //----------------------------------------------------------

        buttonExitLeaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.exit(0);
            }
        });

        //----------------------------------------------------------

    }
}