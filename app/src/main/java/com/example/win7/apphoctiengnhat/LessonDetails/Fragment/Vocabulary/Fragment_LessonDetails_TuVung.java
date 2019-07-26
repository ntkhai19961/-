package com.example.win7.apphoctiengnhat.LessonDetails.Fragment.Vocabulary;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.win7.apphoctiengnhat.LessonDetails.Fragment.Vocabulary.adapter.LessonDetailsVocabularyAdapter;
import com.example.win7.apphoctiengnhat.LessonDetails.Fragment.Vocabulary.model.ClassLessonDetailsVocabulary;
import com.example.win7.apphoctiengnhat.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by ntkhai1996 on 19-Dec-17.
 */

public class Fragment_LessonDetails_TuVung extends Fragment {

    private RecyclerView rcvVocabulary;
    private ArrayList<ClassLessonDetailsVocabulary> arrayListVocabulary;
    private LessonDetailsVocabularyAdapter adapter;
    private ProgressBar progressBar_Fragment_LessonDetails_TuVung;
    private DatabaseReference mDatabase;
    private String lessonName;
    private String childLessonName;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lesson_details_tu_vung, container, false);

        getLessonName();

        setUpFireBase();

        initView(rootView);

        readDataFromFirebase();

        return rootView;
    }

    private void getLessonName() {
        lessonName = this.getArguments().getString("lessonName");
        childLessonName = this.getArguments().getString("childLessonName");
    }

    private void setUpFireBase() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private void initView(View rootView) {

        arrayListVocabulary = new ArrayList<>();

        rcvVocabulary = rootView.findViewById(R.id.rcvVocabulary);
        setupRecycleView();

        progressBar_Fragment_LessonDetails_TuVung = rootView.findViewById(R.id.progressBar_Fragment_LessonDetails_TuVung);
        progressBar_Fragment_LessonDetails_TuVung.setVisibility(View.VISIBLE);
    }

    private void setupRecycleView() {
        rcvVocabulary.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rcvVocabulary.setLayoutManager(layoutManager);

        adapter = new LessonDetailsVocabularyAdapter(getContext(),arrayListVocabulary);
        rcvVocabulary.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("言葉");
    }

    private void readDataFromFirebase() {

        mDatabase.child("Lesson").child("N5").child("SoCap2").child(lessonName).child(childLessonName).child("Vocabulary").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                progressBar_Fragment_LessonDetails_TuVung.setVisibility(View.INVISIBLE);

                ClassLessonDetailsVocabulary classLessonDetailsVocabulary = dataSnapshot.getValue(ClassLessonDetailsVocabulary.class);
                arrayListVocabulary.add(classLessonDetailsVocabulary);
                sortArrayListByID(arrayListVocabulary);

                adapter.notifyDataSetChanged();
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
                if (databaseError != null)
                    Toast.makeText(Fragment_LessonDetails_TuVung.this.getContext(), "Error fragment lesson details vocabulary", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void sortArrayListByID(ArrayList<ClassLessonDetailsVocabulary> arrayListVocabulary) {

        Comparator<ClassLessonDetailsVocabulary> cmp=new Comparator<ClassLessonDetailsVocabulary>(){
            public int compare(ClassLessonDetailsVocabulary a,ClassLessonDetailsVocabulary b){
                if(a.getId()!=b.getId()){
                    return a.getId()-b.getId();
                }
                return Integer.compare(a.getId(), b.getId());
            }
        };

        arrayListVocabulary.sort(cmp);
    }
}
