package com.example.win7.apphoctiengnhat.LessonDetails.Fragment.NguPhap.adapter;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.win7.apphoctiengnhat.LessonDetails.Fragment.NguPhap.FragmentGrammarItem;
import com.example.win7.apphoctiengnhat.LessonDetails.Fragment.NguPhap.model.Grammar;
import com.example.win7.apphoctiengnhat.R;
import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListener;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ntkhai1996 on 09-Apr-18.
 */

public class GrammarAdapter extends FragmentStatePagerAdapter {

    private ArrayList<FragmentGrammarItem> arrayList = new ArrayList<>();

    public GrammarAdapter(FragmentManager fm) {
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

    public void addFragment(FragmentGrammarItem fragmentGrammarItem){
        arrayList.add(fragmentGrammarItem);
    }
}
