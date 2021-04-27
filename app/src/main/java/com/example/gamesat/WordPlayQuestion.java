package com.example.gamesat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;


/*
 * The screen where the word play will occur.
 */
public class WordPlayQuestion extends AppCompatActivity {

    Button buttonBackWordPlay, buttonWordPlayExit;

    private final int DarkGreen = 0xFF006400; // FF is for transparency, rest is rgb

    //----------------------------------------------------
    // countdown variables
    private static final long CountDown_In_Millis_Lev_1 = 30000; // 30 seconds for level 1
    private static final long CountDown_In_Millis_Lev_2 = 20000; // 20 seconds for level 2
    private static final long CountDown_In_Millis_Lev_3 = 10000; // 10 seconds for level 3

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

    private List<Question> wordQuestionList; // question outcome color

    private ColorStateList textClrDefaultRb;
    private ColorStateList colorDefaultWCd; // countdown color default

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    private Question currentWQ;//current word question
    private int wordPlayScore = 3;
    private int minScore = 0;
    private int maxScore = 15;

    private boolean wordQuestAnswered; // what happens when button is clicked
    private int wordQuestionCount; // word questions count for a level
    private int level = 1; // start at level 1

    private int questIndex = 0; // index starts at 0
    private int prevIndex = 0;
    private String userName_;
    private long wordGameTime = 0L;

    GameDbHelper dbHelper;

    //-----------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_play_question);

        buttonBackWordPlay = findViewById(R.id.buttonBackWordPlay);
        buttonWordPlayExit = findViewById(R.id.buttonWordPlayExit);

        //-----------------------------------------------------------------

        textViewWordQuestion = findViewById(R.id.textViewWordQuestion);
        textViewWordScore = findViewById(R.id.textViewWordScore);
        textViewWordLevel = findViewById(R.id.textViewWordLevel);
        textViewWordCountDown = findViewById(R.id.textViewWordCountDown);
        rbGroupWordPlay = findViewById(R.id.rbGroupWordPlay);
        rbWordPlay1 = findViewById(R.id.rbWordPlay1);
        rbWordPlay2 = findViewById(R.id.rbWordPlay2);
        rbWordPlay3 = findViewById(R.id.rbWordPlay3);
        bConfirmWordPlay = findViewById(R.id.bConfirmWordPlay);
        //-----------------------------------------------------------
        textClrDefaultRb = rbWordPlay1.getTextColors();
        colorDefaultWCd = textViewWordCountDown.getTextColors();
        //--------------------------------------------------------------------

        dbHelper = new GameDbHelper(this);

        //--------------||||-------
        userName_ = dbHelper.getUserName();
        //--------------------------------------------------------------------

        dbHelper.fillWordQuestTable(); // populate the word table with questions

        setWordQuestionList(level);

        wordQuestionCount = wordQuestionList.size();
        Collections.shuffle(wordQuestionList);

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
                Intent intent = new Intent(getApplicationContext(), PlayScreen.class);
                startActivity(intent);
                finish();
            }
        });
    //--------------------------------------------------------------------------------------

        buttonWordPlayExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteUserLogin(userName_);
                finishAffinity();
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


        // if the score is not minScore or maxScore
        if (wordPlayScore != minScore && wordPlayScore != maxScore){

            //------------------------ set the timer and level ---
            if (0 < wordPlayScore && wordPlayScore < 5){
                timeLeftInMillis = CountDown_In_Millis_Lev_1;
                level = 1;
            } else if (5 <= wordPlayScore && wordPlayScore < 10){
                timeLeftInMillis = CountDown_In_Millis_Lev_2;
                level = 2;
            } else if (10 <= wordPlayScore && wordPlayScore < 15){
                timeLeftInMillis = CountDown_In_Millis_Lev_3;
                level = 3;
            }

            setWordQuestionList(level); // select the right list

            //--- find question index, a unique random value
            questIndex = findQuestIndex();
            currentWQ = wordQuestionList.get(questIndex); // get word question

            textViewWordQuestion.setText(currentWQ.getQuestion()); // display the question
            rbWordPlay1.setText(currentWQ.getOption1());
            rbWordPlay2.setText(currentWQ.getOption2());
            rbWordPlay3.setText(currentWQ.getOption3());

            //***********|||||||||||||||||||||||***************************
            textViewWordLevel.setText("Level: " + currentWQ.getLevel());
            //textViewWordLevel.setText(userName_);
            //textViewWordLevel.setText("TimeVar: " + wordGameTime);
            textViewWordScore.setText("Score: " + wordPlayScore);

            wordQuestAnswered = false;
            bConfirmWordPlay.setText("Confirm");

            startCountDown();

        } else { // score reaches 0 or 30, or no more questions
            finishWordPlay();
        }
    }

    //***************************************************
    private void startCountDown(){
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();

            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
                checkWPAnswer(); // so we don't lose the answer chosen
            }
        }.start();
    }

    //************************************************

    private void updateCountDownText(){

        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60; // seconds left after minutes has been extracted

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        textViewWordCountDown.setText(timeFormatted);

        if (timeLeftInMillis < 5000){ // change color if less than 5 seconds
            textViewWordCountDown.setTextColor(Color.RED);
        }else {
            textViewWordCountDown.setTextColor(colorDefaultWCd);
        }
    }


    //**************************************************
    private int findQuestIndex() {
        Random random = new Random();


        int randomIndex = random.nextInt(wordQuestionCount);

        while (prevIndex == randomIndex) { // next quest is different from previous one
            randomIndex = random.nextInt(wordQuestionCount);
        }
        prevIndex = randomIndex;

        return randomIndex;
    }
    //***************************************************************
    private void setWordQuestionList(int level){
        switch (level){
            case 1: // if level is 1 get level 1 questions
                wordQuestionList = dbHelper.getAllWordQuestions(1);
                break;
            case 2:
                wordQuestionList = dbHelper.getAllWordQuestions(2);
                break;
            case 3:
                wordQuestionList = dbHelper.getAllWordQuestions(3);
                break;
        }
    }


    //******************************************************************
    private void checkWPAnswer(){ // check word play answer
        wordQuestAnswered = true; // question has been answered

        countDownTimer.cancel(); // stop the timer once the answer has been locked in

        switch (level){
            case 1:
                wordGameTime += (CountDown_In_Millis_Lev_1 - timeLeftInMillis);
                break;
            case 2:
                wordGameTime += (CountDown_In_Millis_Lev_2 - timeLeftInMillis);
                break;
            case 3:
                wordGameTime += (CountDown_In_Millis_Lev_3 - timeLeftInMillis);
                break;
        }


        RadioButton rbSelected = findViewById(rbGroupWordPlay.getCheckedRadioButtonId());
        int ansNumSelect = rbGroupWordPlay.indexOfChild(rbSelected) + 1; // add 1 since starts at 0

        if (ansNumSelect == currentWQ.getAnswerNr()){ // if correct choice selected

            // increase score
            wordPlayScore += 1;


        } else {
            wordPlayScore -= 1; // decrease score
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

        if (wordPlayScore != minScore && wordPlayScore != maxScore){
            bConfirmWordPlay.setText("Next");
        } else {
            bConfirmWordPlay.setText("Finish");
        }
    }

    //******************************************************************
    private void finishWordPlay() { // could be an advanced to level 2
        // for now go to welcome screen
        Intent intent = new Intent(getApplicationContext(), GameOver.class);
        intent.putExtra("wScoreExists", 1);
        intent.putExtra("gameScoreW", wordPlayScore);

        /** put the user name and time on the table here*/

        startActivity(intent);
        finish();
    }

    protected void onDestroy(){
        super.onDestroy();
        if (countDownTimer != null){
            countDownTimer.cancel();
        }
    }



}