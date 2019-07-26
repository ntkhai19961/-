package com.example.win7.apphoctiengnhat.Translate.ui.fragment.about;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.win7.apphoctiengnhat.R;
import com.example.win7.apphoctiengnhat.Translate.ui.fragment.BaseFragment;

public class AboutFragment extends BaseFragment {

    public static Fragment newInstance(Bundle args) {
        AboutFragment fragment = new AboutFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about_yandex_api_translate, container, false);
    }
}
