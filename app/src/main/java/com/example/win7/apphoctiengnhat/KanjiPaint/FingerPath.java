package com.example.win7.apphoctiengnhat.KanjiPaint;

import android.graphics.Path;

/**
 * Created by ntkhai1996 on 04-Nov-17.
 */

public class FingerPath {

    public int color;
    public boolean emboss;
    public boolean blur;
    public int strokeWidth;
    public Path path;

    public FingerPath(int color, boolean emboss, boolean blur, int strokeWidth, Path path) {
        this.color = color;
        this.emboss = emboss;
        this.blur = blur;
        this.strokeWidth = strokeWidth;
        this.path = path;
    }
}
