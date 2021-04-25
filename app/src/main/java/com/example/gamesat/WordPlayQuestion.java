package com.example.gamesat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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
import java.util.Random;


/*
 * The screen where the word play will occur.
 */
public class WordPlayQuestion extends AppCompatActivity {

    Button buttonBackWordPlay, buttonWordPlayExit;

    private final int DarkGreen = 0xFF006400; // FF is for transparency, rest is rgb

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
    private int scoreWordPlay = 3;
    private int minScore = 0;
    private int maxScore = 5;

    private boolean wordQuestAnswered; // what happens when button is clicked
    private int wordQCountLev1; // word questions count level 1
    private int questIndex = 0; // index starts at 0
    private int prevIndex = 0;
    private String userName_;

    //-----------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_play_question);

        buttonBackWordPlay = (Button) findViewById(R.id.buttonBackWordPlay);
        buttonWordPlayExit = (Button) findViewById(R.id.buttonWordPlayExit);

        //--------------||||-------
        userName_ = getIntent().getStringExtra("usernameWP");
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

        wordQCountLev1 = wordQuestionListLev1.size(); // total number of questions
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

        //--- find question index
        questIndex = findQuestIndex();

        // if we the score is not 0 or 30
        if (scoreWordPlay != minScore && scoreWordPlay != maxScore){

            currentWQ = wordQuestionListLev1.get(questIndex); // get word question

            textViewWordQuestion.setText(currentWQ.getQuestion()); // display the question
            rbWordPlay1.setText(currentWQ.getOption1());
            rbWordPlay2.setText(currentWQ.getOption2());
            rbWordPlay3.setText(currentWQ.getOption3());

            //***********|||||||||||||||||||||||***************************
            textViewWordLevel.setText("Level: " + currentWQ.getLevel());
            //textViewWordLevel.setText(userName_);
            //textViewWordLevel.setText("Index: " + questIndex);
            textViewWordScore.setText("Score: " + scoreWordPlay);

            wordQuestAnswered = false;
            bConfirmWordPlay.setText("Confirm");

        } else { // score reaches 0 or 30, or no more questions
            finishWordPlay();
        }
    }


    //**************************************************
    private int findQuestIndex() {
        Random random = new Random();

        int randomIndex = random.nextInt(wordQCountLev1);

        while (prevIndex == randomIndex) { // next quest is different from previous one
            randomIndex = random.nextInt(wordQCountLev1);
        }
        prevIndex = randomIndex;

        return randomIndex;
    }

    //******************************************************************
    private void checkWPAnswer(){ // check word play answer
        wordQuestAnswered = true; // question has been answered
        RadioButton rbSelected = findViewById(rbGroupWordPlay.getCheckedRadioButtonId());
        int ansNumSelect = rbGroupWordPlay.indexOfChild(rbSelected) + 1; // add 1 since starts at 0

        if (ansNumSelect == currentWQ.getAnswerNr()){ // if correct choice selected

            // increase score
            scoreWordPlay += 1;


        } else {
            scoreWordPlay -= 1; // decease score
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
                rbWordPlay1.setTextColor(DarkGreen);
                rbWordPlay1.setText(currentWQ.getOption1() + " is correct.");
                break;
            case 2:
                rbWordPlay2.setTextColor(DarkGreen);
                rbWordPlay2.setText(currentWQ.getOption2() + " is correct.");
                break;
            case 3:
                rbWordPlay3.setTextColor(DarkGreen);
                rbWordPlay3.setText(currentWQ.getOption3() + " is correct.");
                break;
        }

        if (scoreWordPlay != minScore && scoreWordPlay != maxScore){
            bConfirmWordPlay.setText("Next");
        } else {
            bConfirmWordPlay.setText("Finish");
        }
    }

    //******************************************************************
    private void finishWordPlay() { // could be an advanced to level 2
        // for now go to welcome screen
        Intent intent = new Intent(getApplicationContext(), GameOver.class);
        startActivity(intent);
        finish();
    }




}