package com.example.win7.apphoctiengnhat.LessonDetails.Fragment.NguPhap;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.win7.apphoctiengnhat.CustomViewPager.CustomViewPager;
import com.example.win7.apphoctiengnhat.LessonDetails.Fragment.NguPhap.adapter.GrammarAdapter;
import com.example.win7.apphoctiengnhat.LessonDetails.Fragment.NguPhap.model.Grammar;
import com.example.win7.apphoctiengnhat.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import qdx.bezierviewpager_compile.BezierRoundView;
import qdx.bezierviewpager_compile.vPage.BezierViewPager;
import qdx.bezierviewpager_compile.vPage.CardPagerAdapter;

/**
 * Created by ntkhai1996 on 19-Dec-17.
 */

public class Fragment_LessonDetails_NguPhap extends Fragment {

    private DatabaseReference mDatabase;
    private String lessonName = "";
    private String childLessonName = "";
    private ArrayList<Grammar> arrayGrammar;
    private GrammarAdapter adapter;
    private CardPagerAdapter cardPagerAdapter;
    private List<Object> lstImage;
    private BezierViewPager viewPager;
    private BezierRoundView bezRound;
    private CustomViewPager viewPagerGrammar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getLessonName();

    }

    private void getLessonName() {
        lessonName = Objects.requireNonNull(this.getArguments()).getString("lessonName");
        childLessonName = this.getArguments().getString("childLessonName");
    }

    private void getGrammar() {

        mDatabase.child("Lesson").child("N5").child("SoCap2").child(lessonName).child(childLessonName).child("Grammar").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Grammar grammar = dataSnapshot.getValue(Grammar.class);
                arrayGrammar.add(grammar);

                lstImage.add(Objects.requireNonNull(grammar).getImgURL());
                cardPagerAdapter.notifyDataSetChanged();
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

    private void init() {

        mDatabase = FirebaseDatabase.getInstance().getReference();

        lstImage     = new ArrayList<>();
        arrayGrammar = new ArrayList<>();
        adapter      = new GrammarAdapter(getChildFragmentManager());
        cardPagerAdapter = new CardPagerAdapter(getContext());

        addFragment();
    }

    private void addFragment() {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (Grammar anGrammar : arrayGrammar) {
                    adapter.addFragment(FragmentGrammarItem.newInstance(anGrammar));
                }

                viewPagerGrammar.setOffscreenPageLimit(arrayGrammar.size());
                viewPagerGrammar.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lesson_details_ngu_phap, container, false);

        initView(rootView);
        init();

        getGrammar();

        setImgBezierViewPager();

        onViewPagerChange();

        onViewPagerGrammarChange();

        return rootView;
    }

    private void onViewPagerGrammarChange() {
        viewPagerGrammar.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void onViewPagerChange() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPagerGrammar.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setImgBezierViewPager() {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cardPagerAdapter.addImgUrlList(lstImage);
                viewPager.setAdapter(cardPagerAdapter);
                bezRound.attach2ViewPage(viewPager);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initView(View rootView) {
        viewPager  = rootView.findViewById(R.id.bezierViewPager);
        bezRound   = rootView.findViewById(R.id.bezierRoundView);
        viewPagerGrammar = rootView.findViewById(R.id.viewPagerGrammar);
    }

//        // giúp trong việc scroll page không bị nửa vời , hoặc liên tục khi scroll quá mạnh tay
//        // nghĩa là khi scroll tới phân nữa sẽ tự động về page bên trái (nếu hơn phần nữa về trái) hoặc phải (nếu hơn phần nữa về phải)
//        // khi scroll thì chỉ scroll dc từng 1 page
//        SnapHelper mSnapHelper = new PagerSnapHelper();
//        mSnapHelper.attachToRecyclerView(rcvGrammarFragment);

}
