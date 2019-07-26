package com.example.win7.apphoctiengnhat.App.Fragment.Setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.win7.apphoctiengnhat.Constant.UserInfo;
import com.example.win7.apphoctiengnhat.R;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by ntkhai1996 on 05-Jan-18.
 */

public class FragmentSetting extends Fragment implements ScreenShotable {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }
}
