package com.example.win7.apphoctiengnhat.Translate.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.win7.apphoctiengnhat.R;
import com.example.win7.apphoctiengnhat.Translate.ui.fragment.about.AboutFragment;
import com.example.win7.apphoctiengnhat.Translate.ui.fragment.favorite.FavoriteFragment;
import com.example.win7.apphoctiengnhat.Translate.ui.fragment.history.HistoryFragment;
import com.example.win7.apphoctiengnhat.Translate.ui.fragment.translate.TranslateFragment;
import com.example.win7.apphoctiengnhat.Translate.util.DrawerFragmentEnum;
import com.example.win7.apphoctiengnhat.Translate.util.DrawerItemClickListener;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ntkhai1996 on 02-Nov-17.
 */

public class BaseActivity extends AppCompatActivity implements DrawerItemClickListener.OnDrawerItemClickListener {

    public static final String TAG = BaseActivity.class.getSimpleName();

    //ActionBar and Drawer
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.drawer_list_view)
    ListView mDrawerListView;
    @BindArray(R.array.drawer_items)
    String[] mDrawerStrings;

    private ActionBarDrawerToggle mDrawerToggle;
    private String mTitle;
    private String mDrawerTitle;
    private DrawerFragmentEnum currentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!isConnected(BaseActivity.this))
        {
            buildDialog(BaseActivity.this).show();
        }
        else
        {
            setContentView(R.layout.activity_main_yandex_api_translate);

            ButterKnife.bind(this);

            initDrawer();

            //setup icons for toggle
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            mDrawerToggle.syncState();

            setFragment(DrawerFragmentEnum.TRANSLATION.getDeclaredPosition());
            currentFragment = DrawerFragmentEnum.TRANSLATION;
        }

    }

    private void initDrawer() {
        mDrawerListView.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item_yandex_api_translate, mDrawerStrings));
        //set first item checked
        setDrawerActivePosition(0);

        mDrawerListView.setOnItemClickListener(new DrawerItemClickListener(this));

        mDrawerTitle = getResources().getString(R.string.open_drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
                closeKeyboard();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
                closeKeyboard();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void closeKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onDrawerItemClick(int position) {
        mDrawerLayout.closeDrawer(mDrawerListView);

        if (currentFragment.getDeclaredPosition() == position) {
            return;
        }

        Log.d(TAG, "onDrawerClick. drawerPosition: " + position);

        setFragment(position);
        setDrawerActivePosition(position);
    }

    void setFragment(int drawerPosition) {
//        DrawerFragmentEnum enumElement = DrawerFragmentEnum.values()[drawerPosition];
        currentFragment = DrawerFragmentEnum.values()[drawerPosition];
        Fragment fragment = null;

        switch (currentFragment) {
            case TRANSLATION: {
                fragment = getTranslateFragment();
                break;
            }
            case HISTORY: {
                fragment = getHistoryFragment();
                break;
            }
            case FAVOURITES: {
                fragment = getFavoriteFragment();
                break;
            }
            case ABOUT: {
                fragment = getAboutFragment();
                break;
            }
            case GOBACK:
                finish();
            default:
                return;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.activity_main_frame_layout, fragment)
                .commit();
    }

    private void setDrawerActivePosition(int position) {
        setTitle(mDrawerStrings[position]);
        mDrawerListView.setItemChecked(position, true);
    }

    private void setTitle(String title) {
        mTitle = title;
        getSupportActionBar().setTitle(title);
    }

    private Fragment getTranslateFragment() {
        return TranslateFragment.newInstance(null);
    }

    private Fragment getHistoryFragment() {
        return HistoryFragment.newInstance(null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public Fragment getFavoriteFragment() {
        return FavoriteFragment.newInstance(null);
    }

    public Fragment getAboutFragment() {
        return AboutFragment.newInstance(null);
    }


    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else
            return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;
    }
}
