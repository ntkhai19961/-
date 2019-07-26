package com.example.win7.apphoctiengnhat.App.Fragment.Communication.CacChucNang.Katakana;

/**
 * Created by WIN7 on 08-Jun-17.
 */

public class ClassKatakana {

    public Integer idKatakana;
    public String Katakana;
    public String RomanjiKatakana;
    public byte[] HinhAnhKata;

    public ClassKatakana(Integer idKatakana, String katakana, String romanjiKatakana) {
        this.idKatakana = idKatakana;
        Katakana = katakana;
        RomanjiKatakana = romanjiKatakana;
    }

    public ClassKatakana(Integer idKatakana, String katakana, String romanjiKatakana, byte[] hinhAnhKata) {
        this.idKatakana = idKatakana;
        Katakana = katakana;
        RomanjiKatakana = romanjiKatakana;
        HinhAnhKata = hinhAnhKata;
    }
}
