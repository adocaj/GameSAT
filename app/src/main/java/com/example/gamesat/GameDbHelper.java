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
    private static final int DATABASE_VERSION = 25; /**  Reset the database num for new tables*/


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

        //------------------------------------------
        // create passage questions table

        final String SQL_CREATE_PASSAGE_QUEST_TABLE = "CREATE TABLE IF NOT EXISTS " +
                PassageQuestionsTable.TABLE_NAME + " ( " +
                PassageQuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PassageQuestionsTable.COLUMN_QUESTION + " TEXT, " +
                PassageQuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                PassageQuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                PassageQuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                PassageQuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                PassageQuestionsTable.COLUMN_LEVEL + " INTEGER, " +
                PassageQuestionsTable.COLUMN_CORRECT_VAL + " INTEGER" +
                " ) ";

        db.execSQL(SQL_CREATE_PASSAGE_QUEST_TABLE);

        // create a unique index for the table to avoid duplicate questions
        final String SQL_CREATE_PASSAGE_QUEST_INDEX = "CREATE UNIQUE INDEX IF NOT EXISTS " +
                "passage_index ON " +  PassageQuestionsTable.TABLE_NAME + "(" + PassageQuestionsTable.COLUMN_QUESTION + ")";
        db.execSQL(SQL_CREATE_PASSAGE_QUEST_INDEX);

        //----------------------------------------------------------------------

        //------------------------------------------------------------------------------
        // create table of users and their Word Game completion times

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

        //----------------------------------------------------------------------

        // create table of users and their Passage Game completion times

        final String SQL_CREATE_PASSAGE_USERNAME_TIME_TABLE = "CREATE TABLE IF NOT EXISTS " +
                UsersPassageCompletionTimeTable.TABLE_NAME + " ( " +
                UsersPassageCompletionTimeTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UsersPassageCompletionTimeTable.COLUMN_USERNAME + " TEXT, " +
                UsersPassageCompletionTimeTable.COLUMN_TIME + " INTEGER" +
                " ) ";
        //*** Time will be stored as an integer and retrieved as a long.

        db.execSQL(SQL_CREATE_PASSAGE_USERNAME_TIME_TABLE); // create the username time table for passage game

        // create a unique index for the table to avoid duplicate username and times
        final String SQL_CREATE_PASSAGE_USERNAME_TIME_INDEX = "CREATE UNIQUE INDEX IF NOT EXISTS " +
                "username_time_passage_index ON " +  UsersPassageCompletionTimeTable.TABLE_NAME + "(" + UsersPassageCompletionTimeTable.COLUMN_USERNAME
                + "," + UsersWordCompletionTimeTable.COLUMN_TIME + ")";
        db.execSQL(SQL_CREATE_PASSAGE_USERNAME_TIME_INDEX); // we don't want same user, same time to be entered repeatedly


        //----------------------------------------------------------------------

    }
                            /*********----On--Create----Ends----*****/
    /**********************************************************************************************************/

    //*********************************************************************
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + WordQuestionsTable.TABLE_NAME); // drop word questions table

        db.execSQL("DROP TABLE IF EXISTS " + PassageQuestionsTable.TABLE_NAME); // drop passage questions table

        db.execSQL("DROP TABLE IF EXISTS " + UsersWordCompletionTimeTable.TABLE_NAME); // drop username time Word table

        db.execSQL("DROP TABLE IF EXISTS " + UsersPassageCompletionTimeTable.TABLE_NAME); // drop username time Passage table

        db.execSQL("DROP TABLE IF EXISTS " + UserLoginDataTable.TABLE_NAME); // drop username time table

        onCreate(db);
    }
    //*******************************************************************

    /************************************************************************************************************************/
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

    /****************************************************************************************************/
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
    /***********************************************************************************************************/

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
    /******************************************************************************************************************************/
    //*******************************************************************

    public void fillPassageQuestTableIfEmpty(){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(PassageQuestionsTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        if (cursor != null && cursor.getCount() > 0){
            return;
        }
        fillPassageQuestTable();
    }

    // fill word database with word questions

    public void fillPassageQuestTable(){

        //---------------------- Level 1 -------------------------------------------------

        addPassageQuestion("Like the rest of the world, the store is a slight aberration of normal. " +
                "Aberration in the sentence above", "dormant", "remote", "anomaly", 3, 1);

        addPassageQuestion("They indicated that the commissioners would keep abreast of future developments. " +
                "Abreast in the sentence above", "uncertain", "volatile", "informed", 3, 1);

        addPassageQuestion("As a result, they tend to abstain or drink very little. " +
                "Abstain in the sentence above", "entice", "refrain", "abhor", 2, 1);

        addPassageQuestion("This is a huge leap of faith, and I hope it’s not into the abyss. " +
                "Abyss in the sentence above", "attract", "void", "perish", 2, 1);

        addPassageQuestion("They became adept at explaining radiation basics and risks to residents and officials. " +
                "Adept in the sentence above", "proficient", "uncertain", "adorn", 1, 1);

        //--------------------------------------------------------------------------------------------------------------

        //---------------------- Level 2 -------------------------------------------------

        addPassageQuestion("Not a trace of humiliation or abasement was to be seen in the Duke's countenance or demeanour. " +
                "Abasement in the sentence above", "sobriety", "belittlement", "aggravate", 2,2);

        addPassageQuestion("The sky remained dark around us, but the clouds of dust abated somewhat." +
                "Abate in the sentence above", "subside", "adorn", "stray", 1,2);

        addPassageQuestion("Each State will declare, at the time of its accession, in which of the said classes it desires to be placed. " +
                "Accession in the sentence above", "acumen", "combustion", "joining",3,2);

        addPassageQuestion("But sometimes, one is taken over by a dark mood, an unusually acerbic wit, strange and vitrified. " +
                "Acerbic in the sentence above", "caustic", "edible", "dullard", 1, 2);

        addPassageQuestion("The priest, his acolytes, the director and I all went outside. " +
                "Acolyte in the sentence above", "cryptic", "assistant", "euphoria", 2, 2);

        //--------------------------------------------------------------------------------

        //---------------------- Level 3 -------------------------------------------------

        addPassageQuestion("The scientific mood dominated them, the artistic and practical moods were in abeyance. " +
                "Abeyance in the sentence above", "anomaly", "remission", "chisel", 2,3);

        addPassageQuestion("In words they may pretend to abjure their empire: but in reality they will remain subject to it all the while. " +
                "Abjure in the sentence above", "reject", "adorn", "stray", 1,3);

        addPassageQuestion("Despite its anodyne title the book contained shocking revelations. " +
                "Anodyne in the sentence above", "acumen", "combustion", "inoffensive",3,3);

        addPassageQuestion("Authorities said they were part of a conspiracy to bilk owners of time-share properties. " +
                "Bilk in the sentence above", "swindle", "gallant", "dullard", 1, 3);

        addPassageQuestion("Elephants will be admitted, too, on account of the unjust canard concerning their fear of mice." +
                "Canard in the sentence above", "cryptic", "rumor", "shard", 2, 3);

    }

    //***********************************************************************************************************
    private void addPassageQuestion(String word, String opt1, String opt2, String opt3, int ansNr, int level){

        Question passageQuestion = new Question(word + " is closest in meaning to:", opt1, opt2, opt3, ansNr, level);


        ContentValues cv = new ContentValues();
        cv.put(PassageQuestionsTable.COLUMN_QUESTION, passageQuestion.getQuestion());
        cv.put(PassageQuestionsTable.COLUMN_OPTION1, passageQuestion.getOption1());
        cv.put(PassageQuestionsTable.COLUMN_OPTION2, passageQuestion.getOption2());
        cv.put(PassageQuestionsTable.COLUMN_OPTION3, passageQuestion.getOption3());
        cv.put(PassageQuestionsTable.COLUMN_ANSWER_NR, passageQuestion.getAnswerNr());
        cv.put(PassageQuestionsTable.COLUMN_LEVEL, passageQuestion.getLevel());
        cv.put(PassageQuestionsTable.COLUMN_CORRECT_VAL, passageQuestion.getCorrectVal()); // all correctVals are zero as default

        // now we add the question to the database db
        this.getWritableDatabase().insertWithOnConflict(PassageQuestionsTable.TABLE_NAME,null, cv,SQLiteDatabase.CONFLICT_IGNORE);

    }

    //-------------------------------------------------------------------------------------
    public void resetPassageQuestionCorrectValues(){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PassageQuestionsTable.COLUMN_CORRECT_VAL, 0);

        Cursor cursor = database.query(PassageQuestionsTable.TABLE_NAME,
                new String[]{PassageQuestionsTable.COLUMN_CORRECT_VAL},
                PassageQuestionsTable.COLUMN_CORRECT_VAL + " !=?",
                new String[]{String.valueOf(0)},
                null,
                null,
                null);
        if (cursor.moveToFirst()){
            do {
                this.getWritableDatabase().updateWithOnConflict(PassageQuestionsTable.TABLE_NAME, contentValues,
                        PassageQuestionsTable.COLUMN_CORRECT_VAL+ " !=? ",
                        new String[]{String.valueOf(0)},SQLiteDatabase.CONFLICT_IGNORE);
            } while (cursor.moveToNext());
        }
    }
    //------------------------------------------------------------------------

    //*********************************************************************************************************************

    public void updatePassageQuestionCorrectVal(int questionID, int correctVal){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PassageQuestionsTable.COLUMN_CORRECT_VAL, correctVal);

        Cursor cursor = database.query(PassageQuestionsTable.TABLE_NAME,
                new String[]{PassageQuestionsTable._ID, PassageQuestionsTable.COLUMN_CORRECT_VAL},
                PassageQuestionsTable._ID + " =?",
                new String[]{String.valueOf(questionID)},
                null,
                null,
                null);
        if (cursor.moveToFirst()){
            if (cursor.getInt(cursor.getColumnIndex(PassageQuestionsTable.COLUMN_CORRECT_VAL)) == 0){
                this.getWritableDatabase().updateWithOnConflict(PassageQuestionsTable.TABLE_NAME, contentValues,
                        PassageQuestionsTable._ID + "=?",
                        new String[]{String.valueOf(questionID)},SQLiteDatabase.CONFLICT_IGNORE);
            }
        }
    }

    //-------------------------------------------------------------------------------------


    //************************************************************************************************
    public List<Question> getAllPassageQuestionsPerLevel(int level){
        List<Question> passageQuestionList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(PassageQuestionsTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()){
            do {
                Question question = new Question();
                question.setQuestionID(cursor.getInt(cursor.getColumnIndex(PassageQuestionsTable._ID))); // automatically set by android
                question.setQuestion(cursor.getString(cursor.getColumnIndex(PassageQuestionsTable.COLUMN_QUESTION)));
                question.setOption1(cursor.getString(cursor.getColumnIndex(PassageQuestionsTable.COLUMN_OPTION1)));
                question.setOption2(cursor.getString(cursor.getColumnIndex(PassageQuestionsTable.COLUMN_OPTION2)));
                question.setOption3(cursor.getString(cursor.getColumnIndex(PassageQuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(cursor.getInt(cursor.getColumnIndex(PassageQuestionsTable.COLUMN_ANSWER_NR)));
                question.setLevel(cursor.getInt(cursor.getColumnIndex(PassageQuestionsTable.COLUMN_LEVEL)));
                question.setCorrectVal(cursor.getInt(cursor.getColumnIndex(PassageQuestionsTable.COLUMN_CORRECT_VAL)));
                if (cursor.getInt(cursor.getColumnIndex(PassageQuestionsTable.COLUMN_LEVEL)) == level){
                    passageQuestionList.add(question);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return passageQuestionList;
    }
    //*********************************************************************************************************************
    //************************************************************************************************
    public List<Question> getAllPassageQuestions(){

        List<Question> allPassageQuestionList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(PassageQuestionsTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()){
            do {
                Question question = new Question();
                question.setQuestionID(cursor.getInt(cursor.getColumnIndex(PassageQuestionsTable._ID))); // automatically set by android
                question.setQuestion(cursor.getString(cursor.getColumnIndex(PassageQuestionsTable.COLUMN_QUESTION)));
                question.setOption1(cursor.getString(cursor.getColumnIndex(PassageQuestionsTable.COLUMN_OPTION1)));
                question.setOption2(cursor.getString(cursor.getColumnIndex(PassageQuestionsTable.COLUMN_OPTION2)));
                question.setOption3(cursor.getString(cursor.getColumnIndex(PassageQuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(cursor.getInt(cursor.getColumnIndex(PassageQuestionsTable.COLUMN_ANSWER_NR)));
                question.setLevel(cursor.getInt(cursor.getColumnIndex(PassageQuestionsTable.COLUMN_LEVEL)));
                question.setCorrectVal(cursor.getInt(cursor.getColumnIndex(PassageQuestionsTable.COLUMN_CORRECT_VAL)));

                allPassageQuestionList.add(question);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return allPassageQuestionList;
    }
    //*********************************************************************************************************************

    public void insertUserNameTimePassageTable(String username, Long time){

        ContentValues cv = new ContentValues();
        cv.put(UsersPassageCompletionTimeTable.COLUMN_USERNAME, username);
        cv.put(UsersPassageCompletionTimeTable.COLUMN_TIME, time);

        // insert if the user doesn't exist, or if they exist and their time is less than current
        if (doesUserExistInPassageTable(username)){
            if (isUserPassageTableUpdateNecessary(username, time)){
                deleteUserPassageTableRecord(username);
                this.getWritableDatabase().insertWithOnConflict(UsersPassageCompletionTimeTable.TABLE_NAME,null, cv, SQLiteDatabase.CONFLICT_IGNORE);
            }
        } else { // add users that do not exist
            this.getWritableDatabase().insertWithOnConflict(UsersPassageCompletionTimeTable.TABLE_NAME,null, cv, SQLiteDatabase.CONFLICT_IGNORE);
        }

    }

    //******************************************************

    // does user exist
    public boolean doesUserExistInPassageTable(String username){

        SQLiteDatabase db = this.getReadableDatabase();

        // check if a user exists and if their time is larger then current
        Cursor cursor = db.query(UsersPassageCompletionTimeTable.TABLE_NAME,
                new String[] {UsersPassageCompletionTimeTable.COLUMN_USERNAME},
                UsersPassageCompletionTimeTable.COLUMN_USERNAME + "=?",
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
    public boolean isUserPassageTableUpdateNecessary(String username, Long time){

        SQLiteDatabase db = this.getReadableDatabase();

        // check if a user exists and if their time is larger then current
        Cursor cursor = db.query(UsersPassageCompletionTimeTable.TABLE_NAME,
                new String[] {UsersPassageCompletionTimeTable.COLUMN_USERNAME},
                UsersPassageCompletionTimeTable.COLUMN_USERNAME + "=? AND " +
                        UsersPassageCompletionTimeTable.COLUMN_TIME + ">?",
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
    private void deleteUserPassageTableRecord(String username){

        this.getWritableDatabase().delete(UsersPassageCompletionTimeTable.TABLE_NAME,
                UsersPassageCompletionTimeTable.COLUMN_USERNAME + " =?",
                new String[]{username});
    }
    //******************************************************
    public void clearUserNameTimePassageTable(){
        this.getWritableDatabase().delete(UsersPassageCompletionTimeTable.TABLE_NAME, null, null);
    }

    //************************************************************************************************




}
