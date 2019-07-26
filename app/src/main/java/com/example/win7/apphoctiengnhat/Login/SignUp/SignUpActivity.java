package com.example.win7.apphoctiengnhat.Login.SignUp;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.win7.apphoctiengnhat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.wang.avi.AVLoadingIndicatorView;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialEditText edtEmail_SignUpActivity, edtPassword_SignUpActivity;
    private Button btnSignUp_SignUpActitivy;
    private AVLoadingIndicatorView AVLoadingIndicatorView_SignUpActivity;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        initView();
    }


    private void initView() {

        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/NABILA.TTF");

        edtEmail_SignUpActivity    = findViewById(R.id.edtEmail_SignUpActivity);
        edtPassword_SignUpActivity = findViewById(R.id.edtPassword_SignUpActivity);

        btnSignUp_SignUpActitivy = findViewById(R.id.btnSignUp_SignUpActitivy);
        btnSignUp_SignUpActitivy.setOnClickListener(this);
        btnSignUp_SignUpActitivy.setTypeface(typeface);

        AVLoadingIndicatorView_SignUpActivity = findViewById(R.id.AVLoadingIndicatorView_SignUpActivity);
    }

    @Override
    public void onClick(View v) {
        if( v == btnSignUp_SignUpActitivy ) {
            if(checkEditText())
                createUserWithEmailAndPassword(edtEmail_SignUpActivity.getText().toString(),edtPassword_SignUpActivity.getText().toString());
        }
    }

    private boolean checkEditText() {

        String email    = edtEmail_SignUpActivity.getText().toString().trim();
        String password = edtPassword_SignUpActivity.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void createUserWithEmailAndPassword(String email, String password){

        AVLoadingIndicatorView_SignUpActivity.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        AVLoadingIndicatorView_SignUpActivity.setVisibility(View.INVISIBLE);
                        if(task.isSuccessful())
                            Toast.makeText(SignUpActivity.this, "Sign Up Successfully !!", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(SignUpActivity.this, "Sign Up Failed !!!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        AVLoadingIndicatorView_SignUpActivity.setVisibility(View.GONE);
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
