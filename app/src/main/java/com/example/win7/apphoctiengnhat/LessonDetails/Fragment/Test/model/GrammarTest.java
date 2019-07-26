package com.example.win7.apphoctiengnhat.LessonDetails.Fragment.Test.model;

/**
 * Created by ntkhai1996 on 07-Apr-18.
 */

public class GrammarTest {

    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String question;
    private String rightAnswer;

    public GrammarTest() {
    }

    public GrammarTest(String answer1, String answer2, String answer3, String answer4, String question, String rightAnswer) {
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.question = question;
        this.rightAnswer = rightAnswer;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }
}
