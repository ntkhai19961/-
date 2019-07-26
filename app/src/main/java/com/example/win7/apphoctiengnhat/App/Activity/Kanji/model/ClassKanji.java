package com.example.win7.apphoctiengnhat.App.Activity.Kanji.model;

/**
 * Created by WIN7 on 11-Jun-17.
 */

public class ClassKanji {

    private int id;
    private String kanji;
    private String hanTu;
    private String ViDu;
    private String kanjiVGFileName;
    private String urlKanjiVGFileName;
    private String onyomi;
    private String kunyomi;
    private String yNghia;
    private String capDo;

    public ClassKanji() {
    }

    public ClassKanji(ClassKanji classKanji , String capDo) {

        hanTu = classKanji.getHanTu();
        id = classKanji.getId();
        kanji = classKanji.getKanji();
        kanjiVGFileName = classKanji.getKanjiVGFileName();
        kunyomi = classKanji.getKunyomi();
        onyomi = classKanji.getOnyomi();
        urlKanjiVGFileName = classKanji.getUrlKanjiVGFileName();
        ViDu = classKanji.getViDu();
        yNghia = classKanji.getyNghia();

        this.capDo = capDo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCapDo() {
        return capDo;
    }

    public void setCapDo(String capDo) {
        this.capDo = capDo;
    }

    public String getKanji() {
        return kanji;
    }

    public void setKanji(String kanji) {
        this.kanji = kanji;
    }

    public String getHanTu() {
        return hanTu;
    }

    public void setHanTu(String hanTu) {
        this.hanTu = hanTu;
    }

    public String getViDu() {
        return ViDu;
    }

    public void setViDu(String viDu) {
        ViDu = viDu;
    }

    public String getKanjiVGFileName() {
        return kanjiVGFileName;
    }

    public void setKanjiVGFileName(String kanjiVGFileName) {
        this.kanjiVGFileName = kanjiVGFileName;
    }

    public String getUrlKanjiVGFileName() {
        return urlKanjiVGFileName;
    }

    public void setUrlKanjiVGFileName(String urlKanjiVGFileName) {
        this.urlKanjiVGFileName = urlKanjiVGFileName;
    }

    public String getOnyomi() {
        return onyomi;
    }

    public void setOnyomi(String onyomi) {
        this.onyomi = onyomi;
    }

    public String getKunyomi() {
        return kunyomi;
    }

    public void setKunyomi(String kunyomi) {
        this.kunyomi = kunyomi;
    }

    public String getyNghia() {
        return yNghia;
    }

    public void setyNghia(String yNghia) {
        this.yNghia = yNghia;
    }
}
