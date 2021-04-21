package com.example.gamesat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;


/*
 * The screen where the word play will occur.
 */
public class WordPlayQuestion extends AppCompatActivity {

    Button buttonBackWordPlay, buttonWordPlayExit;

    //------------------------------------------------------

    private TextView textViewWordQuestion;
    private TextView textViewWordScore;
    private TextView textViewWordLevel;
    private TextView textViewWordCountDown;
    private RadioGroup rbGroupWordPlay;
    private RadioButton rbWordPlay1;
    private RadioButton rbWordPlay2;
    private RadioButton rbWordPlay3;
    private Button bConfirmWordPlay;

    private List<WordQuestion> wordQuestionListLev1; // question outcome color

    private ColorStateList textClrDefaultRb;

    private WordQuestion currentWQ;//current word question
    private int scoreWordPlay;
    private boolean wordQuestAnswered; // what happens when button is clicked
    private int questCountTotalLev1;
    private int questCounter;

    //-----------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_play_question);

        buttonBackWordPlay = (Button) findViewById(R.id.buttonBackWordPlay);
        buttonWordPlayExit = (Button) findViewById(R.id.buttonWordPlayExit);
        //--------------------------------------------------------------------

        textViewWordQuestion = findViewById(R.id.textViewWordQuestion);
        textViewWordScore = findViewById(R.id.textViewWordScore);
        textViewWordLevel = findViewById(R.id.textViewWordLevel);
        textViewWordCountDown = findViewById(R.id.textViewWordCountDown);
        rbGroupWordPlay = findViewById(R.id.rbGroupWordPlay);
        rbWordPlay1 = findViewById(R.id.rbWordPlay1);
        rbWordPlay2 = findViewById(R.id.rbWordPlay2);
        rbWordPlay3 = findViewById(R.id.rbWordPlay3);
        bConfirmWordPlay = findViewById(R.id.bConfirmWordPlay);

        textClrDefaultRb = rbWordPlay1.getTextColors();
        //--------------------------------------------------------------------

        GameDbHelper dbHelper = new GameDbHelper(this);

        dbHelper.fillWordQuestTable();
        wordQuestionListLev1 = dbHelper.getAllWordQuestions(1); // create db and store all questions on list

        questCountTotalLev1 = wordQuestionListLev1.size(); // total number of questions
        Collections.shuffle(wordQuestionListLev1);

        showNextWordPlayQuestion();

        //---------------------------------------------------------------------
    //-----------------------------------------------------------------------------------
        buttonBackWordPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Welcome.class);
                startActivity(intent);
                finish();
            }
        });
    //--------------------------------------------------------------------------------------

        buttonWordPlayExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

    //-------------------------------------------------------------------------------------

    }

    private void showNextWordPlayQuestion(){
        //rbWordPlay1.setTextColor(textClrDefaultRb);
        //rbWordPlay2.setTextColor(textClrDefaultRb);
        //rbWordPlay3.setTextColor(textClrDefaultRb);
        rbGroupWordPlay.clearCheck();
        questCounter = 1;

        if (questCounter < questCountTotalLev1){
            currentWQ = wordQuestionListLev1.get(questCounter);

            textViewWordQuestion.setText(currentWQ.getQuestion()); // display the question
            rbWordPlay1.setText(currentWQ.getOption1());
            rbWordPlay2.setText(currentWQ.getOption2());
            rbWordPlay3.setText(currentWQ.getOption3());

            textViewWordLevel.setText("Level: " + currentWQ.getLevel());

            questCounter += 1;
            wordQuestAnswered = false;

        } else {
            finishWordPlay();
        }
    }

    private void finishWordPlay() { // could be an advanced to level 2
        finish();
    }




}