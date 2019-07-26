package com.example.win7.apphoctiengnhat.App.Fragment.Communication.CacChucNang.Katakana;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.example.win7.apphoctiengnhat.DocDuLieuSQLite.Database;
import com.example.win7.apphoctiengnhat.R;

import java.util.ArrayList;

public class Katakana extends AppCompatActivity {

    final String DATABASE_NAME = "apphoctiengnhat.sqlite";
    SQLiteDatabase database;

    GridView gridViewKatakana;

    ArrayList<ClassKatakana> arrayKatakana;
    KatakanaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_katakana);

        //----------------------------------------------

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBarKatakana);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        toolbar.setTitle("Katakana");

        //Hiện nút back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));

        //----------------------------------------------

        addControls();
        readData();
    }

    //-------------------------------------------------------------------------------
    //inflate menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_katakana, menu);
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

    private void readData() {
        database = Database.initDatabase(this , DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM BangChuKatakana",null);
        arrayKatakana.clear();
        for(int i = 0 ; i < cursor.getCount() ; i++)
        {
            if(i==10)
            {
                arrayKatakana.add(new ClassKatakana(0,"",""));
                arrayKatakana.add(new ClassKatakana(0,"",""));
            }
            if(i==27)
            {
                arrayKatakana.add(new ClassKatakana(0,"",""));
                arrayKatakana.add(new ClassKatakana(0,"",""));
                arrayKatakana.add(new ClassKatakana(0,"",""));
            }
            if(i==46)
            {
                arrayKatakana.add(new ClassKatakana(0,"",""));
            }
            if(i==51)
            {
                arrayKatakana.add(new ClassKatakana(0,"",""));
                arrayKatakana.add(new ClassKatakana(0,"",""));
                arrayKatakana.add(new ClassKatakana(0,"",""));
            }
            if(i==64)
            {
                arrayKatakana.add(new ClassKatakana(0,"",""));
                arrayKatakana.add(new ClassKatakana(0,"",""));
                arrayKatakana.add(new ClassKatakana(0,"",""));
            }
            if(i==69)
            {
                arrayKatakana.add(new ClassKatakana(0,"",""));
                arrayKatakana.add(new ClassKatakana(0,"",""));
                arrayKatakana.add(new ClassKatakana(0,"",""));
            }
            if(i==79)
            {
                arrayKatakana.add(new ClassKatakana(0,"",""));
                arrayKatakana.add(new ClassKatakana(0,"",""));
            }
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            String Hiragana = cursor.getString(1);
            String RomanjiHiragana = cursor.getString(2);
            arrayKatakana.add(new ClassKatakana(id,Hiragana,RomanjiHiragana));

        }
        adapter.notifyDataSetChanged();
    }

    private void addControls() {

        gridViewKatakana = (GridView) findViewById(R.id.GridViewKatakana);
        arrayKatakana = new ArrayList<>();
        adapter = new KatakanaAdapter(this , R.layout.item_katakana, arrayKatakana);
        gridViewKatakana.setAdapter(adapter);

    }
}
