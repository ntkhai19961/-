package com.example.win7.apphoctiengnhat.App.Fragment.Communication.CacChucNang.Hiragana;

/**
 * Created by WIN7 on 08-Jun-17.
 */

public class ClassHiragana {

    public Integer idHiragana;
    public String Hiragana;
    public String RomanjiHiragana;
    public byte[] HinhAnhHira;

    public ClassHiragana(Integer idHiragana, String hiragana, String romanjiHiragana) {
        this.idHiragana = idHiragana;
        Hiragana = hiragana;
        RomanjiHiragana = romanjiHiragana;
    }

    public ClassHiragana(Integer idHiragana, String hiragana, String romanji, byte[] hinhAnhHira) {
        this.idHiragana = idHiragana;
        Hiragana = hiragana;
        RomanjiHiragana = romanji;
        HinhAnhHira = hinhAnhHira;
    }
}
