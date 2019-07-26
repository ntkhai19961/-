package com.example.win7.apphoctiengnhat.Login;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.win7.apphoctiengnhat.Login.SignIn.SignInActivity;
import com.example.win7.apphoctiengnhat.Login.SignUp.SignUpActivity;
import com.example.win7.apphoctiengnhat.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @SuppressLint("StaticFieldLeak")
    public static Activity LoginActivity;

    private Button btnSignUp,btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginActivity = this;

        initView();
    }

    private void initView() {

        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/NABILA.TTF");

        btnSignIn = findViewById(R.id.btnSignIn_LoginActitivy);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setTypeface(typeface);
        btnSignIn.setTypeface(typeface);

        TextView txtSlogan     = findViewById(R.id.txtSlogan);
        TextView txtTitleLogin = findViewById(R.id.txtTitleLogin);
        txtSlogan.setTypeface(typeface);
        txtTitleLogin.setTypeface(typeface);

        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btnSignIn)
            startActivity(new Intent(this, SignInActivity.class));
        else if(v == btnSignUp)
            startActivity(new Intent(this, SignUpActivity.class));
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
