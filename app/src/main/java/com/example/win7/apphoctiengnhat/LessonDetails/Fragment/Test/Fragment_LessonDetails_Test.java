package com.example.win7.apphoctiengnhat.LessonDetails.Fragment.Test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.win7.apphoctiengnhat.R;
import com.example.win7.apphoctiengnhat.interfaces.Fragment_Lesson_Test_Interface;

/**
 * Created by ntkhai1996 on 07-Apr-18.
 */

public class Fragment_LessonDetails_Test extends Fragment implements Fragment_Lesson_Test_Interface {

    private String lessonName = "";
    private String childLessonName = "";
    private Integer totalResult = 0;
    private Integer currentResult = 0;
    private Integer totalNumberAnswer = 0;
    private Integer numberRightAnswer = 0;
    private int idChildLesson;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLessonName();
    }

    private void getLessonName() {
        lessonName = this.getArguments().getString("lessonName");
        childLessonName = this.getArguments().getString("childLessonName");
        idChildLesson = this.getArguments().getInt("idChildLesson");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lesson_details_test,container,false);

        displayFragment(0);

        return rootView;
    }

    private void displayFragment(int position){

        FragmentManager fragmentManager = getChildFragmentManager();

        Fragment_LessonDetails_Test_Vocabulary fragment_test_vocabulary = null;
        Fragment_LessonDetails_Test_Grammar    fragment_test_grammar    = null;
        Fragment_LessonDetails_Test_Result     fragment_test_result     = null;

        switch (position)
        {
            case 0:
                fragment_test_vocabulary = Fragment_LessonDetails_Test_Vocabulary.newInstance(lessonName,childLessonName);
                fragment_test_vocabulary.setListener(this);
                break;
            case 1:
                fragment_test_grammar = Fragment_LessonDetails_Test_Grammar.newInstance(lessonName,childLessonName);
                fragment_test_grammar.setListener(this);
                break;
            case 2:
                fragment_test_result = new Fragment_LessonDetails_Test_Result();
                fragment_test_result.setListener(this);
                break;
            default:
                break;
        }

        if (fragment_test_vocabulary != null)
            fragmentManager.beginTransaction()
                    .replace(R.id.content_lesson_details_test, fragment_test_vocabulary)
                    .commit();

        if (fragment_test_grammar != null)
            fragmentManager.beginTransaction()
                    .replace(R.id.content_lesson_details_test, fragment_test_grammar)
                    .commit();

        if (fragment_test_result != null)
            fragmentManager.beginTransaction()
                    .replace(R.id.content_lesson_details_test, fragment_test_result)
                    .commit();
    }

    @Override
    public void onEndVocabularyTest() {
        displayFragment(1);
    }

    @Override
    public void onEndGrammarTest() {
        displayFragment(2);
    }

    @Override
    public void onUpdateResult() {
        currentResult += 100;
    }

    @Override
    public void onMinusResult() {
        currentResult -= 100;
    }

    @Override
    public void onUpdateNumberRightAnswer() {
        numberRightAnswer++;
        Log.e("numberRightAnswer",numberRightAnswer+"");
    }

    @Override
    public void onMinusNumberRightAnswer() {
        numberRightAnswer--;
        Log.e("numberRightAnswer",numberRightAnswer+"");
    }

    @Override
    public void setTotalResult(int numOfSentences) {
        // 1 câu 100 điểm => tổng điểm = tổng số câu*100
        totalResult = totalResult + numOfSentences*100;
    }

    @Override
    public void setTotalNumberAnswer(int numOfSentences) {
        totalNumberAnswer = totalNumberAnswer + numOfSentences;
    }

    @Override
    public Integer getNumberRightAnswer() {
        return numberRightAnswer;
    }

    @Override
    public Integer getCurrentResult() {
        return currentResult;
    }

    @Override
    public Integer getTotalNumberAnswer() {
        return totalNumberAnswer;
    }

    @Override
    public Integer getTotalResult() {
        return totalResult;
    }

    @Override
    public Integer getIdChildLesson() {
        return idChildLesson;
    }
}
