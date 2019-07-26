package com.example.win7.apphoctiengnhat.KanjiDetails;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.win7.apphoctiengnhat.KanjiPaint.KanjiPaint;
import com.example.win7.apphoctiengnhat.R;
import com.github.badoualy.kanjistroke.KanjiStrokeView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

public class KanjiDetails extends AppCompatActivity implements View.OnClickListener {

    private StorageReference storageRef;
    private TextView txtToolbarTitle;
    private TextView txtKanjiDetails_Vidu;
    private TextView txtKanjiDetails_HanTu;
    private TextView txtKanjiDetails_Id;
    private TextView txtKanjiDetails_Title;
    private TextView txtKanjiDetails_Kanji;
    private TextView txtKanjiDetails_YNghia;
    private TextView txtKanjiDetails_CapDo;
    private TextView txtKanjiDetails_OnYoMi;
    private TextView txtKanjiDetails_KunYoMi;
    private KanjiStrokeView kanjiStrokeView;
    private AVLoadingIndicatorView AVLoadingIndicatorView_KanjiDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kanji_details);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        initView();
        getExtraFromKanjiAdapterAndSetText();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_details, menu);
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

    public void initView() {

        setupToolbar();

        txtKanjiDetails_Vidu    = findViewById(R.id.txtKanjiDetails_Vidu);
        txtKanjiDetails_HanTu   = findViewById(R.id.txtKanjiDetails_HanTu);
        txtKanjiDetails_Id      = findViewById(R.id.txtKanjiDetails_Id);
        txtKanjiDetails_Title   = findViewById(R.id.txtKanjiDetails_Title);
        txtKanjiDetails_Kanji   = findViewById(R.id.txtKanji_ChiTietKanji);
        txtKanjiDetails_YNghia  = findViewById(R.id.txtYNghia_ChiTietKanji);
        txtKanjiDetails_CapDo   = findViewById(R.id.txtCapDo_ChiTietKanji);
        txtKanjiDetails_OnYoMi  = findViewById(R.id.txtOnYoMi_ChiTietKanji);
        txtKanjiDetails_KunYoMi = findViewById(R.id.txtKunYoMi_ChiTietKanji);

        kanjiStrokeView = findViewById(R.id.view_stroke);
        kanjiStrokeView.setOnClickListener(this);

        AVLoadingIndicatorView_KanjiDetails = findViewById(R.id.AVLoadingIndicatorView_KanjiDetails);
        AVLoadingIndicatorView_KanjiDetails.setVisibility(View.VISIBLE);

        ImageButton imageButtonKanjiPaint = findViewById(R.id.imageButtonKanjiPaint);
        imageButtonKanjiPaint.setOnClickListener(this);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtToolbarTitle = findViewById(R.id.txtToolbarTitle);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/NABILA.TTF");
        txtToolbarTitle.setTypeface(typeface);
    }

    public void getExtraFromKanjiAdapterAndSetText() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        // get data via the key
        int id         = extras.getInt("id");
        String Kanji   = extras.getString("Kanji");
        String HanTu   = extras.getString("HanTu");
        String YNghia  = extras.getString("YNghia");
        String Vidu    = extras.getString("Vidu");
        String Level   = extras.getString("Level");
        String Onyomi  = extras.getString("Onyomi");
        String Kunyomi = extras.getString("Kunyomi");
        final String KanjiVGFileName = extras.getString("KanjiVGFileName");

        txtKanjiDetails_Vidu.setText(Vidu);
        txtKanjiDetails_HanTu.setText(HanTu);
        txtKanjiDetails_Id.setText(String.valueOf(id));
        txtKanjiDetails_Title.setText(Kanji);
        txtKanjiDetails_Kanji.setText(Kanji);
        txtKanjiDetails_YNghia.setText(YNghia);
        txtKanjiDetails_CapDo.setText(Level);
        txtKanjiDetails_OnYoMi.setText(Onyomi);
        txtKanjiDetails_KunYoMi.setText(Kunyomi);

        txtToolbarTitle.setText(Kanji);

        runKanjiStrokeView(KanjiVGFileName);
    }

    private void runKanjiStrokeView(String KanjiVGFileName) {

        if(KanjiVGFileName != null && !KanjiVGFileName.equals("")) {

            StorageReference islandRef = storageRef.child(KanjiVGFileName);

            final long ONE_MEGABYTE = 1024 * 1024;
            islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {

                    String str = null;
                    try {
                        str = new String(bytes, "UTF-8");
                        kanjiStrokeView.loadSvg(str);
                        kanjiStrokeView.startDrawAnimation();
                        kanjiStrokeView.setAutoRun(true);
                        AVLoadingIndicatorView_KanjiDetails.setVisibility(View.INVISIBLE);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        Log.e("Error","Failed to convert from byte[] to string");
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    Log.e("Error","Error download SVG file " + KanjiVGFileName );
                }
            });

        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.view_stroke:
                kanjiStrokeView.startDrawAnimation();
                break;
            case R.id.imageButtonKanjiPaint:
                startActivity(new Intent(this, KanjiPaint.class));
                break;
        }
    }
}
