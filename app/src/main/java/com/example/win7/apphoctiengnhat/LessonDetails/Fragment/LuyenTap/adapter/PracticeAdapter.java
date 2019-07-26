package com.example.win7.apphoctiengnhat.LessonDetails.Fragment.LuyenTap.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.win7.apphoctiengnhat.LessonDetails.Fragment.LuyenTap.FragmentPracticeItem;
import com.example.win7.apphoctiengnhat.LessonDetails.Fragment.LuyenTap.model.Practice;
import com.example.win7.apphoctiengnhat.R;

import java.util.ArrayList;

/**
 * Created by ntkhai1996 on 10-Apr-18.
 */

public class PracticeAdapter extends FragmentStatePagerAdapter {

    private ArrayList<FragmentPracticeItem> arrayList = new ArrayList<>();

    public PracticeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    public void addFragment(FragmentPracticeItem fragment){
        arrayList.add(fragment);
    }
}
