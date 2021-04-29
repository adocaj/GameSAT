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
    private static final int DATABASE_VERSION = 20; /**  Reset the database num for new tables*/


    public GameDbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //-------------------------------------------------------------------------
        // create table of login data
        final String SQL_CREATE_LOGIN_DATA_TABLE = "CREATE TABLE IF NOT EXISTS " +
                UserLoginDataTable.TABLE_NAME + " ( " +
                UserLoginDataTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UserLoginDataTable.COLUMN_USERNAME + " TEXT, " +
                UserLoginDataTable.COLUMN_PASSWORD + " TEXT" +
                " ) ";

        db.execSQL(SQL_CREATE_LOGIN_DATA_TABLE); // create the login data table

        // create a unique index for the table to avoid duplicate username and passwords
        final String SQL_CREATE_LOGIN_DATA_INDEX = "CREATE UNIQUE INDEX IF NOT EXISTS " +
                "login_data_index ON " +  UserLoginDataTable.TABLE_NAME + "(" + UserLoginDataTable.COLUMN_USERNAME
                + "," + UserLoginDataTable.COLUMN_PASSWORD + ")";
        db.execSQL(SQL_CREATE_LOGIN_DATA_INDEX); // we don't want same user, same password to be entered repeatedly


        //----------------------------------------------------------------------

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
                WordQuestionsTable.COLUMN_LEVEL + " INTEGER, " +
                WordQuestionsTable.COLUMN_CORRECT_VAL + " INTEGER" +
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

        // create a unique index for the table to avoid duplicate username and times
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

        db.execSQL("DROP TABLE IF EXISTS " + UserLoginDataTable.TABLE_NAME); // drop username time table

        onCreate(db);
    }
    //*******************************************************************

    public void fillWordQuestTableIfEmpty(){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(WordQuestionsTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        if (cursor != null && cursor.getCount() > 0){
            return;
        }
        fillWordQuestTable();
    }

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

    //***********************************************************************************************************
    private void addWordQuestion(String word, String opt1, String opt2, String opt3, int ansNr, int level){

        Question wordQuestion = new Question(word + " is closest in meaning to:", opt1, opt2, opt3, ansNr, level);


        ContentValues cv = new ContentValues();
        cv.put(WordQuestionsTable.COLUMN_QUESTION, wordQuestion.getQuestion());
        cv.put(WordQuestionsTable.COLUMN_OPTION1, wordQuestion.getOption1());
        cv.put(WordQuestionsTable.COLUMN_OPTION2, wordQuestion.getOption2());
        cv.put(WordQuestionsTable.COLUMN_OPTION3, wordQuestion.getOption3());
        cv.put(WordQuestionsTable.COLUMN_ANSWER_NR, wordQuestion.getAnswerNr());
        cv.put(WordQuestionsTable.COLUMN_LEVEL, wordQuestion.getLevel());
        cv.put(WordQuestionsTable.COLUMN_CORRECT_VAL, wordQuestion.getCorrectVal()); // all correctVals are zero as default

        // now we add the question to the database db
        this.getWritableDatabase().insertWithOnConflict(WordQuestionsTable.TABLE_NAME,null, cv,SQLiteDatabase.CONFLICT_IGNORE);

    }

    //************************************************************************************************
    public List<Question> getAllWordQuestionsPerLevel(int level){
        List<Question> wordQuestionList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(WordQuestionsTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()){
            do {
                Question question = new Question();
                question.setQuestionID(cursor.getInt(cursor.getColumnIndex(WordQuestionsTable._ID))); // automatically set by android
                question.setQuestion(cursor.getString(cursor.getColumnIndex(WordQuestionsTable.COLUMN_QUESTION)));
                question.setOption1(cursor.getString(cursor.getColumnIndex(WordQuestionsTable.COLUMN_OPTION1)));
                question.setOption2(cursor.getString(cursor.getColumnIndex(WordQuestionsTable.COLUMN_OPTION2)));
                question.setOption3(cursor.getString(cursor.getColumnIndex(WordQuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(cursor.getInt(cursor.getColumnIndex(WordQuestionsTable.COLUMN_ANSWER_NR)));
                question.setLevel(cursor.getInt(cursor.getColumnIndex(WordQuestionsTable.COLUMN_LEVEL)));
                question.setCorrectVal(cursor.getInt(cursor.getColumnIndex(WordQuestionsTable.COLUMN_CORRECT_VAL)));
                if (cursor.getInt(cursor.getColumnIndex(WordQuestionsTable.COLUMN_LEVEL)) == level){
                    wordQuestionList.add(question);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return wordQuestionList;
    }
    //*********************************************************************************************************************



    //************************************************************************************************
    public List<Question> getAllWordQuestions(){

        List<Question> allWordQuestionList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(WordQuestionsTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()){
            do {
                Question question = new Question();
                question.setQuestionID(cursor.getInt(cursor.getColumnIndex(WordQuestionsTable._ID))); // automatically set by android
                question.setQuestion(cursor.getString(cursor.getColumnIndex(WordQuestionsTable.COLUMN_QUESTION)));
                question.setOption1(cursor.getString(cursor.getColumnIndex(WordQuestionsTable.COLUMN_OPTION1)));
                question.setOption2(cursor.getString(cursor.getColumnIndex(WordQuestionsTable.COLUMN_OPTION2)));
                question.setOption3(cursor.getString(cursor.getColumnIndex(WordQuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(cursor.getInt(cursor.getColumnIndex(WordQuestionsTable.COLUMN_ANSWER_NR)));
                question.setLevel(cursor.getInt(cursor.getColumnIndex(WordQuestionsTable.COLUMN_LEVEL)));
                question.setCorrectVal(cursor.getInt(cursor.getColumnIndex(WordQuestionsTable.COLUMN_CORRECT_VAL)));

                allWordQuestionList.add(question);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return allWordQuestionList;
    }
    //*********************************************************************************************************************



    //*********************************************************************************************************************

    public void updateWordQuestionCorrectVal(int questionID, int correctVal){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WordQuestionsTable.COLUMN_CORRECT_VAL, correctVal);

        Cursor cursor = database.query(WordQuestionsTable.TABLE_NAME,
                new String[]{WordQuestionsTable._ID, WordQuestionsTable.COLUMN_CORRECT_VAL},
                WordQuestionsTable._ID + " =?",
                new String[]{String.valueOf(questionID)},
                null,
                null,
                null);
        if (cursor.moveToFirst()){
            if (cursor.getInt(cursor.getColumnIndex(WordQuestionsTable.COLUMN_CORRECT_VAL)) == 0){
                this.getWritableDatabase().updateWithOnConflict(WordQuestionsTable.TABLE_NAME, contentValues,
                        WordQuestionsTable._ID + "=?",
                        new String[]{String.valueOf(questionID)},SQLiteDatabase.CONFLICT_IGNORE);
            }
        }
    }

    //-------------------------------------------------------------------------------------
    public void resetWordQuestionCorrectValues(){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WordQuestionsTable.COLUMN_CORRECT_VAL, 0);

        Cursor cursor = database.query(WordQuestionsTable.TABLE_NAME,
                new String[]{WordQuestionsTable.COLUMN_CORRECT_VAL},
                WordQuestionsTable.COLUMN_CORRECT_VAL + " !=?",
                new String[]{String.valueOf(0)},
                null,
                null,
                null);
        if (cursor.moveToFirst()){
            do {
                this.getWritableDatabase().updateWithOnConflict(WordQuestionsTable.TABLE_NAME, contentValues,
                        WordQuestionsTable.COLUMN_CORRECT_VAL+ " !=? ",
                        new String[]{String.valueOf(0)},SQLiteDatabase.CONFLICT_IGNORE);
            } while (cursor.moveToNext());
        }
    }
    //------------------------------------------------------------------------

    // insert user data into the login database

    //***********************************************************************************************************
    public void insertUserLogin(String username, String password){

        ContentValues cv = new ContentValues();
        cv.put(UserLoginDataTable.COLUMN_USERNAME, username);
        cv.put(UserLoginDataTable.COLUMN_PASSWORD, password);
        // now we add the question to the database db
        this.getWritableDatabase().insertWithOnConflict(UserLoginDataTable.TABLE_NAME,null, cv, SQLiteDatabase.CONFLICT_IGNORE);
    }
    //******************************************
    public void clearAllLoginData(){

        this.getWritableDatabase().delete(UserLoginDataTable.TABLE_NAME, null, null);
    }
    //*****************************************


    //******************************************************************

    public String getUserName(){
        SQLiteDatabase db = this.getReadableDatabase();
        String usernameResult = "";

        Cursor cursor = db.rawQuery("SELECT " + UserLoginDataTable.COLUMN_USERNAME +
                " FROM " + UserLoginDataTable.TABLE_NAME, null);

        if (cursor.moveToFirst()){
            usernameResult = cursor.getString(cursor.getColumnIndex(UserLoginDataTable.COLUMN_USERNAME));
            cursor.close();
            return usernameResult;
        }
        cursor.close();

        return "";
    }


    //*****************************************************************************************
    // insert username and time to complete the game
    public void insertUserNameTimeWordTable(String username, Long time){

        ContentValues cv = new ContentValues();
        cv.put(UsersWordCompletionTimeTable.COLUMN_USERNAME, username);
        cv.put(UsersWordCompletionTimeTable.COLUMN_TIME, time);

        // insert if the user doesn't exist, or if they exist and their time is less than current
        if (doesUserExistInWordTable(username)){
            if (isUserWordTableUpdateNecessary(username, time)){
                deleteUserWordTableRecord(username);
                this.getWritableDatabase().insertWithOnConflict(UsersWordCompletionTimeTable.TABLE_NAME,null, cv, SQLiteDatabase.CONFLICT_IGNORE);
            }
        } else { // add users that do not exist
            this.getWritableDatabase().insertWithOnConflict(UsersWordCompletionTimeTable.TABLE_NAME,null, cv, SQLiteDatabase.CONFLICT_IGNORE);
        }

    }

    //******************************************************

    // does user exist
    public boolean doesUserExistInWordTable(String username){

        SQLiteDatabase db = this.getReadableDatabase();

        // check if a user exists and if their time is larger then current
        Cursor cursor = db.query(UsersWordCompletionTimeTable.TABLE_NAME,
                new String[] {UsersWordCompletionTimeTable.COLUMN_USERNAME},
                UsersWordCompletionTimeTable.COLUMN_USERNAME + "=?",
                new String[]{username},
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.getCount() > 0){ // only if results exist
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    //**********************************************
    public boolean isUserWordTableUpdateNecessary(String username, Long time){

        SQLiteDatabase db = this.getReadableDatabase();

        // check if a user exists and if their time is larger then current
        Cursor cursor = db.query(UsersWordCompletionTimeTable.TABLE_NAME,
                new String[] {UsersWordCompletionTimeTable.COLUMN_USERNAME},
                UsersWordCompletionTimeTable.COLUMN_USERNAME + "=? AND " +
                UsersWordCompletionTimeTable.COLUMN_TIME + ">?",
                new String[]{username, String.valueOf(time)},
                null,
                null,
                null,
                null
                );

        if (cursor != null && cursor.getCount() > 0){ // only if results exist
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }
    //***********************************************************
    // delete user from table
    private void deleteUserWordTableRecord(String username){

        this.getWritableDatabase().delete(UsersWordCompletionTimeTable.TABLE_NAME,
                UsersWordCompletionTimeTable.COLUMN_USERNAME + " =?",
                new String[]{username});
    }
    //******************************************************
    public void clearUserNameTimeWordTable(){
        this.getWritableDatabase().delete(UsersWordCompletionTimeTable.TABLE_NAME, null, null);
    }
    //**************************************************
}
