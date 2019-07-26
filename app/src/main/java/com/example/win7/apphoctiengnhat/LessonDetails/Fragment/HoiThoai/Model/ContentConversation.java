package com.example.win7.apphoctiengnhat.LessonDetails.Fragment.HoiThoai.Model;

/**
 * Created by ntkhai1996 on 23-Dec-17.
 */

public class ContentConversation {


    private Character character;
    private String Content;
    // số thứ tự : người thứ mấy
    private int soThuTu;
    private long duration;

    public ContentConversation(Character character, String content, int soThuTu, long duration) {
        this.character = character;
        Content = content;
        this.soThuTu = soThuTu;
        this.duration = duration;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public int getSoThuTu() {
        return soThuTu;
    }

    public void setSoThuTu(int soThuTu) {
        this.soThuTu = soThuTu;
    }
}
