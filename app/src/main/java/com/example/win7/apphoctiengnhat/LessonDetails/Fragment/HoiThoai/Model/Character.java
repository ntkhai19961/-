package com.example.win7.apphoctiengnhat.LessonDetails.Fragment.HoiThoai.Model;

/**
 * Created by ntkhai1996 on 23-Dec-17.
 */

public class Character {

    private String name;
    private int avatar;

    public Character(String name, int avatar) {
        this.name = name;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
