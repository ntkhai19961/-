package com.example.win7.apphoctiengnhat.LessonDetails.Fragment.Test.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.win7.apphoctiengnhat.LessonDetails.Fragment.Test.Fragment_LessonDetails_Test_Vocabulary_Item;

import java.util.ArrayList;

/**
 * Created by ntkhai1996 on 23-Apr-18.
 */

public class ViewPagerVocabularyTestAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment_LessonDetails_Test_Vocabulary_Item> arrayFragment = new ArrayList<>();

    public ViewPagerVocabularyTestAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return arrayFragment.get(position);
    }

    @Override
    public int getCount() {
        return arrayFragment.size();
    }

    public void addFragment(Fragment_LessonDetails_Test_Vocabulary_Item fragment){
        arrayFragment.add(fragment);
    }
}
