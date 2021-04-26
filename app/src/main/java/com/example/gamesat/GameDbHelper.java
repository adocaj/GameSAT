package com.example.gamesat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gamesat.GameContract.*;

import java.util.ArrayList;
import java.util.List;

/*
 * Here we created the database and all the needed tables.
 */


public class GameDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "GameSat.db";
    //private static final String DATABASE_NAME = "TEST1.db";
    private static final int DATABASE_VERSION = 5;


    public GameDbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        context.deleteDatabase("TEST1.db");
        context.deleteDatabase("TEST.db");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //------------------------------------------
        // create word questions table

        final String SQL_CREATE_WORD_QUEST_TABLE = "CREATE TABLE IF NOT EXISTS " +
                WordQuestionsTable.TABLE_NAME + " ( " +
                WordQuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WordQuestionsTable.COLUMN_QUESTION + " TEXT, " +
                WordQuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                WordQuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                WordQuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                WordQuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                WordQuestionsTable.COLUMN_LEVEL + " INTEGER" +
                " ) ";

        db.execSQL(SQL_CREATE_WORD_QUEST_TABLE);

        // create a unique index for the table to avoid duplicate questions
        final String SQL_CREATE_WORD_QUEST_INDEX = "CREATE UNIQUE INDEX IF NOT EXISTS " +
                "word_index ON " +  WordQuestionsTable.TABLE_NAME + "(" + WordQuestionsTable.COLUMN_QUESTION + ")";
        db.execSQL(SQL_CREATE_WORD_QUEST_INDEX);

        //----------------------------------------------------------------------

        // create table of users and their completion times

        final String SQL_CREATE_WORD_USERNAME_TIME_TABLE = "CREATE TABLE IF NOT EXISTS " +
                UsersWordCompletionTimeTable.TABLE_NAME + " ( " +
                UsersWordCompletionTimeTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UsersWordCompletionTimeTable.COLUMN_USERNAME + " TEXT, " +
                UsersWordCompletionTimeTable.COLUMN_TIME + " INTEGER" +
                " ) ";
        //*** Time will be stored as an integer and retrieved as a long.

        db.execSQL(SQL_CREATE_WORD_USERNAME_TIME_TABLE); // create the username time table

        // create a unique index for the table to avoid duplicate usernames and times
        final String SQL_CREATE_WORD_USERNAME_TIME_INDEX = "CREATE UNIQUE INDEX IF NOT EXISTS " +
                "username_time_word_index ON " +  UsersWordCompletionTimeTable.TABLE_NAME + "(" + UsersWordCompletionTimeTable.COLUMN_USERNAME
                + "," + UsersWordCompletionTimeTable.COLUMN_TIME + ")";
        db.execSQL(SQL_CREATE_WORD_USERNAME_TIME_INDEX); // we don't want same user, same time to be entered repeatedly


        //----------------------------------------------------------------------

    }

    //*********************************************************************
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + WordQuestionsTable.TABLE_NAME); // drop word questions table

        db.execSQL("DROP TABLE IF EXISTS " + UsersWordCompletionTimeTable.TABLE_NAME); // drop username time table

        onCreate(db);
    }
    //*******************************************************************


    // fill word database with word questions

    public void fillWordQuestTable(){

        //---------------------- Level 1 -------------------------------------------------

        addWordQuestion("Aberration", "anomaly", "remote", "desist", 1, 1);

        addWordQuestion("Abreast", "uncertain", "informed", "above", 2, 1);

        addWordQuestion("Abstain", "entice", "refrain", "abhor", 2, 1);

        addWordQuestion("Abyss", "attract", "entice", "void", 3, 1);

        addWordQuestion("Adept", "proficient", "uncertain", "adorn", 1, 1);

        //--------------------------------------------------------------------------------------------------------------

        //---------------------- Level 2 -------------------------------------------------

        addWordQuestion("Abasement", "anomaly", "belittlement", "aggravate", 2,2);

        addWordQuestion("Abate", "subside", "adorn", "stray", 1,2);

        addWordQuestion("Accession", "acumen", "combustion", "joining",3,2);

        addWordQuestion("Acerbic", "caustic", "edible", "dullard", 1, 2);

        addWordQuestion("Acolyte", "cryptic", "assistant", "euphoria", 2, 2);

        //--------------------------------------------------------------------------------

        //---------------------- Level 3 -------------------------------------------------

        addWordQuestion("Abeyance", "anomaly", "remission", "chisel", 2,3);

        addWordQuestion("Abjure", "reject", "adorn", "stray", 1,3);

        addWordQuestion("Anodyne", "acumen", "combustion", "inoffensive",3,3);

        addWordQuestion("Bilk", "swindle", "gallant", "dullard", 1, 3);

        addWordQuestion("Canard", "cryptic", "gossip", "shard", 2, 3);

    }


    private void addWordQuestion(String word, String opt1, String opt2, String opt3, int ansNr, int level){

        WordQuestion wordQuestion = new WordQuestion(word + " is closest in meaning to:", opt1, opt2, opt3, ansNr, level);

        ContentValues cv = new ContentValues();
        cv.put(WordQuestionsTable.COLUMN_QUESTION, wordQuestion.getQuestion());
        cv.put(WordQuestionsTable.COLUMN_OPTION1, wordQuestion.getOption1());
        cv.put(WordQuestionsTable.COLUMN_OPTION2, wordQuestion.getOption2());
        cv.put(WordQuestionsTable.COLUMN_OPTION3, wordQuestion.getOption3());
        cv.put(WordQuestionsTable.COLUMN_ANSWER_NR, wordQuestion.getAnswerNr());
        cv.put(WordQuestionsTable.COLUMN_LEVEL, wordQuestion.getLevel());

        // now we add the question to the database db
        this.getWritableDatabase().insertWithOnConflict(WordQuestionsTable.TABLE_NAME,null, cv,SQLiteDatabase.CONFLICT_IGNORE);

    }

    public List<WordQuestion> getAllWordQuestions(int level){
        List<WordQuestion> wordQuestionList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + WordQuestionsTable.TABLE_NAME, null);

        if (cursor.moveToFirst()){
            do {
                WordQuestion question = new WordQuestion();
                question.setQuestion(cursor.getString(cursor.getColumnIndex(WordQuestionsTable.COLUMN_QUESTION)));
                question.setOption1(cursor.getString(cursor.getColumnIndex(WordQuestionsTable.COLUMN_OPTION1)));
                question.setOption2(cursor.getString(cursor.getColumnIndex(WordQuestionsTable.COLUMN_OPTION2)));
                question.setOption3(cursor.getString(cursor.getColumnIndex(WordQuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(cursor.getInt(cursor.getColumnIndex(WordQuestionsTable.COLUMN_ANSWER_NR)));
                question.setLevel(cursor.getInt(cursor.getColumnIndex(WordQuestionsTable.COLUMN_LEVEL)));
                if (cursor.getInt(cursor.getColumnIndex(WordQuestionsTable.COLUMN_LEVEL)) == level){
                    wordQuestionList.add(question);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return wordQuestionList;
    }
    //*********************************************************************************************************************8

}
