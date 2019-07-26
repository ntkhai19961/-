package com.example.win7.apphoctiengnhat.App.Fragment.Communication.CacChucNang.Hiragana;

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

public class Hiragana extends AppCompatActivity {

    final String DATABASE_NAME = "apphoctiengnhat.sqlite";
    SQLiteDatabase database;

    GridView gridViewHiragana;

    ArrayList<ClassHiragana> arrayHiragana;
    HiraganaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hiragana);

        //----------------------------------------------

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBarHiragana);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        toolbar.setTitle("Hiragana");

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
        getMenuInflater().inflate(R.menu.menu_hiragana, menu);
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

    private void addControls() {

        gridViewHiragana = (GridView) findViewById(R.id.GridViewHiragana);
        arrayHiragana = new ArrayList<>();
        adapter = new HiraganaAdapter(this , R.layout.item_hiragana, arrayHiragana);
        gridViewHiragana.setAdapter(adapter);
    }

    private void readData()
    {
        database = Database.initDatabase(this , DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM BangChuHiragana",null);
        arrayHiragana.clear();
        for(int i = 0 ; i < cursor.getCount() ; i++)
        {
            if(i==10)
            {
                arrayHiragana.add(new ClassHiragana(0,"",""));
                arrayHiragana.add(new ClassHiragana(0,"",""));
            }
            if(i==27)
            {
                arrayHiragana.add(new ClassHiragana(0,"",""));
                arrayHiragana.add(new ClassHiragana(0,"",""));
                arrayHiragana.add(new ClassHiragana(0,"",""));
            }
            if(i==46)
            {
                arrayHiragana.add(new ClassHiragana(0,"",""));
            }
            if(i==51)
            {
                arrayHiragana.add(new ClassHiragana(0,"",""));
                arrayHiragana.add(new ClassHiragana(0,"",""));
                arrayHiragana.add(new ClassHiragana(0,"",""));
            }
            if(i==64)
            {
                arrayHiragana.add(new ClassHiragana(0,"",""));
                arrayHiragana.add(new ClassHiragana(0,"",""));
                arrayHiragana.add(new ClassHiragana(0,"",""));
            }
            if(i==69)
            {
                arrayHiragana.add(new ClassHiragana(0,"",""));
                arrayHiragana.add(new ClassHiragana(0,"",""));
                arrayHiragana.add(new ClassHiragana(0,"",""));
            }
            if(i==79)
            {
                arrayHiragana.add(new ClassHiragana(0,"",""));
                arrayHiragana.add(new ClassHiragana(0,"",""));
            }
            if(i==90)
            {
                arrayHiragana.add(new ClassHiragana(0,"",""));
            }
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            String Hiragana = cursor.getString(1);
            String RomanjiHiragana = cursor.getString(2);
            arrayHiragana.add(new ClassHiragana(id,Hiragana,RomanjiHiragana));

        }
        adapter.notifyDataSetChanged();
    }
}
