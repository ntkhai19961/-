package com.example.win7.apphoctiengnhat.LessonDetails.Fragment.Test.model;

import java.io.Serializable;

/**
 * Created by ntkhai1996 on 07-Apr-18.
 */

public class VocabularyQuest implements Serializable {

    private String answer;
    private String hint;
    private String image;
    private String type;

    // user to know this is a right or wrong answer when back to prev quiz
    private boolean isRightAnswer;

    public VocabularyQuest() {
    }

    public void setRightAnswer(boolean rightAnswer) {
        isRightAnswer = rightAnswer;
    }

    public boolean isRightAnswer() {
        return isRightAnswer;
    }

    public String getAnswer() {
        return answer;
    }

    public String getImage() {
        return image;
    }

    public String getHint() {
        return hint;
    }

    public String getType() {
        return type;
    }
}
