package com.example.win7.apphoctiengnhat.Dialog.MiniTest;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.win7.apphoctiengnhat.R;
import com.example.win7.apphoctiengnhat.interfaces.Dialog_MiniTest_Interface;

import java.util.Objects;

public class DialogGoNextQuestionMiniTest extends DialogFragment{

    private TextView txtChinhXacDialog;
    private TextView txtCauTraLoiDialog;
    private TextView txtCauHoiDialog;
    private Button btnTiepTheoDialog;
    private Dialog_MiniTest_Interface listener;
    private String chinhXacDialog  = "";
    private String cauTraLoiDialog = "";
    private String cauHoiDialog    = "";

    public void setListener(Dialog_MiniTest_Interface listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().requestWindowFeature(STYLE_NO_TITLE);
        setCancelable(false);
        Objects.requireNonNull(getDialog().getWindow()).getAttributes().windowAnimations = R.style.FadeScaleOutIn;
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.border_custom_dialog);

        return inflater.inflate(R.layout.dialog_mini_test_go_next_question, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    private void initView(View view) {
        txtChinhXacDialog  = view.findViewById(R.id.txtChinhXacDialog);
        txtCauTraLoiDialog = view.findViewById(R.id.txtCauTraLoiDialog);
        txtCauHoiDialog    = view.findViewById(R.id.txtCauHoiDialog);
        btnTiepTheoDialog  = view.findViewById(R.id.btnTiepTheoDialog);

        setOnTouchTestButton();
        setText();
    }

    private void setText() {
        txtChinhXacDialog.setText(chinhXacDialog);
        txtCauTraLoiDialog.setText(cauTraLoiDialog);
        txtCauHoiDialog.setText(cauHoiDialog);
    }

    public void isRightAnswer(boolean isRightAnswer){
        if(isRightAnswer)
            chinhXacDialog = "Good job!!";
        else
            chinhXacDialog = "Oops!!";
    }

    public void setCauTraLoiDialogText(String text){
        cauTraLoiDialog = text;
    }

    public void setCauHoiDialogText(String text){
        cauHoiDialog = text;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setOnTouchTestButton() {
        View.OnTouchListener onTouch = new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        btnTiepTheoDialog.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkOnTouch, null));
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        btnTiepTheoDialog.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkOnTouch, null));
                        break;
                    case MotionEvent.ACTION_UP:
                        btnTiepTheoDialog.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark, null));
                        goNextQuestion();
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        btnTiepTheoDialog.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark, null));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        btnTiepTheoDialog.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark, null));
                        break;
                }
                return true;
            }
        };
        btnTiepTheoDialog.setOnTouchListener(onTouch);
    }

    private void goNextQuestion() {
        if(listener != null)
            listener.goNextQuestion();
    }
}
