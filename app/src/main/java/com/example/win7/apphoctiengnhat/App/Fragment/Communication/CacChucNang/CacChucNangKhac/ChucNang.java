package com.example.win7.apphoctiengnhat.App.Fragment.Communication.CacChucNang.CacChucNangKhac;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.win7.apphoctiengnhat.App.Fragment.Communication.ChucNangKiemTra.KiemTra;
import com.example.win7.apphoctiengnhat.CustomView.CustomRoundedButton;
import com.example.win7.apphoctiengnhat.DocDuLieuSQLite.Database;
import com.example.win7.apphoctiengnhat.R;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by ntkhai1996 on 19-Nov-17.
 */

public class ChucNang extends AppCompatActivity{

    private String TenTableSQLite;
    private String TenChucNang;
    private RecyclerView rcvCommunication;
    private CommunicationAdapter adapter;
    private CustomRoundedButton btnKiemTraChucNang;
    private ArrayList<ClassChucNangCustomAdapter> arrayChucNang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_communication);

        getTableName();
        setUpToolbar();
        addControls();
        readData();
        setOnTouchTestButton();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setOnTouchTestButton() {
        View.OnTouchListener onTouch = new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        btnKiemTraChucNang.setButtonColor(R.color.colorPrimaryDarkOnTouch);
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        btnKiemTraChucNang.setButtonColor(R.color.colorPrimaryDarkOnTouch);
                        break;
                    case MotionEvent.ACTION_UP:
                        btnKiemTraChucNang.setButtonColor(R.color.colorPrimaryDark);
                        Intent KiemTraIntent = new Intent(ChucNang.this,KiemTra.class);
                        KiemTraIntent.putExtra("ChucNang",TenTableSQLite);
                        startActivity(KiemTraIntent);
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        btnKiemTraChucNang.setButtonColor(R.color.colorPrimaryDark);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        btnKiemTraChucNang.setButtonColor(R.color.colorPrimaryDark);
                        break;
                }
                return true;
            }
        };
        btnKiemTraChucNang.setOnTouchListener(onTouch);
    }

    private void getTableName() {
        TenTableSQLite = getIntent().getStringExtra("TenTableSQLiteFromFragment_CoBan");
        TenChucNang = getIntent().getStringExtra("TenChucNangFromFragment_CoBan");
    }

    private void setUpToolbar() {

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView txtToolbarTitle = findViewById(R.id.txtToolbarTitle);
        txtToolbarTitle.setText(TenChucNang);

        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/NABILA.TTF");
        txtToolbarTitle.setTypeface(typeface);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chuc_nang_fragment_co_ban, menu);
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
        btnKiemTraChucNang = findViewById(R.id.btnKiemTraChucNangFragment_CoBan);
        arrayChucNang = new ArrayList<>();
        setupRecycleView();
        setAnimation();
    }

    private void setupRecycleView() {
        rcvCommunication = findViewById(R.id.rcvCommunication);
        rcvCommunication.setHasFixedSize(true);
        rcvCommunication.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        adapter = new CommunicationAdapter(this,arrayChucNang);
        rcvCommunication.setAdapter(adapter);
    }

    private void setAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.anim_fade_out_slide_up);
        rcvCommunication.setAnimation(animation);
    }

    private void readData() {

        String DATABASE_NAME = "apphoctiengnhat.sqlite";
        SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
        String SQL = "SELECT ChucNang.id,TiengNhat,Romanji,YNghia,YeuThich FROM ChucNang,LoaiChucNang WHERE ChucNang.idLoaiChucnang = LoaiChucNang.id AND LoaiChucNang LIKE " + '"' + TenChucNang + '"';

        @SuppressLint("Recycle")
        Cursor cursor = database.rawQuery(SQL,null);

        arrayChucNang.clear();
        for(int i = 0 ; i < cursor.getCount() ; i++)
        {
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
