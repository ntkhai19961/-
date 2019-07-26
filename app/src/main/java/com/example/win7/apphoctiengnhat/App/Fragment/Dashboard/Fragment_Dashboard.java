package com.example.win7.apphoctiengnhat.App.Fragment.Dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.win7.apphoctiengnhat.App.Activity.Kanji.KanjiActivity;
import com.example.win7.apphoctiengnhat.Constant.UserInfo;
import com.example.win7.apphoctiengnhat.Lesson.LessonActivity;
import com.example.win7.apphoctiengnhat.R;
import com.google.android.gms.common.images.ImageManager;

import java.util.Objects;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by ntkhai1996 on 18-Nov-17.
 */

public class Fragment_Dashboard extends Fragment implements View.OnClickListener, ScreenShotable {

    private RelativeLayout rltLesson;
    private RelativeLayout rltKanji;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // just use this to check, nothing more
        SharedPreferences sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences(UserInfo.SHARED_REFERENCES_NAME, Context.MODE_PRIVATE);

        Log.e("KEY_EMAIL _update",sharedPreferences.getString(UserInfo.KEY_EMAIL,""));
        Log.e("KEY_KANJI_LEVEL_update",sharedPreferences.getString(UserInfo.KEY_KANJI_LEVEL,""));

        Log.e("KEY_LESSON_LEVEL_update",sharedPreferences.getString(UserInfo.KEY_LESSON_LEVEL,""));
        Log.e("KEY_UNLOCK_KANJI_update",sharedPreferences.getInt(UserInfo.KEY_UNLOCK_KANJI,0) + "");

        Log.e("KEY_UNLOCK_LESSON_update",sharedPreferences.getInt(UserInfo.KEY_UNLOCK_LESSON,0) + "");
        Log.e("KEY_UNLOCK_CHILD_LESSON_update",sharedPreferences.getInt(UserInfo.KEY_UNLOCK_CHILD_LESSON,0) + "");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        initView(rootView);

        return rootView;
    }

    private void initView(View rootView) {

        rltLesson = rootView.findViewById(R.id.rltLesson);
        rltLesson.setOnClickListener(this);
        setOnTouchRltLesson();

        rltKanji = rootView.findViewById(R.id.rltKanji);
        rltKanji.setOnClickListener(this);
        setOnTouchRltKanji();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setOnTouchRltKanji() {
        View.OnTouchListener onTouch = new View.OnTouchListener() {
            @SuppressLint({"ClickableViewAccessibility"})
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        rltKanji.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.colorPrimaryBackgroundOnTouch,null));
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        rltKanji.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.colorPrimaryBackgroundOnTouch,null));
                        break;
                    case MotionEvent.ACTION_UP:
                        rltKanji.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.colorPrimaryBackground,null));
                        startActivity(new Intent(view.getContext(), KanjiActivity.class));
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        rltKanji.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.colorPrimaryBackground,null));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        rltKanji.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.colorPrimaryBackground,null));
                        break;
                }
                return true;
            }
        };

        rltKanji.setOnTouchListener(onTouch);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setOnTouchRltLesson() {
        View.OnTouchListener onTouch = new View.OnTouchListener() {
            @SuppressLint({"ClickableViewAccessibility"})
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        rltLesson.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.colorPrimaryBackgroundOnTouch,null));
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        rltLesson.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.colorPrimaryBackgroundOnTouch,null));
                        break;
                    case MotionEvent.ACTION_UP:
                        rltLesson.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.colorPrimaryBackground,null));
                        startActivity(new Intent(view.getContext(), LessonActivity.class));
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        rltLesson.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.colorPrimaryBackground,null));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        rltLesson.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.colorPrimaryBackground,null));
                        break;
                }
                return true;
            }
        };

        rltLesson.setOnTouchListener(onTouch);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.rltLesson:
                startActivity(new Intent(view.getContext(), LessonActivity.class));
                break;
            case R.id.rltKanji:
                startActivity(new Intent(view.getContext(), KanjiActivity.class));
                break;
        }
    }

    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }


}
