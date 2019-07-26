package com.example.win7.apphoctiengnhat.App.Activity.Kanji.FabFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;
import com.example.win7.apphoctiengnhat.App.Activity.Kanji.KanjiActivity;
import com.example.win7.apphoctiengnhat.R;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ntkhai1996 on 01-May-18.
 */

public class KanjiFabFragment extends AAH_FabulousFragment {

    private ArrayMap<String, List<String>> applied_filters = new ArrayMap<>();
    private List<TextView> textviews = new ArrayList<>();
    private TabLayout tabs_types;
    private ImageButton imgBtnRefresh, imgBtnApply;
    private SectionsPagerAdapter mAdapter;
    private RelativeLayout rl_content;
    private LinearLayout ll_buttons;
    private ViewPager vp_types;

    public static KanjiFabFragment newInstance() {
        return new KanjiFabFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getApplied_filter();
    }

    private void getApplied_filter() {
        applied_filters = ((KanjiActivity) getActivity()).getApplied_filters();
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {

        View contentView = View.inflate(getContext(), R.layout.fragment_kanji_filter_view, null);

        init(contentView);

        imgBtnApplyOnClick();

        imgBtnRefreshOnClick();

        //params to set
        setAnimationDuration(600); //optional; default 500ms
        setPeekHeight(300); // optional; default 400dp
        setCallbacks((Callbacks) getActivity()); //optional; to get back result
        setAnimationListener((AnimationListener) getActivity()); //optional; to get animation callbacks
        setViewgroupStatic(ll_buttons); // optional; layout to stick at bottom on slide
        setViewPager(vp_types); //optional; if you use viewpager that has scrollview
        setViewMain(rl_content); //necessary; main bottomsheet view
        setMainContentView(contentView); // necessary; call at end before super
        super.setupDialog(dialog, style); //call super at last
    }

    private void init(View contentView) {
        rl_content    = contentView.findViewById(R.id.rl_content);
        ll_buttons    = contentView.findViewById(R.id.ll_buttons);
        imgBtnRefresh = contentView.findViewById(R.id.imgbtn_refresh);
        imgBtnApply   = contentView.findViewById(R.id.imgbtn_apply);
        vp_types      = contentView.findViewById(R.id.vp_types);
        tabs_types    = contentView.findViewById(R.id.tabs_types);

        mAdapter = new SectionsPagerAdapter();
        vp_types.setOffscreenPageLimit(2);
        vp_types.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        tabs_types.setupWithViewPager(vp_types);
    }

    private void imgBtnApplyOnClick() {
        imgBtnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFilter(applied_filters);
            }
        });
    }

    private void imgBtnRefreshOnClick() {
        imgBtnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (TextView tv : textviews) {
                    tv.setTag("unselected");
                    tv.setBackgroundResource(R.drawable.item_chip_unselected);
                    tv.setTextColor(ContextCompat.getColor(getContext(), R.color.filters_chips));
                }
                applied_filters.clear();
            }
        });
    }

    public class SectionsPagerAdapter extends PagerAdapter{

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            LayoutInflater inflater = LayoutInflater.from(getContext());

            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.view_filters_sorters, container, false);

            FlexboxLayout flexBoxLayout = layout.findViewById(R.id.flexBoxLayout);

            switch (position) {
                case 0:
                    inflateLayoutWithFilters("degree", flexBoxLayout);
                    break;
                case 1:
                    inflateLayoutWithFilters("level", flexBoxLayout);
                    break;
            }
            container.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object view) {
            container.removeView((View) view);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "DEGREE";
                case 1:
                    return "LEVEL";
            }
            return "";
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }

    private void inflateLayoutWithFilters(String filter_category, FlexboxLayout flexBoxLayout) {

        List<String> keys = new ArrayList<>();
        switch (filter_category) {
            case "degree":
                keys = ((KanjiActivity) getActivity()).getListDegreeOrLevel(filter_category);
                break;
            case "level":
                keys = ((KanjiActivity) getActivity()).getListDegreeOrLevel(filter_category);
                break;
        }

        for (int i = 0; i < keys.size(); i++) {
            View subchild = getActivity().getLayoutInflater().inflate(R.layout.item_single_chip, null);
            final TextView tv = subchild.findViewById(R.id.txt_title);
            tv.setText(keys.get(i));
            final int finalI = i;
            final List<String> finalKeys = keys;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tv.getTag() != null && tv.getTag().equals("selected")) {
                        tv.setTag("unselected");
                        tv.setBackgroundResource(R.drawable.item_chip_unselected);
                        tv.setTextColor(ContextCompat.getColor(getContext(), R.color.filters_chips));
                        removeFromSelectedMap(filter_category, finalKeys.get(finalI));
                    } else {
                        tv.setTag("selected");
                        tv.setBackgroundResource(R.drawable.item_chip_selected);
                        tv.setTextColor(ContextCompat.getColor(getContext(), R.color.filters_header));
                        addToSelectedMap(filter_category, finalKeys.get(finalI));
                    }
                }
            });

            if (applied_filters != null && applied_filters.get(filter_category) != null && applied_filters.get(filter_category).contains(keys.get(finalI))) {
                tv.setTag("selected");
                tv.setBackgroundResource(R.drawable.item_chip_selected);
                tv.setTextColor(ContextCompat.getColor(getContext(), R.color.filters_header));
            } else {
                tv.setBackgroundResource(R.drawable.item_chip_unselected);
                tv.setTextColor(ContextCompat.getColor(getContext(), R.color.filters_chips));
            }
            textviews.add(tv);

            flexBoxLayout.addView(subchild);
        }
    }

    private void addToSelectedMap(String key, String value) {
        if (applied_filters.get(key) != null && !applied_filters.get(key).contains(value)) {
            applied_filters.get(key).add(value);
        } else {
            List<String> temp = new ArrayList<>();
            temp.add(value);
            applied_filters.put(key, temp);
        }
    }

    private void removeFromSelectedMap(String key, String value) {
        if (applied_filters.get(key).size() == 1) {
            applied_filters.remove(key);
        } else {
            applied_filters.get(key).remove(value);
        }
    }
}
