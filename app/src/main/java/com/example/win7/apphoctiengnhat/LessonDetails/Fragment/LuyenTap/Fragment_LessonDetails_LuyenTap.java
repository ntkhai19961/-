package com.example.win7.apphoctiengnhat.LessonDetails.Fragment.LuyenTap;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.win7.apphoctiengnhat.LessonDetails.Fragment.LuyenTap.adapter.PracticeAdapter;
import com.example.win7.apphoctiengnhat.LessonDetails.Fragment.LuyenTap.model.Practice;
import com.example.win7.apphoctiengnhat.LessonDetails.Fragment.NguPhap.model.Grammar;
import com.example.win7.apphoctiengnhat.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by ntkhai1996 on 19-Dec-17.
 */

public class Fragment_LessonDetails_LuyenTap extends Fragment {

    private DatabaseReference mDatabase;
    private String lessonName = "";
    private String childLessonName = "";
    private ViewPager viewPagerPractice;
    private ArrayList<Practice> arrayPractice;
    private PracticeAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLessonName();
    }

    private void getLessonName() {
        lessonName = Objects.requireNonNull(this.getArguments()).getString("lessonName");
        childLessonName = this.getArguments().getString("childLessonName");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lesson_details_luyen_tap, container, false);

        initView(rootView);

        init();

        getPractice();

        return rootView;
    }

    private void initView(View rootView) {
        viewPagerPractice = rootView.findViewById(R.id.viewPagerPractice);
    }

    private void getPractice() {
        mDatabase.child("Lesson").child("N5").child("SoCap2").child(lessonName).child(childLessonName).child("Practice").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Practice practice = dataSnapshot.getValue(Practice.class);
                arrayPractice.add(practice);
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

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(Practice anPractice : arrayPractice){
                    adapter.addFragment(FragmentPracticeItem.newInstance(anPractice));
                }

                viewPagerPractice.setOffscreenPageLimit(arrayPractice.size());
                viewPagerPractice.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void init() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        arrayPractice = new ArrayList<>();
        adapter = new PracticeAdapter(getChildFragmentManager());
    }
}
