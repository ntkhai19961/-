package com.example.win7.apphoctiengnhat.Login.SignIn;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.win7.apphoctiengnhat.App.Activity.Main.MainActivity;
import com.example.win7.apphoctiengnhat.Constant.UserInfo;
import com.example.win7.apphoctiengnhat.LessonDetails.Fragment.Test.model.UserLevel;
import com.example.win7.apphoctiengnhat.Login.LoginActivity;
import com.example.win7.apphoctiengnhat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Objects;


public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtEmail, edtPassword;
    private Button btnSignIn_SignInActitivy;
    private AVLoadingIndicatorView AVLoadingIndicatorView_SignInActivity;
    private FirebaseAuth mAuth;
    private String userEmail;
    private UserLevel userLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        initView();
    }

    private void initView() {

        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/NABILA.TTF");

        edtEmail    = findViewById(R.id.edtEmail_SignInActivity);
        edtPassword = findViewById(R.id.edtPassword_SignInActivity);

        btnSignIn_SignInActitivy = findViewById(R.id.btnSignIn_SignInActitivy);
        btnSignIn_SignInActitivy.setOnClickListener(this);
        btnSignIn_SignInActitivy.setTypeface(typeface);

        AVLoadingIndicatorView_SignInActivity = findViewById(R.id.AVLoadingIndicatorView_SignInActivity);

    }

    @Override
    public void onClick(View v) {
        if( v == btnSignIn_SignInActitivy ) {
            if(checkEditText()){
                signInWithEmailAndPassword(edtEmail.getText().toString(),edtPassword.getText().toString());
            }
        }
    }

    private boolean checkEditText() {

        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void signInWithEmailAndPassword(String email, String password){

        AVLoadingIndicatorView_SignInActivity.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        AVLoadingIndicatorView_SignInActivity.setVisibility(View.INVISIBLE);

                        if(task.isSuccessful()) {
                            Toast.makeText(SignInActivity.this, "Sign In Successfully !!", Toast.LENGTH_SHORT).show();

                            getUserInfo();

                            startActivity(new Intent(SignInActivity.this, MainActivity.class));
                            LoginActivity.LoginActivity.finish();
                            finish();
                        } else {
                            Toast.makeText(SignInActivity.this, "Sign In failed !!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void getUserInfo() {

        userEmail = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
        removeDotComFromEmail();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("user").child(userEmail).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                userLevel = dataSnapshot.getValue(UserLevel.class);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // when get data done
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                saveUserCache();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void saveUserCache() {

        SharedPreferences sharedPreferences = getSharedPreferences(UserInfo.SHARED_REFERENCES_NAME, Context.MODE_PRIVATE);

        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(UserInfo.KEY_EMAIL              , userEmail);
        editor.putString(UserInfo.KEY_KANJI_LEVEL        , userLevel.getKanjiLevel());
        editor.putString(UserInfo.KEY_LESSON_LEVEL       , userLevel.getLessonLevel());
        editor.putInt   (UserInfo.KEY_UNLOCK_KANJI       , userLevel.getUnlockKanji());
        editor.putInt   (UserInfo.KEY_UNLOCK_LESSON      , userLevel.getUnlockLesson());
        editor.putInt   (UserInfo.KEY_UNLOCK_CHILD_LESSON, userLevel.getUnlockChildLesson());

        Log.e("KEY_EMAIL",userEmail);
        Log.e("KEY_KANJI_LEVEL",userLevel.getKanjiLevel());
        Log.e("KEY_LESSON_LEVEL",userLevel.getLessonLevel());
        Log.e("KEY_UNLOCK_KANJI",userLevel.getUnlockKanji()+ "");
        Log.e("KEY_UNLOCK_LESSON",userLevel.getUnlockLesson()+ "");
        Log.e("KEY_UNLOCK_CHILD_LESSON",userLevel.getUnlockChildLesson()+ "");

        editor.apply();
    }

    private void removeDotComFromEmail() {
        int startRemoveAt = 0;

        StringBuilder sb = new StringBuilder(userEmail);
        for(int i = 0 ; i < sb.length() ; i++){
            if(sb.charAt(i) == '.'){
                startRemoveAt = i;
                break;
            }
        }
        if(startRemoveAt != 0)
            sb.delete(startRemoveAt,sb.length());

        userEmail =  sb.toString();
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
