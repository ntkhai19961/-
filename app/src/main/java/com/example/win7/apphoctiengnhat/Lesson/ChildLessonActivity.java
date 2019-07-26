package com.example.win7.apphoctiengnhat.Lesson;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.win7.apphoctiengnhat.Constant.UserInfo;
import com.example.win7.apphoctiengnhat.Lesson.Adapter.ChildLessonAdapter;
import com.example.win7.apphoctiengnhat.Lesson.Model.ItemLesson;
import com.example.win7.apphoctiengnhat.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import io.supercharge.shimmerlayout.ShimmerLayout;

public class ChildLessonActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<ItemLesson> itemLessonList;
    private ChildLessonAdapter lessonAdapter;
    private DatabaseReference mDatabase;
    private ShimmerLayout shimmerLayoutChildLesson;
    private int numCurrentUnlockChildLesson;
    private String lessonName = "";

    // 1: Image
    // Status
    // 2: id -> dùng sắp xếp là chính
    // type
    // check để lấy đúng thông tin cho item lesson
    private int check = 1;
    private int type;
    private int idChildLesson;
    private String headerText;
    private String BottomText;
    private String imageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_lesson);

        initFireBase();
        getLessonName();
        getUserCurrentLevel();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                shimmerLayoutChildLesson.stopShimmerAnimation();
                shimmerLayoutChildLesson.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void getLessonName() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }

        lessonName = extras.getString("lessonName");
    }

    private void getUserCurrentLevel() {
        SharedPreferences sharedPreferences = getSharedPreferences(UserInfo.SHARED_REFERENCES_NAME, Context.MODE_PRIVATE);
        numCurrentUnlockChildLesson = sharedPreferences.getInt(UserInfo.KEY_UNLOCK_CHILD_LESSON,0);
    }

    private void initFireBase() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private void initView() {

        setUpToolbar();

        setupRecycleView();

        showSkeleton();

        itemLessonList = new ArrayList<>();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                initItem();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showSkeleton() {
        shimmerLayoutChildLesson = findViewById(R.id.shimmerLayoutChildLesson);
        shimmerLayoutChildLesson.setShimmerAngle(10);
        shimmerLayoutChildLesson.setShimmerColor(ResourcesCompat.getColor(getResources(),R.color.dark_transparent,null));
        shimmerLayoutChildLesson.startShimmerAnimation();
    }

    private void setupRecycleView() {
        recyclerView = findViewById(R.id.recyclerViewChildLesson);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initItem() {
        lessonAdapter = new ChildLessonAdapter(this,itemLessonList,lessonName);
        recyclerView.setAdapter(lessonAdapter);

        // We have: 0 = AVAILABLE type ; 1 = LOCK type
        getChildLesson();
    }

    private void getChildLesson() {
        mDatabase.child("Lesson").child("N5").child("SoCap2").child(lessonName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if(!dataSnapshot.getKey().equals("Info")){
                    getChildLessonInfo(dataSnapshot.getKey());
                    Log.e("name",dataSnapshot.getKey());
                    lessonAdapter.notifyDataSetChanged();
                }
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

    private void getChildLessonInfo(String ChildLessonName) {
        mDatabase.child("Lesson").child("N5").child("SoCap2").child(lessonName).child(ChildLessonName).child("Info").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if(check <= 2){
                    switch (check){
                        case 1:
                            imageName = dataSnapshot.getValue().toString();
                            break;
                        case 2:
                            idChildLesson = Integer.parseInt(dataSnapshot.getValue().toString());
                            break;
                    }
                    check++;
                }
                if(check == 3){

                    checkUnlockedLesson();

                    BottomText = ChildLessonName;

                    itemLessonList.add(new ItemLesson(type,headerText,BottomText,imageName,idChildLesson));

                    sortArrayListByID(itemLessonList);

                    check = 1;

                    lessonAdapter.notifyDataSetChanged();
                }
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

    private void sortArrayListByID(List<ItemLesson> itemLessonList) {
        Comparator<ItemLesson> cmp=new Comparator<ItemLesson>(){
            public int compare(ItemLesson a,ItemLesson b){
                if(a.getId()!=b.getId()){
                    return a.getId()-b.getId();
                }
                return Integer.compare(a.getId(), b.getId());
            }
        };

        itemLessonList.sort(cmp);
    }

    private void checkUnlockedLesson() {

        if( 0 < numCurrentUnlockChildLesson && idChildLesson <= numCurrentUnlockChildLesson ){
            // type 0: available
            type = 0;
            headerText = "Available";
        } else {
            // type 1: Lock
            type = 1;
            headerText = "Lock";
        }

    }

    @SuppressLint("SetTextI18n")
    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView txtToolbarTitle = findViewById(R.id.txtToolbarTitle);
        txtToolbarTitle.setText("Lesson");

        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/NABILA.TTF");
        txtToolbarTitle.setTypeface(typeface);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // dùng chung vs menu lesson ??
        getMenuInflater().inflate(R.menu.menu_lesson, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.lesson_action_legend:
                //showLegend();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
