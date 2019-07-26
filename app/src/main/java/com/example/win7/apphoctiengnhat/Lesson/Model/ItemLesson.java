package com.example.win7.apphoctiengnhat.Lesson.Model;

/**
 * Created by ntkhai1996 on 02-Dec-17.
 */

public class ItemLesson {

    private int type;
    private String headerText,bottomText;
    private String image;
    private int id;

    public ItemLesson() {
    }

    public ItemLesson(int type, String headerText, String bottomText, String image, int id) {
        this.type = type;
        this.headerText = headerText;
        this.bottomText = bottomText;
        this.image = image;
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getHeaderText() {
        return headerText;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    public String getBottomText() {
        return bottomText;
    }

    public void setBottomText(String bottomText) {
        this.bottomText = bottomText;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
