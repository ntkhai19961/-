package com.example.win7.apphoctiengnhat.Dialog.LessonTest.LevelUp;

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
import com.example.win7.apphoctiengnhat.interfaces.Dialog_Lesson_Level_Up;

import java.util.Objects;

public class DialogLessonLevelUp extends DialogFragment {

    private String CurrentLevel        = "";
    private String CurrentUnlockLesson = "";
    private String NextLevel           = "";
    private String NextUnlockLesson    = "";
    private Dialog_Lesson_Level_Up listener;
    private Button   btnLessonLevelUpOK;

    public void setListener(Dialog_Lesson_Level_Up listener) {
        this.listener = listener;
    }

    public void setCurrentLevel(String currentLevel) {
        CurrentLevel = currentLevel;
    }

    public void setCurrentUnlockLesson(String currentUnlockLesson) {
        CurrentUnlockLesson = currentUnlockLesson;
    }

    public void setNextLevel(String nextLevel) {
        NextLevel = nextLevel;
    }

    public void setNextUnlockLesson(String nextUnlockLesson) {
        NextUnlockLesson = nextUnlockLesson;
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
        Objects.requireNonNull(getDialog().getWindow()).getAttributes().windowAnimations = R.style.BounceDialog;
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.border_custom_dialog);

        return inflater.inflate(R.layout.dialog_lesson_level_up,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    private void initView(View view) {
        TextView txtCurrentLevel        = view.findViewById(R.id.txtCurrentLevel);
        TextView txtNextLevel           = view.findViewById(R.id.txtNextLevel);
        TextView txtCurrentUnlockLesson = view.findViewById(R.id.txtCurrentUnlockLesson);
        TextView txtNextUnlockLesson    = view.findViewById(R.id.txtNextUnlockLesson);
        btnLessonLevelUpOK              = view.findViewById(R.id.btnLessonLevelUpOK);

        txtCurrentLevel.setText(CurrentLevel);
        txtCurrentUnlockLesson.setText(CurrentUnlockLesson);

        txtNextLevel.setText(NextLevel);
        txtNextUnlockLesson.setText(NextUnlockLesson);

        setOnTouchButton();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setOnTouchButton() {
        View.OnTouchListener onTouch = new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        btnLessonLevelUpOK.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkOnTouch, null));
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        btnLessonLevelUpOK.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkOnTouch, null));
                        break;
                    case MotionEvent.ACTION_UP:
                        btnLessonLevelUpOK.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark, null));
                        onOK();
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        btnLessonLevelUpOK.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark, null));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        btnLessonLevelUpOK.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark, null));
                        break;
                }
                return true;
            }
        };
        btnLessonLevelUpOK.setOnTouchListener(onTouch);
    }


    private void onOK() {
        if(listener != null)
            listener.onOK();
    }
}
