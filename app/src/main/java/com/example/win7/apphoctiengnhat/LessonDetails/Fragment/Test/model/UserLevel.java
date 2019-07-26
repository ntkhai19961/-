package com.example.win7.apphoctiengnhat.LessonDetails.Fragment.Test.model;

/**
 * Created by ntkhai1996 on 14-Apr-18.
 */

public class UserLevel {
    private String KanjiLevel;
    private String LessonLevel;
    private int UnlockChildLesson;
    private int UnlockKanji;
    private int UnlockLesson;

    public UserLevel() {
    }

    public UserLevel(String kanjiLevel, String lessonLevel, int unlockChildLesson, int unlockKanji, int unlockLesson) {
        KanjiLevel = kanjiLevel;
        LessonLevel = lessonLevel;
        UnlockChildLesson = unlockChildLesson;
        UnlockKanji = unlockKanji;
        UnlockLesson = unlockLesson;
    }

    public String getKanjiLevel() {
        return KanjiLevel;
    }

    public String getLessonLevel() {
        return LessonLevel;
    }

    public int getUnlockChildLesson() {
        return UnlockChildLesson;
    }

    public int getUnlockKanji() {
        return UnlockKanji;
    }

    public int getUnlockLesson() {
        return UnlockLesson;
    }

    public void setKanjiLevel(String kanjiLevel) {
        KanjiLevel = kanjiLevel;
    }

    public void setLessonLevel(String lessonLevel) {
        LessonLevel = lessonLevel;
    }

    public void setUnlockChildLesson(int unlockChildLesson) {
        UnlockChildLesson = unlockChildLesson;
    }

    public void setUnlockKanji(int unlockKanji) {
        UnlockKanji = unlockKanji;
    }

    public void setUnlockLesson(int unlockLesson) {
        UnlockLesson = unlockLesson;
    }
}
