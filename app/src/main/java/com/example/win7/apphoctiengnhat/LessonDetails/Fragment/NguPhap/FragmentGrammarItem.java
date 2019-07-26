package com.example.win7.apphoctiengnhat.LessonDetails.Fragment.NguPhap;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.win7.apphoctiengnhat.LessonDetails.Fragment.NguPhap.model.Grammar;
import com.example.win7.apphoctiengnhat.R;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;

import java.util.Objects;

/**
 * Created by ntkhai1996 on 27-Apr-18.
 */

public class FragmentGrammarItem extends Fragment implements View.OnClickListener{

    private TextView txtGrammarExample;
    private ImageButton imgBtnExpandGrammarFragment;
    private ExpandableLinearLayout expandableLayoutGrammarFragment;
    private RelativeLayout rltGrammarExample;
    private Grammar grammar;

    public static FragmentGrammarItem newInstance(Grammar grammar) {

        Bundle args = new Bundle();
        args.putSerializable("grammar",grammar);
        FragmentGrammarItem fragment = new FragmentGrammarItem();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getGrammar();
    }

    private void getGrammar() {
        try {
            grammar = (Grammar) Objects.requireNonNull(this.getArguments()).getSerializable("grammar");
        }
        catch (Exception ex){
            Log.e("FragmentGrammarItem",ex.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_grammar,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

        setupExpandableView();

        txtGrammarExample.setText(grammar.getExample());
    }

    private void setupExpandableView() {
        expandableLayoutGrammarFragment.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        expandableLayoutGrammarFragment.setDuration(500);
        setExpandableListener();
        imgBtnExpandGrammarFragment.setRotation(true ? 180f : 0f);

        setOnClick();
    }

    private void setExpandableListener(){
        expandableLayoutGrammarFragment.setListener(new ExpandableLayoutListenerAdapter() {

            @Override
            public void onPreOpen() {
                changeRotate(imgBtnExpandGrammarFragment, 0f, 180f).start();
            }

            @Override
            public void onPreClose() {
                changeRotate(imgBtnExpandGrammarFragment, 180f, 0f).start();
            }

        });
    }

    private void setOnClick() {
        rltGrammarExample.setOnClickListener(this);
        imgBtnExpandGrammarFragment.setOnClickListener(this);
        expandableLayoutGrammarFragment.setOnClickListener(this);
    }

    private ObjectAnimator changeRotate(ImageButton imageButton, float from, float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageButton, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }

    @SuppressLint("SetTextI18n")
    private void initView(View view) {
        TextView txtGrammarID = view.findViewById(R.id.txtGrammarID);
        TextView txtGrammar   = view.findViewById(R.id.txtGrammar);
        txtGrammarExample     = view.findViewById(R.id.txtGrammarExample);
        rltGrammarExample     = view.findViewById(R.id.rltGrammarExample);
        imgBtnExpandGrammarFragment     = view.findViewById(R.id.imgBtnExpandGrammarFragment);
        expandableLayoutGrammarFragment = view.findViewById(R.id.expandableLayoutGrammarFragment);

        txtGrammarID.setText("文法 (" + grammar.getId() + ")");
        txtGrammar.setText(grammar.getGrammar());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rltGrammarExample:
            case R.id.imgBtnExpandGrammarFragment:
            case R.id.expandableLayoutGrammarFragment:
                expandableLayoutGrammarFragment.toggle();
                break;
        }
    }
}
