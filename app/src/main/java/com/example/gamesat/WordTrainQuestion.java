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

import java.util.List;

public class WordTrainQuestion extends AppCompatActivity {

    Button buttonBackWordTrain, buttonWordTrainExit;
    private final int DarkGreen = 0xFF006400; // FF is for transparency, rest is rgb

    //-------------------------------------------
    private TextView textViewWordTrainQuestion;
    private RadioGroup radioGroupWordTrain;
    private RadioButton rbWordTrain1;
    private RadioButton rbWordTrain2;
    private RadioButton rbWordTrain3;
    private Button bConfirmWordTrain;

    private List<Question> allWordQuestionList;
    private List<Question> trainWordQuestionList;

    private ColorStateList rbDefaultColorWT;

    private Question currentWQ;

    private boolean wordQuestAnswered;
    private int totalWordQuestionCount;

    private int questIndex = 0;
    //-------------------------------------------

    GameDbHelper gameDbHelper;

    //------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_train_question);


        buttonBackWordTrain = (Button) findViewById(R.id.buttonBackWordTrain);
        buttonWordTrainExit = (Button) findViewById(R.id.buttonWordTrainExit);

        //----------------------------------------------------------
        textViewWordTrainQuestion = findViewById(R.id.text_vew_word_question_train);
        radioGroupWordTrain = findViewById(R.id.radio_group_word_train);
        rbWordTrain1 = findViewById(R.id.radio_button_word_train1);
        rbWordTrain2 = findViewById(R.id.radio_button_word_train2);
        rbWordTrain3 = findViewById(R.id.radio_button_word_train3);
        //-----------------------------------------------------

        rbDefaultColorWT = rbWordTrain1.getTextColors(); // store the default button color
        //-------------------------------------------------

        gameDbHelper = new GameDbHelper(this);
        gameDbHelper.fillWordQuestTableIfEmpty();

    //--------------------------------------------------------------------------------
        buttonBackWordTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TrainScreen.class);
                startActivity(intent);
                finish();
            }
        });
    //----------------------------------------------------------------------------

        buttonWordTrainExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.exit(0);
            }
        });

    //------------------------------------------------------------------------

    }
}