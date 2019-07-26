package com.example.win7.apphoctiengnhat.App.Activity.Main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.win7.apphoctiengnhat.App.Fragment.Setting.FragmentSetting;
import com.example.win7.apphoctiengnhat.App.Fragment.Dashboard.Fragment_Dashboard;
import com.example.win7.apphoctiengnhat.App.Fragment.KizunaAi.Fragment_KizunaAi;
import com.example.win7.apphoctiengnhat.App.Fragment.OnlineDictionary.Fragment_OnlineDictionary;
import com.example.win7.apphoctiengnhat.App.Fragment.Recognition.Fragment_Recognition;
import com.example.win7.apphoctiengnhat.App.Fragment.Communication.FragmentCoBan;
import com.example.win7.apphoctiengnhat.Dialog.NoConnection.DialogNoConnection;
import com.example.win7.apphoctiengnhat.Login.LoginActivity;
import com.example.win7.apphoctiengnhat.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;

public class MainActivity extends AppCompatActivity implements ViewAnimator.ViewAnimatorListener {

    private DrawerLayout drawer;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> listMenuItemDrawer = new ArrayList<>();
    private ViewAnimator viewAnimator;
    private LinearLayout linearLayoutLeftDrawer;
    private TextView txtToolbarTitle;

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initDrawerNavigation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        txtToolbarTitle = findViewById(R.id.txtToolbarTitle);
        txtToolbarTitle.setText("Home");

        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/NABILA.TTF");
        txtToolbarTitle.setTypeface(typeface);
    }

    private void initDrawerNavigation(){
        drawer = findViewById(R.id.drawer_layout);

        // init fragment dashboard when first time
        Fragment_Dashboard fragmentDashboard = new Fragment_Dashboard();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragmentDashboard).commit();

        drawer.setScrimColor(Color.TRANSPARENT);
        linearLayoutLeftDrawer = findViewById(R.id.left_drawer);
        linearLayoutLeftDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
            }
        });

        setActionBar();
        createMenuList();
        viewAnimator = new ViewAnimator<>(this, listMenuItemDrawer, fragmentDashboard, drawer, this);
    }

    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem("Close", R.drawable.icn_close);
        listMenuItemDrawer.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem("Home", R.drawable.ic_dashboard_white_24dp);
        listMenuItemDrawer.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem("Recognition", R.drawable.ic_menu_camera);
        listMenuItemDrawer.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem("Online Dictionary", R.drawable.ic_translate_white_24dp);
        listMenuItemDrawer.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem("Communication", R.drawable.ic_question_answer_white_24dp);
        listMenuItemDrawer.add(menuItem4);
        SlideMenuItem menuItem5 = new SlideMenuItem("Kizuna Ai", R.drawable.ic_keyboard_voice_white_24dp);
        listMenuItemDrawer.add(menuItem5);
        SlideMenuItem menuItem6 = new SlideMenuItem("Settings", R.drawable.ic_settings_white_24dp);
        listMenuItemDrawer.add(menuItem6);
        SlideMenuItem menuItem7 = new SlideMenuItem("Sign Out", R.drawable.ic_log_out_white);
        listMenuItemDrawer.add(menuItem7);
    }

    private void setActionBar() {
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //toolbar.setTitleTextColor(Color.WHITE);

        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawer,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayoutLeftDrawer.removeAllViews();
                linearLayoutLeftDrawer.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayoutLeftDrawer.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(drawerToggle);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @SuppressLint("SetTextI18n")
    private ScreenShotable replaceFragment(String itemName) {
        if(itemName.equals("Home"))
        {
            txtToolbarTitle.setText("Home");

            Fragment_Dashboard fragment_dashboard = new Fragment_Dashboard();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment_dashboard).commit();
            return fragment_dashboard;
        }
        else if(itemName.equals("Recognition"))
        {
            if(isConnected(this)) {
                txtToolbarTitle.setText("Recognition");

                Fragment_Recognition fragmentRecognition = new Fragment_Recognition();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragmentRecognition).commit();
                return fragmentRecognition;
            }
            else
                showDialog();
        }
        else if(itemName.equals("Online Dictionary"))
        {
            if(isConnected(this)) {
                txtToolbarTitle.setText("Online Dictionary");

                Fragment_OnlineDictionary fragmentOnlineDictionary = new Fragment_OnlineDictionary();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragmentOnlineDictionary).commit();
                return fragmentOnlineDictionary;
            }
            else
                showDialog();
        }
        else if(itemName.equals("Communication")) {
            txtToolbarTitle.setText("Communication");

            FragmentCoBan fragmentCoBan = new FragmentCoBan();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragmentCoBan).commit();
            return fragmentCoBan;
        }
        else if(itemName.equals("Kizuna Ai"))
        {
            if(isConnected(this)) {
                txtToolbarTitle.setText("Kizuna Ai");

                Fragment_KizunaAi fragmentKizunaAi = new Fragment_KizunaAi();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragmentKizunaAi).commit();
                return fragmentKizunaAi;
            }
            else
                showDialog();
        }
        else if(itemName.equals("Settings")) {
            txtToolbarTitle.setText("Settings");

            FragmentSetting fragmentSetting = new FragmentSetting();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragmentSetting).commit();
            return fragmentSetting;
        }
        else if(itemName.equals("Sign Out")) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }

        Fragment_Dashboard fragment_dashboard = new Fragment_Dashboard();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment_dashboard).commit();
        return fragment_dashboard;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = null;
        if (cm != null) {
            netinfo = cm.getActiveNetworkInfo();
        }

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            return (mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting());
        } else
            return false;
    }

    public void showDialog() {
        DialogNoConnection dialog = new DialogNoConnection();
        dialog.show(getSupportFragmentManager(),"NoConnection");
    }

    //-------------------------------------------------------------------------------
    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()) {
            case "Close":
                return screenShotable;
            default:
                return replaceFragment(slideMenuItem.getName());
        }
    }

    @Override
    public void disableHomeButton() {
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(false);
    }

    @Override
    public void enableHomeButton() {
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        drawer.closeDrawers();
    }

    @Override
    public void addViewToContainer(View view) {
        linearLayoutLeftDrawer.addView(view);
    }
}
