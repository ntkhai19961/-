package com.example.win7.apphoctiengnhat.App.Fragment.OnlineDictionary;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.win7.apphoctiengnhat.R;
import com.example.win7.apphoctiengnhat.Translate.ui.fragment.favorite.FavoriteFragment;
import com.example.win7.apphoctiengnhat.Translate.ui.fragment.history.HistoryFragment;
import com.example.win7.apphoctiengnhat.Translate.ui.fragment.translate.TranslateFragment;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.OnBoomListener;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.nightonke.boommenu.Util;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Objects;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by ntkhai1996 on 08-Jan-18.
 */

public class Fragment_OnlineDictionary extends Fragment implements ScreenShotable {

    private AVLoadingIndicatorView AVLoadingIndicatorView_GoogleTranslate;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_online_dictionary, container, false);

        initView(rootView);

        return rootView;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView(View rootView) {

        AVLoadingIndicatorView_GoogleTranslate = rootView.findViewById(R.id.AVLoadingIndicatorView_GoogleTranslate);

        WebView webViewGoogleTranslate = rootView.findViewById(R.id.webViewGoogleTranslate);

        webViewGoogleTranslate.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                AVLoadingIndicatorView_GoogleTranslate.setVisibility(View.GONE);
            }
        });
        webViewGoogleTranslate.loadUrl("https://translate.google.com/m/translate");

        WebSettings webSettings = webViewGoogleTranslate.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }
}
