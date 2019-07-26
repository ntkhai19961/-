package com.example.win7.apphoctiengnhat.interfaces;

/**
 * Created by ntkhai1996 on 07-Apr-18.
 */

public interface Fragment_Lesson_Test_Interface {
     void onEndVocabularyTest();
     void onEndGrammarTest();
     void onUpdateResult();
     void onUpdateNumberRightAnswer();
     void onMinusResult();
     void onMinusNumberRightAnswer();
     void setTotalResult(int numOfSentences);
     void setTotalNumberAnswer(int numOfSentences);
     Integer getNumberRightAnswer();
     Integer getCurrentResult();
     Integer getTotalNumberAnswer();
     Integer getTotalResult();
     Integer getIdChildLesson();
}
