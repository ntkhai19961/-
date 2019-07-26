package com.example.win7.apphoctiengnhat.App.Activity.Kanji;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.support.v7.widget.GridLayoutManager;
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
import android.widget.Toast;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;
import com.example.win7.apphoctiengnhat.App.Activity.Kanji.FabFragment.KanjiFabFragment;
import com.example.win7.apphoctiengnhat.App.Activity.Kanji.adapter.RecycleViewKanjiAdapter;
import com.example.win7.apphoctiengnhat.App.Activity.Kanji.model.ClassKanji;
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

public class KanjiActivity extends AppCompatActivity implements AAH_FabulousFragment.Callbacks, AAH_FabulousFragment.AnimationListener {

    private ArrayMap<String, List<String>> applied_filters = new ArrayMap<>();
    private FloatingActionButton fabKanji;
    private KanjiFabFragment dialogChooseLevel;
    private ShimmerLayout shimmer_layout_kanji;
    private ArrayList<ClassKanji> arrayKanji;
    private RecycleViewKanjiAdapter adapter;
    private DatabaseReference mDatabase;
    private List<String> degree;
    private List<String> level;
    private boolean checkNotChooseLevel = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kanji);

        init();

        getKanji("N5","1");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setVisibleFabtn();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void setVisibleFabtn() {

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fabKanji.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getKanji(String degree, String level) {
        mDatabase.child("Kanji").child(degree).child(level).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                ClassKanji classKanji = dataSnapshot.getValue(ClassKanji.class);
                arrayKanji.add(new ClassKanji(classKanji , degree));
                sortArrayListByID(arrayKanji);

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
                    Toast.makeText(KanjiActivity.this, "Error fragment kanji", Toast.LENGTH_SHORT).show();
            }
        });

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                shimmer_layout_kanji.stopShimmerAnimation();
                shimmer_layout_kanji.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void sortArrayListByID(ArrayList<ClassKanji> arrayKanji) {

        Comparator<ClassKanji> cmp=new Comparator<ClassKanji>(){
            public int compare(ClassKanji a,ClassKanji b){
                if(a.getId()!=b.getId()){
                    return a.getId()-b.getId();
                }
                return Integer.compare(a.getId(), b.getId());
            }
        };

        arrayKanji.sort(cmp);
    }

    private void init() {

        fabKanji = findViewById(R.id.fabKanji);
        dialogChooseLevel = KanjiFabFragment.newInstance();
        dialogChooseLevel.setParentFab(fabKanji);
        fabKanjiOnClick();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        arrayKanji = new ArrayList<>();
        setupRecycleView();

        showSkeleton();

        setupToolbar();
    }

    private void showSkeleton() {
        shimmer_layout_kanji = findViewById(R.id.shimmer_layout_kanji);
        shimmer_layout_kanji.setShimmerAngle(10);
        shimmer_layout_kanji.setShimmerAnimationDuration(2000);
        shimmer_layout_kanji.setShimmerColor(ResourcesCompat.getColor(getResources(),R.color.light_transparent,null));
        shimmer_layout_kanji.startShimmerAnimation();
    }

    private void setupRecycleView() {
        RecyclerView rcvKanji = findViewById(R.id.rcvKanji);
        rcvKanji.setHasFixedSize(true);
        rcvKanji.setLayoutManager(new GridLayoutManager(this,3));

        adapter = new RecycleViewKanjiAdapter(this,arrayKanji);
        rcvKanji.setAdapter(adapter);
    }

    @SuppressLint("SetTextI18n")
    private void setupToolbar() {
        Toolbar toolBar = findViewById(R.id.toolBar);

        setSupportActionBar(toolBar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView txtToolbarTitle = findViewById(R.id.txtToolbarTitle);
        txtToolbarTitle.setText("Kanji");

        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/NABILA.TTF");
        txtToolbarTitle.setTypeface(typeface);
    }

    private void fabKanjiOnClick() {

        fabKanji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!dialogChooseLevel.isAdded())
                    dialogChooseLevel.show(getSupportFragmentManager(), dialogChooseLevel.getTag());
            }
        });
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
                list.add("1");
                list.add("2");
                list.add("3");
                list.add("4");
                list.add("5");
                list.add("6");
                list.add("7");
                list.add("8");
                list.add("9");
                list.add("10");
                list.add("11");
                list.add("12");
                list.add("13");
                list.add("14");
                list.add("15");
                list.add("16");
                list.add("17");
                list.add("18");
                list.add("19");
                list.add("20");
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

                        arrayKanji.clear();

                        int degreeSize = degree.size();
                        int levelSize = level.size();

                        Log.e("degreeSize", "" + degreeSize);
                        Log.e("levelSize", "" + levelSize);

                        for(int i = 0 ; i < degreeSize ; i++)
                            for(int j = 0 ; j < levelSize ; j++)
                                getKanji(degree.get(i),level.get(j));

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
                    if(arrayKanji.size() == 0)
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
                        .on(this, fabKanji)
                        .autoHide(true, 2000)
                        .clickToHide(true)
                        .align(CENTER)
                        .position(ViewTooltip.Position.BOTTOM)
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
                        .on(this, fabKanji)
                        .autoHide(true, 1000)
                        .clickToHide(true)
                        .align(CENTER)
                        .position(ViewTooltip.Position.BOTTOM)
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
                        .on(this, fabKanji)
                        .autoHide(true, 1000)
                        .clickToHide(true)
                        .align(CENTER)
                        .position(ViewTooltip.Position.BOTTOM)
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
                        .on(this, fabKanji)
                        .autoHide(true, 1000)
                        .clickToHide(true)
                        .align(CENTER)
                        .position(ViewTooltip.Position.BOTTOM)
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

    private boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            return (mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting());
        } else
            return false;
    }
}
