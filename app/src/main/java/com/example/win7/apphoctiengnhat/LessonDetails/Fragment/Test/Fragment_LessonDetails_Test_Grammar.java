package com.example.win7.apphoctiengnhat.LessonDetails.Fragment.Test;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.win7.apphoctiengnhat.Dialog.LessonTest.NextGrammarQuest.DialogNextGrammarQuest;
import com.example.win7.apphoctiengnhat.LessonDetails.Fragment.Test.model.GrammarTest;
import com.example.win7.apphoctiengnhat.R;
import com.example.win7.apphoctiengnhat.interfaces.Dialog_Lesson_Test_Next_Grammar_Quest_Interface;
import com.example.win7.apphoctiengnhat.interfaces.Fragment_Lesson_Test_Interface;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hanks.library.AnimateCheckBox;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by ntkhai1996 on 07-Apr-18.
 */

public class Fragment_LessonDetails_Test_Grammar extends Fragment implements View.OnClickListener, AnimateCheckBox.OnCheckedChangeListener, Dialog_Lesson_Test_Next_Grammar_Quest_Interface {

    private DatabaseReference mDatabase;
    private ArrayList<GrammarTest> arrayQuest;
    private String lessonName = "";
    private String childLessonName = "";
    private TextView txtLessonGrammar_Question;
    private TextView txtAnswerA, txtAnswerB, txtAnswerC, txtAnswerD;
    private long idQuestionNow = 0;
    private AnimateCheckBox chkbAnswerA, chkbAnswerB, chkbAnswerC, chkbAnswerD;
    private Fragment_Lesson_Test_Interface listener;
    private DialogNextGrammarQuest dialog;
    private boolean isRightAnswer;


    public static Fragment_LessonDetails_Test_Grammar newInstance(String lessonName, String childLessonName) {

        Bundle args = new Bundle();
        args.putString("lessonName", lessonName );
        args.putString("childLessonName", childLessonName );
        Fragment_LessonDetails_Test_Grammar fragment = new Fragment_LessonDetails_Test_Grammar();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLessonName();
        
        init();

        initFireBase();

        getGrammarQuestion();
    }

    public void setListener(Fragment_Lesson_Test_Interface listener) {
        this.listener = listener;
    }

    private void init() {
        arrayQuest = new ArrayList<>();
    }

    private void initFireBase() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private void getLessonName() {
        lessonName = Objects.requireNonNull(this.getArguments()).getString("lessonName");
        childLessonName = this.getArguments().getString("childLessonName");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lesson_details_test_grammar , container , false);

        initView(rootView);

        return rootView;
    }

    private void initView(View rootView) {

        CardView cardViewA = rootView.findViewById(R.id.cardViewA);
        CardView cardViewB = rootView.findViewById(R.id.cardViewB);
        CardView cardViewC = rootView.findViewById(R.id.cardViewC);
        CardView cardViewD = rootView.findViewById(R.id.cardViewD);
        txtAnswerA  = rootView.findViewById(R.id.txtAnswerA);
        txtAnswerB  = rootView.findViewById(R.id.txtAnswerB);
        txtAnswerC  = rootView.findViewById(R.id.txtAnswerC);
        txtAnswerD  = rootView.findViewById(R.id.txtAnswerD);
        chkbAnswerA = rootView.findViewById(R.id.chkbAnswerA);
        chkbAnswerB = rootView.findViewById(R.id.chkbAnswerB);
        chkbAnswerC = rootView.findViewById(R.id.chkbAnswerC);
        chkbAnswerD = rootView.findViewById(R.id.chkbAnswerD);
        dialog      = new DialogNextGrammarQuest();
        txtLessonGrammar_Question = rootView.findViewById(R.id.txtLessonGrammar_Question);

        setQuestion();

        cardViewA.setOnClickListener(this);
        cardViewB.setOnClickListener(this);
        cardViewC.setOnClickListener(this);
        cardViewD.setOnClickListener(this);

        chkbAnswerA.setOnClickListener(this);
        chkbAnswerB.setOnClickListener(this);
        chkbAnswerC.setOnClickListener(this);
        chkbAnswerD.setOnClickListener(this);

        chkbAnswerA.setOnCheckedChangeListener(this);
        chkbAnswerB.setOnCheckedChangeListener(this);
        chkbAnswerC.setOnCheckedChangeListener(this);
        chkbAnswerD.setOnCheckedChangeListener(this);

        dialog.setListener(this);
    }


    private void checkRightAnswer(String answerSelected) {

        switch (answerSelected){

            case "A":
                if(txtAnswerA.getText().equals(arrayQuest.get((int) idQuestionNow).getRightAnswer()))
                    //dialog(getContext(), true).show();
                    isRightAnswer = true;
                else
                    //dialog(getContext(), false).show();
                    isRightAnswer = false;
                break;

            case "B":
                if(txtAnswerB.getText().equals(arrayQuest.get((int) idQuestionNow).getRightAnswer()))
                    //dialog(getContext(), true).show();
                    isRightAnswer = true;
                else
                    //dialog(getContext(), false).show();
                    isRightAnswer = false;
                break;

            case "C":
                if(txtAnswerC.getText().equals(arrayQuest.get((int) idQuestionNow).getRightAnswer()))
                    //dialog(getContext(), true).show();
                    isRightAnswer = true;
                else
                    //dialog(getContext(), false).show();
                    isRightAnswer = false;
                break;

            case "D":
                if(txtAnswerD.getText().equals(arrayQuest.get((int) idQuestionNow).getRightAnswer()))
                    //dialog(getContext(), true).show();
                    isRightAnswer = true;
                else
                    //dialog(getContext(), false).show();
                    isRightAnswer = false;
                break;
        }

        dialog.show(getChildFragmentManager(), "");
    }

    private void updateCurrentResult() {

        if(listener != null){
            listener.onUpdateResult();
            listener.onUpdateNumberRightAnswer();
        }
    }

    private void setNextQuestion() {

        idQuestionNow++;

        if(idQuestionNow < arrayQuest.size()){
            txtAnswerA.setText(arrayQuest.get((int) idQuestionNow).getAnswer1());
            txtAnswerB.setText(arrayQuest.get((int) idQuestionNow).getAnswer2());
            txtAnswerC.setText(arrayQuest.get((int) idQuestionNow).getAnswer3());
            txtAnswerD.setText(arrayQuest.get((int) idQuestionNow).getAnswer4());
            txtLessonGrammar_Question.setText(arrayQuest.get((int) idQuestionNow).getQuestion());

            chkbAnswerA.setChecked(false);
            chkbAnswerB.setChecked(false);
            chkbAnswerC.setChecked(false);
            chkbAnswerD.setChecked(false);

        } else if(listener != null) {
            listener.onEndGrammarTest();
        }
    }

    private AlertDialog.Builder dialog(Context c, boolean rightAnswer) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("Are you sure ??");
        builder.setMessage("Go to next question ??");
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(rightAnswer){
                    updateCurrentResult();
                    setNextQuestion();
                } else {
                    setNextQuestion();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                chkbAnswerA.setChecked(false);
                chkbAnswerB.setChecked(false);
                chkbAnswerC.setChecked(false);
                chkbAnswerD.setChecked(false);
            }
        });

        return builder;
    }

    private void setQuestion() {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                txtLessonGrammar_Question.setText(arrayQuest.get(0).getQuestion());
                txtAnswerA.setText(arrayQuest.get(0).getAnswer1());
                txtAnswerB.setText(arrayQuest.get(0).getAnswer2());
                txtAnswerC.setText(arrayQuest.get(0).getAnswer3());
                txtAnswerD.setText(arrayQuest.get(0).getAnswer4());

                if(listener != null){
                    listener.setTotalResult(arrayQuest.size());
                    listener.setTotalNumberAnswer(arrayQuest.size());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getGrammarQuestion(){
        mDatabase.child("Lesson").child("N5").child("SoCap2").child(lessonName).child(childLessonName).child("Testing").child("Grammar").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                GrammarTest grammarTest = dataSnapshot.getValue(GrammarTest.class);
                arrayQuest.add(grammarTest);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.chkbAnswerA:
                resetCheckView("A");
                break;
            case R.id.chkbAnswerB:
                resetCheckView("B");
                break;
            case R.id.chkbAnswerC:
                resetCheckView("C");
                break;
            case R.id.chkbAnswerD:
                resetCheckView("D");
                break;

            case R.id.cardViewA:
                resetCheckView("A");
                break;
            case R.id.cardViewB:
                resetCheckView("B");
                break;
            case R.id.cardViewC:
                resetCheckView("C");
                break;
            case R.id.cardViewD:
                resetCheckView("D");
                break;

            default:
                break;
        }

    }

    private void resetCheckView(String answerSelected) {

        switch (answerSelected){

            case "A":
                chkbAnswerA.setChecked(true);
                chkbAnswerB.setChecked(false);
                chkbAnswerC.setChecked(false);
                chkbAnswerD.setChecked(false);
                break;
            case "B":
                chkbAnswerA.setChecked(false);
                chkbAnswerB.setChecked(true);
                chkbAnswerC.setChecked(false);
                chkbAnswerD.setChecked(false);
                break;
            case "C":
                chkbAnswerB.setChecked(false);
                chkbAnswerA.setChecked(false);
                chkbAnswerC.setChecked(true);
                chkbAnswerD.setChecked(false);
                break;
            case "D":
                chkbAnswerB.setChecked(false);
                chkbAnswerC.setChecked(false);
                chkbAnswerA.setChecked(false);
                chkbAnswerD.setChecked(true);
                break;
        }
    }

    @Override
    public void onCheckedChanged(View buttonView, boolean isChecked) {

        switch (buttonView.getId()){

            case R.id.chkbAnswerA:
                if(isChecked)
                    onEndAnimation("A");
                break;
            case R.id.chkbAnswerB:
                if(isChecked)
                    onEndAnimation("B");
                break;
            case R.id.chkbAnswerC:
                if(isChecked)
                    onEndAnimation("C");
                break;
            case R.id.chkbAnswerD:
                if(isChecked)
                    onEndAnimation("D");
                break;
            default:
                break;
        }

    }

    private void onEndAnimation(String answerSelected) {

        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkRightAnswer(answerSelected);
            }
        }, 200);
    }

    @Override
    public void onOkDialog() {
        if(isRightAnswer){
            updateCurrentResult();
            setNextQuestion();
        } else {
            setNextQuestion();
        }
        dialog.dismiss();
    }

    @Override
    public void onCancleDialog() {
        chkbAnswerA.setChecked(false);
        chkbAnswerB.setChecked(false);
        chkbAnswerC.setChecked(false);
        chkbAnswerD.setChecked(false);
        dialog.dismiss();
    }
}
