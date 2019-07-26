package com.example.win7.apphoctiengnhat.App.Fragment.Communication.Searching;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.win7.apphoctiengnhat.App.Fragment.Communication.CacChucNang.CacChucNangKhac.CommunicationAdapter;
import com.example.win7.apphoctiengnhat.DocDuLieuSQLite.Database;
import com.example.win7.apphoctiengnhat.App.Fragment.Communication.CacChucNang.CacChucNangKhac.ClassChucNangCustomAdapter;
import com.example.win7.apphoctiengnhat.R;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.Objects;

public class Searching extends AppCompatActivity {

    private MaterialSearchView materialSearchView;
    private RecyclerView rcvCommunicationSearch;
    private TextView txtTimKiem_Notification;
    private ArrayList<ClassChucNangCustomAdapter> arrayChucNang;
    private ArrayList<ClassChucNangCustomAdapter> arrayChucNangFound;
    private CommunicationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);

        initView();
        readData();
        searchViewOnSearchViewListenner();
        searchViewOnQueryTextListenner();
    }

    @SuppressLint("SetTextI18n")
    private void initView() {

        setupToolbar();

        arrayChucNang           = new ArrayList<>();
        materialSearchView      = findViewById(R.id.search_view);
        txtTimKiem_Notification = findViewById(R.id.txtTimKiem_Notification);
        txtTimKiem_Notification.setText("Searching....");

        setupRecycleView();
    }

    private void setupRecycleView() {
        rcvCommunicationSearch = findViewById(R.id.rcvCommunicationSearch);
        rcvCommunicationSearch.setHasFixedSize(true);
        rcvCommunicationSearch.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        adapter = new CommunicationAdapter(this,arrayChucNang);
        rcvCommunicationSearch.setAdapter(adapter);
    }

    @SuppressLint("SetTextI18n")
    private void setupToolbar() {

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView txtToolbarTitle = findViewById(R.id.txtToolbarTitle);
        txtToolbarTitle.setText("Search");

        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/NABILA.TTF");
        txtToolbarTitle.setTypeface(typeface);
    }

    private void readData() {

        String DATABASE_NAME = "apphoctiengnhat.sqlite";
        SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);

        @SuppressLint("Recycle")
        Cursor cursor = database.rawQuery("SELECT id,TiengNhat,Romanji,YNghia,YeuThich FROM ChucNang",null);

        arrayChucNang.clear();

        for(int i = 0 ; i < cursor.getCount() ; i++)
        {
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            String TiengNhat = cursor.getString(1);
            String Romanji = cursor.getString(2);

            String YNghia = properCase(cursor.getString(3));

            Integer YeuThich = cursor.getInt(4);
            arrayChucNang.add(new ClassChucNangCustomAdapter(id,TiengNhat,Romanji,YNghia,YeuThich));
        }
        adapter.notifyDataSetChanged();
    }

    private void searchViewOnSearchViewListenner() {
        materialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                // if Search View close
                // Return ??
            }
        });
    }

    private void searchViewOnQueryTextListenner() {
        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @SuppressLint("SetTextI18n")
            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText != null && !newText.isEmpty()) {

                    arrayChucNangFound = new ArrayList<>();

                    for(ClassChucNangCustomAdapter item:arrayChucNang) {
                        if(item.getYNghia().contains(newText))
                            arrayChucNangFound.add(item);
                    }

                    adapter = new CommunicationAdapter(Searching.this,arrayChucNangFound);
                    rcvCommunicationSearch.setAdapter(adapter);

                    if(arrayChucNangFound.size() == 0){
                        txtTimKiem_Notification.setVisibility(View.VISIBLE);
                        txtTimKiem_Notification.setText(getString(R.string.no_data_available));
                    } else {
                        txtTimKiem_Notification.setVisibility(View.INVISIBLE);
                        txtTimKiem_Notification.setText("");
                    }

                } else {
                    // if search is null
                    // return ??
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tim_kiem, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        materialSearchView.setMenuItem(item);
        return true;
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

    // Hàm viết Hoa chữ cái đầu tiên của từ đầu tiên trong câu
    private String properCase (String inputVal) {
        // Empty strings should be returned as-is.

        if (inputVal.length() == 0) return "";

        // Strings with only one character uppercased.

        if (inputVal.length() == 1) return inputVal.toUpperCase();

        // Otherwise uppercase first letter, lowercase the rest.

        return inputVal.substring(0,1).toUpperCase()
                + inputVal.substring(1).toLowerCase();
    }
}
