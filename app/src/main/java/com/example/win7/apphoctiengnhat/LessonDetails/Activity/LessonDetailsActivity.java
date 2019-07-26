package com.example.win7.apphoctiengnhat.LessonDetails.Activity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.win7.apphoctiengnhat.LessonDetails.Fragment.HoiThoai.Fragment_LessonDetails_HoiThoai;
import com.example.win7.apphoctiengnhat.LessonDetails.Fragment.LuyenTap.Fragment_LessonDetails_LuyenTap;
import com.example.win7.apphoctiengnhat.LessonDetails.Fragment.NguPhap.Fragment_LessonDetails_NguPhap;
import com.example.win7.apphoctiengnhat.LessonDetails.Fragment.Test.Fragment_LessonDetails_Test;
import com.example.win7.apphoctiengnhat.LessonDetails.Fragment.Vocabulary.Fragment_LessonDetails_TuVung;
import com.example.win7.apphoctiengnhat.R;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.OnBoomListener;

import java.util.Objects;

public class LessonDetailsActivity extends AppCompatActivity{

    private BoomMenuButton bmb;
    private TextView txtToolBarLessonTitle;
    private String lessonName = "";
    private String childLessonName = "";
    private int idChildLesson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_details);

        getLessonName();

        initView();
        displaySelectedBoomItem(0);
    }

    private void getLessonName() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }

        lessonName = extras.getString("lessonName");
        childLessonName = extras.getString("childLessonName");
        idChildLesson = extras.getInt("idChildLesson");
    }

    private void initView() {
        setUpToolbar();
        setUpBoomButton();
    }

    private void setUpBoomButton() {
        bmb = findViewById(R.id.bmb_lesson_details);
        addBoomButtonItem();
        boomButtonItemOnClick();
    }

    @Override
    protected void onResume() {
        super.onResume();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    @SuppressLint("SetTextI18n")
    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolBarLessonDetails);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));

        txtToolBarLessonTitle = findViewById(R.id.txtToolBarLessonTitle);
        txtToolBarLessonTitle.setText("Lesson 生活の話 (" + "文法" + ")");

        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/NABILA.TTF");
        txtToolBarLessonTitle.setTypeface(typeface);
    }

    private void boomButtonItemOnClick() {

        bmb.setOnBoomListener(new OnBoomListener() {
            @Override
            public void onClicked(int index, BoomButton boomButton) {
                displaySelectedBoomItem(index);
            }

            @Override
            public void onBackgroundClick() {

            }

            @Override
            public void onBoomWillHide() {

            }

            @Override
            public void onBoomDidHide() {

            }

            @Override
            public void onBoomWillShow() {

            }

            @Override
            public void onBoomDidShow() {

            }
        });

    }

    private void addBoomButtonItem() {
        assert bmb != null;
        bmb.setButtonEnum(ButtonEnum.Ham);

        // max 5 item , muốn thêm thì qua layout mà edit
        HamButton.Builder menuItem1 = new HamButton.Builder();
        menuItem1.normalImageRes(R.drawable.ic_kanji_white_128dp)
                .normalTextRes(R.string.ngu_phap)
                .subNormalTextRes(R.string.ngu_phap_slogan)
                .pieceColor(Color.WHITE);
        bmb.addBuilder(menuItem1);

        HamButton.Builder menuItem2 = new HamButton.Builder();
        menuItem2.normalImageRes(R.drawable.ic_vocabulary_white_48dp)
                .normalTextRes(R.string.tu_vung)
                .subNormalTextRes(R.string.tu_vung_slogan)
                .pieceColor(Color.WHITE);
        bmb.addBuilder(menuItem2);

        HamButton.Builder menuItem3 = new HamButton.Builder();
        menuItem3.normalImageRes(R.drawable.ic_chat_bubble_outline_white_16dp)
                .normalTextRes(R.string.hoi_thoai)
                .subNormalTextRes(R.string.hoi_thoai_slogan)
                .pieceColor(Color.WHITE);
        bmb.addBuilder(menuItem3);

        HamButton.Builder menuItem4 = new HamButton.Builder();
        menuItem4.normalImageRes(R.drawable.ic_study_white_128dp)
                .normalTextRes(R.string.luyen_tap)
                .subNormalTextRes(R.string.luyen_tap_slogan)
                .pieceColor(Color.WHITE);
        bmb.addBuilder(menuItem4);

        HamButton.Builder menuItem5 = new HamButton.Builder();
        menuItem5.normalImageRes(R.drawable.ic_test_white_128dp)
                .normalTextRes(R.string.kiem_tra)
                .subNormalTextRes(R.string.kiem_tra_slogan)
                .pieceColor(Color.WHITE);
        bmb.addBuilder(menuItem5);
    }

    @SuppressLint("SetTextI18n")
    private void displaySelectedBoomItem(int position){
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;

        // pass lessson name to fragment to get data from firebase
        Bundle bundle = new Bundle();
        bundle.putString("lessonName", lessonName );
        bundle.putString("childLessonName", childLessonName );
        bundle.putInt("idChildLesson", idChildLesson );

        switch (position)
        {
            case 0:
                txtToolBarLessonTitle.setText("文法");
                fragment = new Fragment_LessonDetails_NguPhap();
                fragment.setArguments(bundle);
                break;
            case 1:
                txtToolBarLessonTitle.setText("言葉");
                fragment = new Fragment_LessonDetails_TuVung();
                fragment.setArguments(bundle);
                break;
            case 2:
                txtToolBarLessonTitle.setText("会話");
                fragment = new Fragment_LessonDetails_HoiThoai();
                break;
            case 3:
                txtToolBarLessonTitle.setText("練習");
                fragment = new Fragment_LessonDetails_LuyenTap();
                fragment.setArguments(bundle);
                break;
            case 4:
                txtToolBarLessonTitle.setText("テスト");
                fragment = new Fragment_LessonDetails_Test();
                fragment.setArguments(bundle);
                break;
        }

        if (fragment != null)
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main_LessonDetails, fragment)
                    .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_for_display_back_button_toolbar, menu);
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

}
