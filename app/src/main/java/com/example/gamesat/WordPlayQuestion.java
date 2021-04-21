package com.example.gamesat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    private int scoreWordPlay = 5;
    private int minScore = 0;
    private int maxScore = 30;

    private boolean wordQuestAnswered; // what happens when button is clicked
    private int questCountTotalLev1;
    private int questCounter = 0; // index starts at 0

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

    //--------------------------------------------------------------------
        bConfirmWordPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!wordQuestAnswered){
                    if (rbWordPlay1.isChecked() || rbWordPlay2.isChecked() || rbWordPlay3.isChecked()){
                        //check answer
                        checkWPAnswer();
                    } else {
                        Toast.makeText(WordPlayQuestion.this, "Please select an answer.", Toast.LENGTH_SHORT).show();
                    }
                } else { // question answered
                    showNextWordPlayQuestion();
                }
            }
        });

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
        rbWordPlay1.setTextColor(textClrDefaultRb);
        rbWordPlay2.setTextColor(textClrDefaultRb);
        rbWordPlay3.setTextColor(textClrDefaultRb);
        rbGroupWordPlay.clearCheck();
        //questCounter = 1;

        // if we still have questions, and the score is not 0 or 30
        if (questCounter < questCountTotalLev1 && scoreWordPlay != minScore &&
                scoreWordPlay != maxScore){
            currentWQ = wordQuestionListLev1.get(questCounter);

            textViewWordQuestion.setText(currentWQ.getQuestion()); // display the question
            rbWordPlay1.setText(currentWQ.getOption1());
            rbWordPlay2.setText(currentWQ.getOption2());
            rbWordPlay3.setText(currentWQ.getOption3());

            textViewWordLevel.setText("Level: " + currentWQ.getLevel());
            textViewWordScore.setText("Score: " + scoreWordPlay);

            questCounter += 1;
            wordQuestAnswered = false;
            bConfirmWordPlay.setText("Confirm");

        } else { // score reaches 0 or 30, or no more questions
            finishWordPlay();
        }
    }

    //******************************************************************
    private void checkWPAnswer(){ // chech word play answer
        wordQuestAnswered = true; // question has been answered
        RadioButton rbSelected = findViewById(rbGroupWordPlay.getCheckedRadioButtonId());
        int ansNumSelect = rbGroupWordPlay.indexOfChild(rbSelected) + 1; // add 1 since starts at 0

        if (ansNumSelect == currentWQ.getAnswerNr()){ // if correct choice selected

            // increase score
            scoreWordPlay += 1;
            textViewWordScore.setText("Score: " + scoreWordPlay); // display new score
        } else {
            scoreWordPlay -= 1; // decease score
            textViewWordScore.setText("Score: " + textViewWordScore);
        }

        // show solution regardless
        showSolutionWP();
    }

    //******************************************************************
    private void showSolutionWP(){
        rbWordPlay1.setTextColor(Color.RED);
        rbWordPlay2.setTextColor(Color.RED);
        rbWordPlay3.setTextColor(Color.RED);

        switch (currentWQ.getAnswerNr()){
            case 1:
                rbWordPlay1.setTextColor(Color.WHITE);
                rbWordPlay1.setText(currentWQ.getOption1() + " is correct.");
                break;
            case 2:
                rbWordPlay2.setTextColor(Color.WHITE);
                rbWordPlay2.setText(currentWQ.getOption2() + " is correct.");
                break;
            case 3:
                rbWordPlay3.setTextColor(Color.WHITE);
                rbWordPlay3.setText(currentWQ.getOption3() + " is correct.");
                break;
        }

        if (questCounter < questCountTotalLev1){
            bConfirmWordPlay.setText("Next");
        } else {
            bConfirmWordPlay.setText("Finish");
        }
    }

    //******************************************************************
    private void finishWordPlay() { // could be an advanced to level 2
        // for now go to welcome screen
        Intent intent = new Intent(getApplicationContext(), Welcome.class);
        startActivity(intent);
        finish();
    }




}