package com.example.win7.apphoctiengnhat.LessonDetails.Fragment.NguPhap.model;

import java.io.Serializable;

/**
 * Created by ntkhai1996 on 09-Apr-18.
 */

public class Grammar implements Serializable{
    private String example;
    private String grammar;
    private String id;
    private String imgURL;

    public Grammar() {
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getGrammar() {
        return grammar;
    }

    public void setGrammar(String grammar) {
        this.grammar = grammar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
