package com.example.win7.apphoctiengnhat.App.Fragment.Communication.ChucNangKiemTra;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.win7.apphoctiengnhat.CustomView.CustomRoundedButton;
import com.example.win7.apphoctiengnhat.Dialog.MiniTest.DialogDoneMiniTest;
import com.example.win7.apphoctiengnhat.Dialog.MiniTest.DialogGoNextQuestionMiniTest;
import com.example.win7.apphoctiengnhat.DocDuLieuSQLite.Database;
import com.example.win7.apphoctiengnhat.R;
import com.example.win7.apphoctiengnhat.interfaces.Dialog_MiniTest_Interface;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class KiemTra extends AppCompatActivity implements Dialog_MiniTest_Interface {

    private String ChucNang;
    private ArrayList<ClassTracNghiem> arrayTracNghiem;
    private int idCauHoiHienTai;
    private int CauHoiHienTai;
    private int Diem = 0;

    // Tỉ lệ phần trăm hoàn thành từng câu
    // Ví dụ : Tổng cộng 4 câu => 100 / 4 = 25 mỗi câu.
    private float TiLePhanTramHoanThanhProgressBar;

    private TextView    txtCauHoi;
    private ProgressBar progressBar;
    private RadioGroup  radioGroupKiemTra;
    private RadioButton rdBtnCauA;
    private RadioButton rdBtnCauB;
    private RadioButton rdBtnCauC;
    private RadioButton rdBtnCauD;
    private CustomRoundedButton btnKiemTra;
    private DialogGoNextQuestionMiniTest dialogGoNextQuestion;
    private DialogDoneMiniTest dialogGoBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiem_tra);

        ChucNang = getIntent().getStringExtra("ChucNang");

        initView();
        readData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void initView() {
        setUpToolbar();
        radioGroupKiemTra = findViewById(R.id.radioGroupKiemTra);
        txtCauHoi         = findViewById(R.id.txtCauHoi);
        rdBtnCauA         = findViewById(R.id.rdBtnCauA);
        rdBtnCauB         = findViewById(R.id.rdBtnCauB);
        rdBtnCauC         = findViewById(R.id.rdBtnCauC);
        rdBtnCauD         = findViewById(R.id.rdBtnCauD);
        progressBar       = findViewById(R.id.progressBar);
        btnKiemTra        = findViewById(R.id.btnKiemTraKetQua);
        setOnTouchTestButton();
        arrayTracNghiem = new ArrayList<>();
        dialogGoNextQuestion = new DialogGoNextQuestionMiniTest();
        dialogGoBack         = new DialogDoneMiniTest();
        dialogGoBack.setListener(this);
        dialogGoNextQuestion.setListener(this);
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setOnTouchTestButton() {
        View.OnTouchListener onTouch = new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        btnKiemTra.setButtonColor(R.color.colorPrimaryDarkOnTouch);
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        btnKiemTra.setButtonColor(R.color.colorPrimaryDarkOnTouch);
                        break;
                    case MotionEvent.ACTION_UP:
                        btnKiemTra.setButtonColor(R.color.colorPrimaryDark);
                        checkAnswer();
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        btnKiemTra.setButtonColor(R.color.colorPrimaryDark);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        btnKiemTra.setButtonColor(R.color.colorPrimaryDark);
                        break;
                }
                return true;
            }
        };
        btnKiemTra.setOnTouchListener(onTouch);
    }

    @SuppressLint("SetTextI18n")
    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView txtToolbarTitle = findViewById(R.id.txtToolbarTitle);
        txtToolbarTitle.setText("Mini Test");

        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/NABILA.TTF");
        txtToolbarTitle.setTypeface(typeface);
    }

    @SuppressLint("SetTextI18n")
    private void checkAnswer() {
        String KetQua = arrayTracNghiem.get(idCauHoiHienTai).CauTraLoi;

        if(KetQua.equals("A")) {
            if(rdBtnCauA.isChecked()) {
                Diem++;

                dialogGoNextQuestion.isRightAnswer(true);
                dialogGoNextQuestion.setCauTraLoiDialogText(arrayTracNghiem.get(idCauHoiHienTai).CauA);
                dialogGoNextQuestion.setCauHoiDialogText(arrayTracNghiem.get(idCauHoiHienTai).CauHoi);
                dialogGoNextQuestion.show(getSupportFragmentManager(),"");
            }
            if(rdBtnCauB.isChecked()) {

                dialogGoNextQuestion.isRightAnswer(false);
                dialogGoNextQuestion.setCauTraLoiDialogText("The right answer is: "+arrayTracNghiem.get(idCauHoiHienTai).CauA);
                dialogGoNextQuestion.setCauHoiDialogText("Not: "+arrayTracNghiem.get(idCauHoiHienTai).CauB);
                dialogGoNextQuestion.show(getSupportFragmentManager(),"");
            }
            if(rdBtnCauC.isChecked()) {

                dialogGoNextQuestion.isRightAnswer(false);
                dialogGoNextQuestion.setCauTraLoiDialogText("The right answer is: "+arrayTracNghiem.get(idCauHoiHienTai).CauA);
                dialogGoNextQuestion.setCauHoiDialogText("Not: "+arrayTracNghiem.get(idCauHoiHienTai).CauC);
                dialogGoNextQuestion.show(getSupportFragmentManager(),"");
            }
            if(rdBtnCauD.isChecked()) {

                dialogGoNextQuestion.isRightAnswer(false);
                dialogGoNextQuestion.setCauTraLoiDialogText("The right answer is: "+arrayTracNghiem.get(idCauHoiHienTai).CauA);
                dialogGoNextQuestion.setCauHoiDialogText("Not: "+arrayTracNghiem.get(idCauHoiHienTai).CauD);
                dialogGoNextQuestion.show(getSupportFragmentManager(),"");
            }
        }
        //-------------------------------------------------------------------
        if(KetQua.equals("B")) {
            if(rdBtnCauA.isChecked()) {

                dialogGoNextQuestion.isRightAnswer(false);
                dialogGoNextQuestion.setCauTraLoiDialogText("The right answer is: "+arrayTracNghiem.get(idCauHoiHienTai).CauB);
                dialogGoNextQuestion.setCauHoiDialogText("Not: "+arrayTracNghiem.get(idCauHoiHienTai).CauA);
                dialogGoNextQuestion.show(getSupportFragmentManager(),"");
            }
            if(rdBtnCauB.isChecked()) {

                Diem++;

                dialogGoNextQuestion.isRightAnswer(true);
                dialogGoNextQuestion.setCauTraLoiDialogText(arrayTracNghiem.get(idCauHoiHienTai).CauB);
                dialogGoNextQuestion.setCauHoiDialogText(arrayTracNghiem.get(idCauHoiHienTai).CauHoi);
                dialogGoNextQuestion.show(getSupportFragmentManager(),"");
            }
            if(rdBtnCauC.isChecked()) {

                dialogGoNextQuestion.isRightAnswer(false);
                dialogGoNextQuestion.setCauTraLoiDialogText("The right answer is: "+arrayTracNghiem.get(idCauHoiHienTai).CauB);
                dialogGoNextQuestion.setCauHoiDialogText("Not: "+arrayTracNghiem.get(idCauHoiHienTai).CauC);
                dialogGoNextQuestion.show(getSupportFragmentManager(),"");
            }
            if(rdBtnCauD.isChecked()) {

                dialogGoNextQuestion.isRightAnswer(false);
                dialogGoNextQuestion.setCauTraLoiDialogText("The right answer is: "+arrayTracNghiem.get(idCauHoiHienTai).CauB);
                dialogGoNextQuestion.setCauHoiDialogText("Not: "+arrayTracNghiem.get(idCauHoiHienTai).CauD);
                dialogGoNextQuestion.show(getSupportFragmentManager(),"");
            }
        }
        //-------------------------------------------------------------------
        if(KetQua.equals("C")) {
            if(rdBtnCauA.isChecked()) {

                dialogGoNextQuestion.isRightAnswer(false);
                dialogGoNextQuestion.setCauTraLoiDialogText("The right answer is: "+arrayTracNghiem.get(idCauHoiHienTai).CauC);
                dialogGoNextQuestion.setCauHoiDialogText("Not: "+arrayTracNghiem.get(idCauHoiHienTai).CauA);
                dialogGoNextQuestion.show(getSupportFragmentManager(),"");
            }
            if(rdBtnCauB.isChecked()) {

                dialogGoNextQuestion.isRightAnswer(false);
                dialogGoNextQuestion.setCauTraLoiDialogText("The right answer is: "+arrayTracNghiem.get(idCauHoiHienTai).CauC);
                dialogGoNextQuestion.setCauHoiDialogText("Not: "+arrayTracNghiem.get(idCauHoiHienTai).CauB);
                dialogGoNextQuestion.show(getSupportFragmentManager(),"");
            }
            if(rdBtnCauC.isChecked()) {
                Diem++;

                dialogGoNextQuestion.isRightAnswer(true);
                dialogGoNextQuestion.setCauTraLoiDialogText(arrayTracNghiem.get(idCauHoiHienTai).CauC);
                dialogGoNextQuestion.setCauHoiDialogText(arrayTracNghiem.get(idCauHoiHienTai).CauHoi);
                dialogGoNextQuestion.show(getSupportFragmentManager(),"");
            }
            if(rdBtnCauD.isChecked()) {

                dialogGoNextQuestion.isRightAnswer(false);
                dialogGoNextQuestion.setCauTraLoiDialogText("The right answer is: "+arrayTracNghiem.get(idCauHoiHienTai).CauC);
                dialogGoNextQuestion.setCauHoiDialogText("Not: "+arrayTracNghiem.get(idCauHoiHienTai).CauD);
                dialogGoNextQuestion.show(getSupportFragmentManager(),"");
            }
        }
        //-------------------------------------------------------------------
        if(KetQua.equals("D")) {
            if(rdBtnCauA.isChecked()) {

                dialogGoNextQuestion.isRightAnswer(false);
                dialogGoNextQuestion.setCauTraLoiDialogText("The right answer is: "+arrayTracNghiem.get(idCauHoiHienTai).CauD);
                dialogGoNextQuestion.setCauHoiDialogText("Not: "+arrayTracNghiem.get(idCauHoiHienTai).CauA);
                dialogGoNextQuestion.show(getSupportFragmentManager(),"");
            }
            if(rdBtnCauB.isChecked()) {

                dialogGoNextQuestion.isRightAnswer(false);
                dialogGoNextQuestion.setCauTraLoiDialogText("The right answer is: "+arrayTracNghiem.get(idCauHoiHienTai).CauD);
                dialogGoNextQuestion.setCauHoiDialogText("Not: "+arrayTracNghiem.get(idCauHoiHienTai).CauB);
                dialogGoNextQuestion.show(getSupportFragmentManager(),"");
            }
            if(rdBtnCauC.isChecked()) {

                dialogGoNextQuestion.isRightAnswer(false);
                dialogGoNextQuestion.setCauTraLoiDialogText("The right answer is: "+arrayTracNghiem.get(idCauHoiHienTai).CauD);
                dialogGoNextQuestion.setCauHoiDialogText("Not: "+arrayTracNghiem.get(idCauHoiHienTai).CauC);
                dialogGoNextQuestion.show(getSupportFragmentManager(),"");
            }
            if(rdBtnCauD.isChecked()) {

                Diem++;

                dialogGoNextQuestion.isRightAnswer(true);
                dialogGoNextQuestion.setCauTraLoiDialogText(arrayTracNghiem.get(idCauHoiHienTai).CauD);
                dialogGoNextQuestion.setCauHoiDialogText(arrayTracNghiem.get(idCauHoiHienTai).CauHoi);
                dialogGoNextQuestion.show(getSupportFragmentManager(),"");
            }
        }
    }

    private void readData() {
        String DATABASE_NAME = "apphoctiengnhat.sqlite";
        SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
        @SuppressLint("Recycle")
        Cursor cursor = database.rawQuery("SELECT * FROM TracNghiem"+ChucNang,null);

        arrayTracNghiem.clear();

        for(int i = 0 ; i < cursor.getCount() ; i++) {
            cursor.moveToPosition(i);
            int idTracNghiem = cursor.getInt   (0);
            String CauHoi    = cursor.getString(1);
            String CauA      = cursor.getString(2);
            String CauB      = cursor.getString(3);
            String CauC      = cursor.getString(4);
            String CauD      = cursor.getString(5);
            String CauTraLoi = cursor.getString(6);
            arrayTracNghiem.add(new ClassTracNghiem(idTracNghiem,CauHoi,CauA,CauB,CauC,CauD,CauTraLoi));
        }

        // Xác định tổng số câu hỏi
        float soCauHoi = (float) arrayTracNghiem.size();
        // Tính tỉ lệ phần trăm hoàn thành từng câu
        TiLePhanTramHoanThanhProgressBar = 100/ soCauHoi;

        Random random = new Random();
        int i = random.nextInt(cursor.getCount());

        // Có 3 dòng => cursor.getCount = 3
        // giá trị i random trong 0 , 1 , 2

        idCauHoiHienTai = i;

        txtCauHoi.setText(arrayTracNghiem.get(idCauHoiHienTai).CauHoi);
        rdBtnCauA.setText(arrayTracNghiem.get(idCauHoiHienTai).CauA);
        rdBtnCauB.setText(arrayTracNghiem.get(idCauHoiHienTai).CauB);
        rdBtnCauC.setText(arrayTracNghiem.get(idCauHoiHienTai).CauC);
        rdBtnCauD.setText(arrayTracNghiem.get(idCauHoiHienTai).CauD);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void goNextQuestion() {
        if(arrayTracNghiem.size() > 1) {

            arrayTracNghiem.remove(idCauHoiHienTai);

            Random random = new Random();
            int i = random.nextInt(arrayTracNghiem.size());
            CauHoiHienTai = arrayTracNghiem.get(i).idKiemTra;

            for( int j = 0 ; j < arrayTracNghiem.size() ; j++) {
                if( arrayTracNghiem.get(j).idKiemTra == CauHoiHienTai ) {

                    txtCauHoi.setText(arrayTracNghiem.get(j).CauHoi);
                    rdBtnCauA.setText(arrayTracNghiem.get(j).CauA);
                    rdBtnCauB.setText(arrayTracNghiem.get(j).CauB);
                    rdBtnCauC.setText(arrayTracNghiem.get(j).CauC);
                    rdBtnCauD.setText(arrayTracNghiem.get(j).CauD);

                    idCauHoiHienTai = j;

                    radioGroupKiemTra.clearCheck();
                    break;
                }
            }
            // update progress bar
            progressBar.setProgress((int)TiLePhanTramHoanThanhProgressBar,true);
            TiLePhanTramHoanThanhProgressBar = TiLePhanTramHoanThanhProgressBar + TiLePhanTramHoanThanhProgressBar;
            dialogGoNextQuestion.dismiss();

        } else {
            // update progress bar (câu cuối cùng)
            progressBar.setProgress((int)TiLePhanTramHoanThanhProgressBar,true);
            TiLePhanTramHoanThanhProgressBar = TiLePhanTramHoanThanhProgressBar + TiLePhanTramHoanThanhProgressBar;

            dialogGoNextQuestion.dismiss();
            dialogGoBack.setResult(Diem);
            dialogGoBack.show(getSupportFragmentManager(),"");
        }
    }

    @Override
    public void goBack() {
        finish();
    }
}
