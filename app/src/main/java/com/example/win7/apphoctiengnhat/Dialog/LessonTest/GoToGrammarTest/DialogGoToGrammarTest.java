package com.example.win7.apphoctiengnhat.Dialog.LessonTest.GoToGrammarTest;

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

import com.example.win7.apphoctiengnhat.R;
import com.example.win7.apphoctiengnhat.interfaces.Dialog_Lesson_Test_Go_Grammar_Test_Interface;

import java.util.Objects;

public class DialogGoToGrammarTest extends DialogFragment{

    private Dialog_Lesson_Test_Go_Grammar_Test_Interface listener;
    private Button btnGoToGrammarTestCancle;
    private Button btnGoToGrammarTestOK;

    public void setListener(Dialog_Lesson_Test_Go_Grammar_Test_Interface listener) {
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

        return inflater.inflate(R.layout.dialog_lesson_test_go_to_grammar_test,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    private void initView(View view) {
        btnGoToGrammarTestCancle = view.findViewById(R.id.btnGoToGrammarTestCancle);
        btnGoToGrammarTestOK     = view.findViewById(R.id.btnGoToGrammarTestOK);

        setOnTouchCancleButton();
        setOnTouchOKButton();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setOnTouchOKButton() {
        View.OnTouchListener onTouch = new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        btnGoToGrammarTestOK.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkOnTouch, null));
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        btnGoToGrammarTestOK.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkOnTouch, null));
                        break;
                    case MotionEvent.ACTION_UP:
                        btnGoToGrammarTestOK.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark, null));
                        onOkDialog();
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        btnGoToGrammarTestOK.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark, null));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        btnGoToGrammarTestOK.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark, null));
                        break;
                }
                return true;
            }
        };
        btnGoToGrammarTestOK.setOnTouchListener(onTouch);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setOnTouchCancleButton() {
        View.OnTouchListener onTouch = new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        btnGoToGrammarTestCancle.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkOnTouch, null));
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        btnGoToGrammarTestCancle.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkOnTouch, null));
                        break;
                    case MotionEvent.ACTION_UP:
                        btnGoToGrammarTestCancle.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark, null));
                        onCancleDialog();
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        btnGoToGrammarTestCancle.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark, null));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        btnGoToGrammarTestCancle.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark, null));
                        break;
                }
                return true;
            }
        };
        btnGoToGrammarTestCancle.setOnTouchListener(onTouch);
    }

    private void onCancleDialog() {
        if(listener != null)
            listener.onCancleDialog();
    }

    private void onOkDialog() {
        if(listener != null)
            listener.onOkDialog();
    }
}
