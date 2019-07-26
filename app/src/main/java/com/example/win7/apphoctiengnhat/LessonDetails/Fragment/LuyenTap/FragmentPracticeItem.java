package com.example.win7.apphoctiengnhat.LessonDetails.Fragment.LuyenTap;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.win7.apphoctiengnhat.LessonDetails.Fragment.LuyenTap.model.Practice;
import com.example.win7.apphoctiengnhat.R;

import java.util.Objects;

public class FragmentPracticeItem extends Fragment {

    private Practice practice;
    private TextView txtPracticeID;
    private TextView txtPractice;
    private TextView txtPracticeExample;
    private TextView txtIimashou;

    public static FragmentPracticeItem newInstance(Practice practice) {

        Bundle args = new Bundle();
        args.putSerializable("practice", practice);
        FragmentPracticeItem fragment = new FragmentPracticeItem();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPractice();
    }

    private void getPractice(){
        try {
            practice = (Practice) Objects.requireNonNull(this.getArguments()).getSerializable("practice");
        }
        catch (Exception ex){
            Log.e("FragmentGrammarItem",ex.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_lesson_details_practice, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

        setText();
    }

    @SuppressLint("SetTextI18n")
    private void setText() {
        txtPracticeID.setText("文法 (" + practice.getId() + ")");
        txtIimashou.setText(practice.getIimashou());
        txtPracticeExample.setText(practice.getHanashimashou());
    }

    private void initView(View view) {
        txtPracticeID      = view.findViewById(R.id.txtPracticeID);
        txtPractice        = view.findViewById(R.id.txtPractice);
        txtPracticeExample = view.findViewById(R.id.txtPracticeExample);
        txtIimashou        = view.findViewById(R.id.txtIimashou);
    }
}
