package com.example.win7.apphoctiengnhat.App.Fragment.Recognition;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.win7.apphoctiengnhat.App.Fragment.Recognition.Adapter.CustomViewPagerFragmentRecognitionAdapter;
import com.example.win7.apphoctiengnhat.App.Fragment.Recognition.ObjectRecognition.Fragment_ObjectRecognition;
import com.example.win7.apphoctiengnhat.App.Fragment.Recognition.TextRecognition.NhanDienChuCai;
import com.example.win7.apphoctiengnhat.CurrentFragmentUseTranslate.CurrentFragmentUseTranslatePresentationImpl;
import com.example.win7.apphoctiengnhat.CustomView.CustomCircleView;
import com.example.win7.apphoctiengnhat.R;

import java.util.ArrayList;
import java.util.Random;

import devlight.io.library.ntb.NavigationTabBar;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by ntkhai1996 on 05-Jan-18.
 */

public class Fragment_Recognition extends Fragment implements ScreenShotable {

    final private byte SUM_PAGER_TABS = 2;
    ViewPager viewPager;
    NavigationTabBar navigationTabBar;
    ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
    String[] colors;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recognition, container, false);
        setHasOptionsMenu(true);

        colors = getResources().getStringArray(R.array.default_preview);

        initModels();
        addFragment();
        initUI(rootView);

        return rootView;
    }

    private void initUI(View rootView) {

        viewPager = rootView.findViewById(R.id.vp_horizontal_ntb);
        viewPager.setAdapter(new CustomViewPagerFragmentRecognitionAdapter(getFragmentManager(),getContext(),SUM_PAGER_TABS));

        navigationTabBar = rootView.findViewById(R.id.ntb_horizontal);
        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager);
        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(final NavigationTabBar.Model model, final int index) {

            }

            @Override
            public void onEndTabSelected(final NavigationTabBar.Model model, final int index) {
                model.hideBadge();

                // khởi tạo cho biết fragment nào sử dụng
                if(index == 0)
                    CurrentFragmentUseTranslatePresentationImpl.CURRENT_LANGUAGE_PAIR = 5;
                else if(index == 1)
                    CurrentFragmentUseTranslatePresentationImpl.CURRENT_LANGUAGE_PAIR = 2;
            }
        });

    }

    private void initModels(){
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_format_shapes_white_24dp),
                        Color.parseColor("#2c344b"))
                        .title("Text Recognition")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_photo_library_white_24dp),
                        Color.parseColor("#2c344b"))
                        .title("Object Recognition")
                        .build()
        );
    }

    private void addFragment(){

        if(CustomViewPagerFragmentRecognitionAdapter.fragments.size() < SUM_PAGER_TABS)
        {
            CustomViewPagerFragmentRecognitionAdapter.fragments.add(NhanDienChuCai.class.getName());
            CustomViewPagerFragmentRecognitionAdapter.fragments.add(Fragment_ObjectRecognition.class.getName());
        }

        // fragment này hiện lên đầu tiên nên nên gán biến này là 5 ở đây
        // chứ ko thì biến sẽ là 0 và translate ko dc
        CurrentFragmentUseTranslatePresentationImpl.CURRENT_LANGUAGE_PAIR = 5;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Recognition");
    }

    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }
}
