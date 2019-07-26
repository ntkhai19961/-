package com.example.win7.apphoctiengnhat.LessonDetails.Fragment.Test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.win7.apphoctiengnhat.CustomViewPager.CustomViewPager;
import com.example.win7.apphoctiengnhat.LessonDetails.Fragment.Test.Adapter.ViewPagerVocabularyTestAdapter;
import com.example.win7.apphoctiengnhat.LessonDetails.Fragment.Test.model.VocabularyQuest;
import com.example.win7.apphoctiengnhat.R;
import com.example.win7.apphoctiengnhat.interfaces.Fragment_Lesson_Test_Interface;
import com.example.win7.apphoctiengnhat.interfaces.Fragment_Lesson_Test_Item_Interface;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by ntkhai1996 on 07-Apr-18.
 */

public class Fragment_LessonDetails_Test_Vocabulary extends Fragment implements Fragment_Lesson_Test_Item_Interface{

    private DatabaseReference mDatabase;
    private String lessonName = "";
    private String childLessonName = "";
    private ArrayList<VocabularyQuest> arrayQuest;
    private CustomViewPager viewPagerVocabularyTest;
    private ViewPagerVocabularyTestAdapter adapter;
    private Fragment_Lesson_Test_Interface listener;
    private int currentQuest = 0;

    public static Fragment_LessonDetails_Test_Vocabulary newInstance(String lessonName, String childLessonName) {

        Bundle args = new Bundle();
        args.putString("lessonName", lessonName );
        args.putString("childLessonName", childLessonName );
        Fragment_LessonDetails_Test_Vocabulary fragment = new Fragment_LessonDetails_Test_Vocabulary();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLessonName();

        initFireBase();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lesson_details_test_vocabulary, container, false);

        init();

        getVocabularyQuestion();

        initView(rootView);

        return rootView;
    }

    private void updateTotalResultAndNumberAnswer() {
        if(listener != null){
            listener.setTotalResult(arrayQuest.size());
            listener.setTotalNumberAnswer(arrayQuest.size());
        }
    }

    public void setListener(Fragment_Lesson_Test_Interface listener) {
        this.listener = listener;
    }

    private void init() {
        arrayQuest = new ArrayList<>();
    }

    private void initView(View rootView) {

        adapter = new ViewPagerVocabularyTestAdapter(getChildFragmentManager());

        viewPagerVocabularyTest = rootView.findViewById(R.id.viewPagerVocabularyTest);
        viewPagerVocabularyTest.setPagingEnabled(false);

        addFragment();
    }

    private void addFragment() {

        // when get all data done
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {

                int size = arrayQuest.size();

                viewPagerVocabularyTest.setOffscreenPageLimit(size);

                for(int i = 0; i < size ; i++) {

                    Fragment_LessonDetails_Test_Vocabulary_Item fragment = Fragment_LessonDetails_Test_Vocabulary_Item.newInstance(arrayQuest.get(i),i,size);
                    fragment.setListener(Fragment_LessonDetails_Test_Vocabulary.this);

                    adapter.addFragment(fragment);
                }

                viewPagerVocabularyTest.setAdapter(adapter);

                updateTotalResultAndNumberAnswer();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    private void getVocabularyQuestion() {

        mDatabase.child("Lesson").child("N5").child("SoCap2").child(lessonName).child(childLessonName).child("Testing").child("Vocabulary").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                VocabularyQuest quest = dataSnapshot.getValue(VocabularyQuest.class);
                arrayQuest.add(quest);
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

    private void initFireBase() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private void getLessonName() {
        lessonName = Objects.requireNonNull(this.getArguments()).getString("lessonName");
        childLessonName = this.getArguments().getString("childLessonName");
    }

    @Override
    public void onNextQuest(int id, boolean isRightAnswer) {

        Log.e("currentQuest",currentQuest+"");

        int size = arrayQuest.size();

        if(currentQuest < size)
            currentQuest++;

        Log.e("currentQuest++",currentQuest+"");

        // go to grammar test
        if(currentQuest >= size && listener != null)
            listener.onEndVocabularyTest();

        // right answer => update result
        if(isRightAnswer && listener != null){
            listener.onUpdateResult();
            listener.onUpdateNumberRightAnswer();
        }

        arrayQuest.get(id).setRightAnswer(isRightAnswer);

        viewPagerVocabularyTest.setCurrentItem(currentQuest);
    }

    @Override
    public void onPrevQuest() {

        //Log.e("currentQuest",currentQuest+"");

        if(currentQuest > 0){
            currentQuest--;

            //Log.e("currentQuest--",currentQuest+"");

            if(arrayQuest.get(currentQuest).isRightAnswer() && listener != null){
                listener.onMinusResult();
                listener.onMinusNumberRightAnswer();
            }
            viewPagerVocabularyTest.setCurrentItem(currentQuest);
        }
    }
}
