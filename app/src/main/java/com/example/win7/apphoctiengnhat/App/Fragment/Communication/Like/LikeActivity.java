package com.example.win7.apphoctiengnhat.App.Fragment.Communication.Like;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.win7.apphoctiengnhat.App.Fragment.Communication.CacChucNang.CacChucNangKhac.CommunicationAdapter;
import com.example.win7.apphoctiengnhat.DocDuLieuSQLite.Database;
import com.example.win7.apphoctiengnhat.App.Fragment.Communication.CacChucNang.CacChucNangKhac.ClassChucNangCustomAdapter;
import com.example.win7.apphoctiengnhat.R;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by ntkhai1996 on 19-Nov-17.
 */

public class LikeActivity extends AppCompatActivity{

    private TextView txtLike_Notification;
    private ArrayList<ClassChucNangCustomAdapter> arrayChucNang;
    private CommunicationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);

        addControls();
        readData();
        isHavingData();
    }

    private void isHavingData() {
        if(arrayChucNang.size() == 0)
            txtLike_Notification.setText(getResources().getText(R.string.no_data_available));
        else
            txtLike_Notification.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_yeu_thich, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addControls() {

        setupToolbar();
        arrayChucNang        = new ArrayList<>();
        txtLike_Notification = findViewById(R.id.txtLike_Notification);
        setupRecycleView();
    }

    private void setupRecycleView() {
        RecyclerView rcvCommunicationLike = findViewById(R.id.rcvCommunicationLike);
        rcvCommunicationLike.setHasFixedSize(true);
        rcvCommunicationLike.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        adapter = new CommunicationAdapter(this,arrayChucNang);
        rcvCommunicationLike.setAdapter(adapter);
    }

    @SuppressLint("SetTextI18n")
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView txtToolbarTitle = findViewById(R.id.txtToolbarTitle);
        txtToolbarTitle.setText("Favorite");

        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/NABILA.TTF");
        txtToolbarTitle.setTypeface(typeface);
    }

    private void readData() {

        String DATABASE_NAME = "apphoctiengnhat.sqlite";
        SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);

        @SuppressLint("Recycle")
        Cursor cursor = database.rawQuery("SELECT * FROM ChucNang WHERE YeuThich = 1",null);

        arrayChucNang.clear();

        for(int i = 0 ; i < cursor.getCount() ; i++) {
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            String TiengNhat = cursor.getString(1);
            String Romanji = cursor.getString(2);
            String YNghia = cursor.getString(3);
            Integer YeuThich = cursor.getInt(4);
            arrayChucNang.add(new ClassChucNangCustomAdapter(id,TiengNhat,Romanji,YNghia,YeuThich));
        }

        adapter.notifyDataSetChanged();
    }
}
