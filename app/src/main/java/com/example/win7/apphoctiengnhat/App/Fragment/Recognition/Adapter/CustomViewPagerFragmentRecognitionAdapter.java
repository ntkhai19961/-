package com.example.win7.apphoctiengnhat.App.Fragment.Recognition.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.win7.apphoctiengnhat.CurrentFragmentUseTranslate.CurrentFragmentUseTranslatePresentationImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ntkhai1996 on 05-Jan-18.
 */

public class CustomViewPagerFragmentRecognitionAdapter extends FragmentStatePagerAdapter {

    private List<String> fragmentsA;
    public static List<String> fragments = new ArrayList<>();
    Context context;
    int SUM_PAGER_TABS;

    public CustomViewPagerFragmentRecognitionAdapter(FragmentManager fm , Context context , int SUM_PAGER_TABS) {
        super(fm);
        this.context = context;
        this.SUM_PAGER_TABS = SUM_PAGER_TABS;
        fragmentsA = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return Fragment.instantiate(context, fragmentsA.get(position));
    }

    @Override
    public int getCount() {
        return SUM_PAGER_TABS;
    }
}
