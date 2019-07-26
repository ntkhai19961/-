package com.example.win7.apphoctiengnhat.Dialog.LessonTest.NextGrammarQuest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.win7.apphoctiengnhat.R;
import com.example.win7.apphoctiengnhat.interfaces.Dialog_Lesson_Test_Next_Grammar_Quest_Interface;

import java.util.Objects;

public class DialogNextGrammarQuest extends DialogFragment implements View.OnClickListener {

    private Dialog_Lesson_Test_Next_Grammar_Quest_Interface listener;

    public void setListener(Dialog_Lesson_Test_Next_Grammar_Quest_Interface listener) {
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

        return inflater.inflate(R.layout.dialog_lesson_test_next_grammar_quest,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        Button btnNextGrammarQuestCancle = view.findViewById(R.id.btnNextGrammarQuestCancle);
        Button btnNextGrammarQuestOK     = view.findViewById(R.id.btnNextGrammarQuestOK);

        btnNextGrammarQuestOK.setOnClickListener(this);
        btnNextGrammarQuestCancle.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnNextGrammarQuestCancle:
                onCancle();
                break;
            case R.id.btnNextGrammarQuestOK:
                onOK();
                break;
        }
    }

    private void onOK() {
        if(listener != null)
            listener.onOkDialog();
    }

    private void onCancle() {
        if(listener != null)
            listener.onCancleDialog();
    }
}
