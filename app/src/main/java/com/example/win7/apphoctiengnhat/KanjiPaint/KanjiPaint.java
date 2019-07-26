package com.example.win7.apphoctiengnhat.KanjiPaint;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.win7.apphoctiengnhat.R;

import br.com.bloder.magic.view.MagicButton;

public class KanjiPaint extends AppCompatActivity {

    private PaintView paintView;
    private MagicButton btnClearKanjiPaint;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kanji_paint);

        paintView = findViewById(R.id.KanjiPaintView);
        btnClearKanjiPaint = findViewById(R.id.btnClearKanjiPaint);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);


        btnClearKanjiPaint.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paintView.clear();
            }
        });

    }
}
