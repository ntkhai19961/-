package com.example.win7.apphoctiengnhat.SplashScreen;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.win7.apphoctiengnhat.App.Activity.Main.MainActivity;
import com.example.win7.apphoctiengnhat.Login.LoginActivity;
import com.example.win7.apphoctiengnhat.R;
import com.google.firebase.auth.*;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        initView();
    }

    private void checkAlreadyLogin() {
        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void initView() {

        TextView txtWelcome_SplashScreen = findViewById(R.id.txtWelcome_SplashScreen);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/NABILA.TTF");
        txtWelcome_SplashScreen.setTypeface(typeface);

        LinearLayout lltSplashScreenAbove = findViewById(R.id.lltSplashScreenAbove);
        LinearLayout lltSplashScreenBelow = findViewById(R.id.lltSplashScreenBelow);
        Animation uptodown = AnimationUtils.loadAnimation(this, R.anim.splash_screen_anim_slide_down);
        Animation downtoup = AnimationUtils.loadAnimation(this, R.anim.splash_screen_anim_slide_up);
        lltSplashScreenAbove.setAnimation(uptodown);
        lltSplashScreenBelow.setAnimation(downtoup);

        uptodown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                checkAlreadyLogin();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}

