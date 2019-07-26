package com.example.win7.apphoctiengnhat.LessonDetails.Fragment.Vocabulary.model;

/**
 * Created by ntkhai1996 on 19-Dec-17.
 */

public class ClassLessonDetailsVocabulary {

    private String character;
    private int id;
    private String meaning;
    private String reading;

    public ClassLessonDetailsVocabulary() {
    }

    public ClassLessonDetailsVocabulary(ClassLessonDetailsVocabulary classLessonDetailsVocabulary){

        this.character = classLessonDetailsVocabulary.getCharacter();
        this.id = classLessonDetailsVocabulary.getId();
        this.meaning = classLessonDetailsVocabulary.getMeaning();
        this.reading = classLessonDetailsVocabulary.getReading();

    }

    public ClassLessonDetailsVocabulary(String vocabularyCharacter, int id, String vocabularyMeaning, String vocabularyReading) {
        this.character = vocabularyCharacter;
        this.id = id;
        this.meaning = vocabularyMeaning;
        this.reading = vocabularyReading;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getReading() {
        return reading;
    }

    public void setReading(String reading) {
        this.reading = reading;
    }
}
