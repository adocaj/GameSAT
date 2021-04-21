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

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + WordQuestionsTable.TABLE_NAME);

        onCreate(db);
    }
    //*******************************************************************


    // fill word database with word questions

    public void fillWordQuestTable(){

        WordQuestion wq1 = new WordQuestion("Aberration is closest in meaning to:", "anomaly", "remote", "desist", 1, 1);
        addWordQuestion(wq1);

        WordQuestion wq2 = new WordQuestion("Abreast is closest in meaning to:", "uncertain", "informed", "above", 2, 1);
        addWordQuestion(wq2);

        WordQuestion wq3 = new WordQuestion("Abstain is closest in meaning to:", "entice", "refrain", "abhor", 2, 1);
        addWordQuestion(wq3);

        WordQuestion wq4 = new WordQuestion("Abyss is closest in meaning to:", "attract", "entice", "void", 3, 1);
        addWordQuestion(wq4);

        WordQuestion wq5 = new WordQuestion("Adept is closest in meaning to:", "proficient", "uncertain", "adorn", 1, 1);
        addWordQuestion(wq5);

    }


    private void addWordQuestion(WordQuestion question){

        ContentValues cv = new ContentValues();
        cv.put(WordQuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(WordQuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(WordQuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(WordQuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(WordQuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(WordQuestionsTable.COLUMN_LEVEL, question.getLevel());

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


}
