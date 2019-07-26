package com.example.win7.apphoctiengnhat.Lesson;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;
import com.example.win7.apphoctiengnhat.Constant.UserInfo;
import com.example.win7.apphoctiengnhat.Lesson.Adapter.LessonAdapter;
import com.example.win7.apphoctiengnhat.Lesson.FabFragment.LessonFabFragment;
import com.example.win7.apphoctiengnhat.Lesson.Model.ItemLesson;
import com.example.win7.apphoctiengnhat.R;
import com.github.florent37.viewtooltip.ViewTooltip;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.supercharge.shimmerlayout.ShimmerLayout;

import static com.github.florent37.viewtooltip.ViewTooltip.ALIGN.CENTER;

public class LessonActivity extends AppCompatActivity implements AAH_FabulousFragment.Callbacks, AAH_FabulousFragment.AnimationListener{

    private FloatingActionButton fabLesson;
    private RecyclerView recyclerView;
    private List<ItemLesson> itemLessonList;
    private LessonAdapter lessonAdapter;
    private DatabaseReference mDatabase;
    private ShimmerLayout shimmer_layout_lesson;
    private int numCurrentUnlockLesson;
    private LessonFabFragment dialogChooseLevel;
    private List<String> degree;
    private List<String> level;
    private boolean checkNotChooseLevel = true;
    private ArrayMap<String, List<String>> applied_filters = new ArrayMap<>();

    // 1: Image
    // Status
    // 2: id -> dùng sắp xếp là chính
    // type
    // check để lấy đúng thông tin cho item lesson
    private int check = 1;
    private int type;
    private int idLesson;
    private String headerText;
    private String BottomText;
    private String imageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        initFireBase();

        getUserCurrentLevel();

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setVisibleFabtn();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void setVisibleFabtn() {

        // set visible after done with downloading data from first time
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fabLesson.setVisibility(View.VISIBLE);
                shimmer_layout_lesson.stopShimmerAnimation();
                shimmer_layout_lesson.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void getLessonInfo(String lessonName, String degree, String level) {

        mDatabase.child("Lesson").child(degree).child(level).child(lessonName).child("Info").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.e("dataSnapshot.getKey()",dataSnapshot.getKey());

                if(check <= 2){
                    switch (check){
                        case 1:
                            imageName = Objects.requireNonNull(dataSnapshot.getValue()).toString();
                            break;
                        case 2:
                            idLesson = Integer.parseInt(Objects.requireNonNull(dataSnapshot.getValue()).toString());
                            break;
                    }
                    check++;
                }
                if(check == 3){

                    checkUnlockedLesson();
                    BottomText = lessonName;

                    itemLessonList.add(new ItemLesson(type,headerText,BottomText,imageName,idLesson));

                    sortArrayListByID(itemLessonList);

                    check = 1;

                    try{
                        lessonAdapter.notifyDataSetChanged();
                        Log.e("a",itemLessonList.size()+"");
                    }
                    catch (Exception ex) {
                        Log.e("Catch()",ex.toString());
                    }
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

    private void checkUnlockedLesson() {

        if( 0 < numCurrentUnlockLesson && idLesson <= numCurrentUnlockLesson){
            headerText = "Available";
            type = 0;
        } else {
            headerText = "Lock";
            type = 1;
        }
    }

    private void getLesson(String degree, String level) {
        mDatabase.child("Lesson").child(degree).child(level).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                getLessonInfo(dataSnapshot.getKey(), degree, level);
                Log.e("dataSnapshot.getKey()",dataSnapshot.getKey());
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

    private void initView() {

        setUpToolbar();

        fabLesson = findViewById(R.id.fabLesson);
        dialogChooseLevel = LessonFabFragment.newInstance();
        dialogChooseLevel.setParentFab(fabLesson);
        fabLessonOnClick();

        recyclerView  = findViewById(R.id.recyclerViewLesson);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        showSkeleton();

        itemLessonList = new ArrayList<>();
        degree         = new ArrayList<>();
        level          = new ArrayList<>();

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
        shimmer_layout_lesson = findViewById(R.id.shimmer_layout_lesson);
        shimmer_layout_lesson.setShimmerAngle(10);
        shimmer_layout_lesson.setShimmerColor(ResourcesCompat.getColor(getResources(),R.color.dark_transparent,null));
        shimmer_layout_lesson.startShimmerAnimation();
    }

    private void fabLessonOnClick() {
        fabLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!dialogChooseLevel.isAdded())
                    dialogChooseLevel.show(getSupportFragmentManager(), dialogChooseLevel.getTag());
            }
        });
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

    private void initItem() {
        lessonAdapter = new LessonAdapter(this,itemLessonList);
        recyclerView.setAdapter(lessonAdapter);

        // We have: 0 = AVAILABLE type ; 1 = LOCK type
        getLesson("N5","SoCap2");
    }

    private void getUserCurrentLevel() {

        SharedPreferences sharedPreferences = getSharedPreferences(UserInfo.SHARED_REFERENCES_NAME, Context.MODE_PRIVATE);
        numCurrentUnlockLesson = sharedPreferences.getInt(UserInfo.KEY_UNLOCK_LESSON,0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

    private void sortArrayListByID(List<ItemLesson> itemLessonList) {

        Comparator<ItemLesson> cmp = new Comparator<ItemLesson>(){
            public int compare(ItemLesson a, ItemLesson b){
                if(a.getId() != b.getId()){
                    return a.getId() - b.getId();
                }
                return Integer.compare(a.getId(), b.getId());
            }
        };
        itemLessonList.sort(cmp);
    }

    public ArrayMap<String, List<String>> getApplied_filters() {
        return applied_filters;
    }

    public ArrayList<String> getListDegreeOrLevel(String category){

        ArrayList<String> list = new ArrayList<>();

        switch (category){
            case "degree":
                list.add("N5");
                list.add("N4");
                list.add("N3");
                list.add("N2");
                list.add("N1");
                break;
            case "level":
                list.add("Sơ cấp 5");
                list.add("Sơ cấp 4");
                list.add("Sơ cấp 3");
                list.add("Sơ cấp 2");
                list.add("Sơ cấp 1");
                break;
        }
        return list;
    }

    @Override
    public void onOpenAnimationStart() {

    }

    @Override
    public void onOpenAnimationEnd() {

    }

    @Override
    public void onCloseAnimationStart() {

    }

    @Override
    public void onCloseAnimationEnd() {
    }

    @Override
    public void onResult(Object result) {
        if (result.toString().equalsIgnoreCase("swiped_down")) {
            //do something or nothing
        } else {
            if (result != null){
                ArrayMap<String, List<String>> applied_filters = (ArrayMap<String, List<String>>) result;
                if (applied_filters.size() != 0){

                    String DegreeKey = "";
                    String LevelKey = "";

                    for (Map.Entry<String, List<String>> entry : applied_filters.entrySet()){
                        Log.e("k9res", "entry.key: " + entry.getKey());
                        Log.e("k9res", "entry.value: " + entry.getValue());
                        switch (entry.getKey()) {
                            case "degree":
                                DegreeKey = entry.getKey();
                                degree = entry.getValue();
                                break;
                            case "level":
                                LevelKey = entry.getKey();
                                level = entry.getValue();
                                break;
                        }
                    }

                    if(LevelKey.equals("") ){
                        showTooltip("LevelEmpty");
                        checkNotChooseLevel = true;
                    }
                    else if(DegreeKey.equals("")){
                        showTooltip("DegreeEmpty");
                        checkNotChooseLevel = true;
                    }
                    // ko internet => lỗi adapter notify data change vì itemlist đã clear
                    else if(isConnected(this)){

                        checkNotChooseLevel = false;

                        itemLessonList.clear();

                        int degreeSize = degree.size();
                        int levelSize = level.size();

                        Log.e("degreeSize", "" + degreeSize);
                        Log.e("levelSize", "" + levelSize);

                        for(int i = 0 ; i < degreeSize ; i++)
                            for(int j = 0 ; j < levelSize ; j++)
                                getLesson(degree.get(i),getLevel(level.get(j)));

                        checkEmptyList();
                    } else
                        showTooltip("NoInternet");
                }
            }
        }
    }

    private void checkEmptyList() {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!checkNotChooseLevel)
                    if(itemLessonList.size() == 0)
                        showTooltip("ItemListEmpty");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showTooltip(String error) {

        switch (error){
            case "ItemListEmpty":
                ViewTooltip
                        .on(this, fabLesson)
                        .autoHide(true, 2000)
                        .clickToHide(true)
                        .align(CENTER)
                        .position(ViewTooltip.Position.TOP)
                        .text("We haven't update these level yet. Please wait for next update!!")
                        .textSize(2,20)
                        .textColor(ResourcesCompat.getColor(this.getResources(),R.color.colorWhite,null))
                        .color(ResourcesCompat.getColor(this.getResources(),R.color.colorPrimaryDark,null))
                        .corner(10)
                        .arrowWidth(15)
                        .arrowHeight(15)
                        .distanceWithView(0)
                        .animation(new ViewTooltip.FadeTooltipAnimation(500))
                        .show();
                break;
            case "LevelEmpty":
                ViewTooltip
                        .on(this, fabLesson)
                        .autoHide(true, 1000)
                        .clickToHide(true)
                        .align(CENTER)
                        .position(ViewTooltip.Position.TOP)
                        .text("Please choose the level!!")
                        .textSize(2,20)
                        .textColor(ResourcesCompat.getColor(this.getResources(),R.color.colorWhite,null))
                        .color(ResourcesCompat.getColor(this.getResources(),R.color.colorPrimaryDark,null))
                        .corner(10)
                        .arrowWidth(15)
                        .arrowHeight(15)
                        .distanceWithView(0)
                        .animation(new ViewTooltip.FadeTooltipAnimation(500))
                        .show();
                break;
            case "DegreeEmpty":
                ViewTooltip
                        .on(this, fabLesson)
                        .autoHide(true, 1000)
                        .clickToHide(true)
                        .align(CENTER)
                        .position(ViewTooltip.Position.TOP)
                        .text("Please choose the degree!!")
                        .textSize(2,20)
                        .textColor(ResourcesCompat.getColor(this.getResources(),R.color.colorWhite,null))
                        .color(ResourcesCompat.getColor(this.getResources(),R.color.colorPrimaryDark,null))
                        .corner(10)
                        .arrowWidth(15)
                        .arrowHeight(15)
                        .distanceWithView(0)
                        .animation(new ViewTooltip.FadeTooltipAnimation(500))
                        .show();
                break;
            case "NoInternet":
                ViewTooltip
                        .on(this, fabLesson)
                        .autoHide(true, 1000)
                        .clickToHide(true)
                        .align(CENTER)
                        .position(ViewTooltip.Position.TOP)
                        .text("Please check your internet")
                        .textSize(2,20)
                        .textColor(ResourcesCompat.getColor(this.getResources(),R.color.colorWhite,null))
                        .color(ResourcesCompat.getColor(this.getResources(),R.color.colorPrimaryDark,null))
                        .corner(10)
                        .arrowWidth(15)
                        .arrowHeight(15)
                        .distanceWithView(0)
                        .animation(new ViewTooltip.FadeTooltipAnimation(500))
                        .show();
                break;
        }
    }

    private String getLevel(String level){

        switch (level){
            case "Sơ cấp 5":
                return "SoCap5";
            case "Sơ cấp 4":
                return "SoCap4";
            case "Sơ cấp 3":
                return "SoCap3";
            case "Sơ cấp 2":
                return "SoCap2";
            case "Sơ cấp 1":
                return "SoCap1";
            default:
                return "";
        }
    }

    private boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo    = Objects.requireNonNull(cm).getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            return (mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting());
        } else
            return false;
    }
}
