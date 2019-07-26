package com.example.win7.apphoctiengnhat.KanjiVG_Drawing;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.win7.apphoctiengnhat.KanjiPaint.KanjiPaint;
import com.example.win7.apphoctiengnhat.R;
import com.github.badoualy.kanjistroke.KanjiStrokeView;

public class KanjiVG_Drawing extends AppCompatActivity {

    String Kanji;

    Button btnKanjiPaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kanji_vg_drawing);

        final KanjiStrokeView strokeView = findViewById(R.id.view_stroke);
        btnKanjiPaint = findViewById(R.id.btnKanjiPaint);

        Resources resources = getApplicationContext().getResources();

        //----------------------------------------------

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBarKanjiVGdrawing);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        toolbar.setTitle("How To Wrtie Kanji");

        //Hiện nút back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));

        //----------------------------------------------

        //------------------------------------------------------------------------------------------
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        // get data via the key
        Kanji = extras.getString("Kanji");
        if (Kanji != null) {
            //Toast.makeText(this, Kanji , Toast.LENGTH_SHORT).show();

            int resourceId = resources.getIdentifier(String.valueOf(Kanji), "raw", getApplicationContext().getPackageName());

            strokeView.loadSvg(resources.openRawResource(resourceId));
            strokeView.startDrawAnimation();
            strokeView.setAutoRun(true);
        }

        //------------------------------------------------------------------------------------------


        //------------------------------------------------------------------------------------------
        strokeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strokeView.startDrawAnimation();
            }
        });
        //------------------------------------------------------------------------------------------

        btnKanjiPaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent KanjiVG_KanjiPaint_Intent = new Intent(KanjiVG_Drawing.this , KanjiPaint.class);
                startActivity(KanjiVG_KanjiPaint_Intent);
            }
        });

    }

    //-------------------------------------------------------------------------------
    //inflate menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_kanji_vg_drawing, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Bắt sự kiện cho các item Menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //-------------------------------------------------------------------------------

}
