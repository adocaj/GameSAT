package com.example.gamesat;

import android.provider.BaseColumns;

public final class GameContract {

    private GameContract(){} // private constructor to avoid creating an accidental contract object

    public static class WordQuestionsTable implements BaseColumns {

        public static final String TABLE_NAME = "word_questions";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_ANSWER_NR = "answer_nr";
    }

}
