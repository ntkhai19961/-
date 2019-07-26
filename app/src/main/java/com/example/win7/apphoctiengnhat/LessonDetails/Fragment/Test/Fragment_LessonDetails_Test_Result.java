package com.example.win7.apphoctiengnhat.LessonDetails.Fragment.Test;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.win7.apphoctiengnhat.Constant.UserInfo;
import com.example.win7.apphoctiengnhat.Dialog.LessonTest.Congrats.DialogTestCongrats;
import com.example.win7.apphoctiengnhat.Dialog.LessonTest.LevelUp.DialogLessonLevelUp;
import com.example.win7.apphoctiengnhat.LessonDetails.Fragment.Test.model.UserLevel;
import com.example.win7.apphoctiengnhat.R;
import com.example.win7.apphoctiengnhat.interfaces.Dialog_Lesson_Level_Up;
import com.example.win7.apphoctiengnhat.interfaces.Fragment_Lesson_Test_Interface;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ntkhai1996 on 08-Apr-18.
 */

public class Fragment_LessonDetails_Test_Result extends Fragment implements Dialog_Lesson_Level_Up {

    private DatabaseReference mDatabase;
    private Fragment_Lesson_Test_Interface listener;
    private ProgressBar progressBarLessonTestResult;
    private TextView txtCurrentResultLessonTest;
    private TextView txtTotalResultLessonTest;
    private TextView txtNumberOfRightAnswerLessonTest;
    private TextView txtPointTitle;
    private CardView cardViewResultLessonTest;
    private Integer currentResult = 0;
    private Integer totalResult   = 0;
    private Integer totalNumberOfAnswer = 0;
    private Integer numberOfRightAnswer = 0;
    private Integer idChildLesson = 0;
    private String  userEmail;
    private UserLevel userLevel;
    private DialogLessonLevelUp dialog;

    public void setListener(Fragment_Lesson_Test_Interface listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getUserResult();

        initFirebase();

        getUserLevel();
    }

    private void getUserLevel() {
        userLevel = new UserLevel();
        @SuppressWarnings("ConstantConditions")
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(UserInfo.SHARED_REFERENCES_NAME, Context.MODE_PRIVATE);

        if(sharedPreferences != null){

            userEmail = sharedPreferences.getString(UserInfo.KEY_EMAIL,"");

            userLevel.setLessonLevel (sharedPreferences.getString  (UserInfo.KEY_LESSON_LEVEL ,""));
            userLevel.setUnlockLesson(sharedPreferences.getInt     (UserInfo.KEY_UNLOCK_LESSON,0));
            userLevel.setUnlockChildLesson(sharedPreferences.getInt(UserInfo.KEY_UNLOCK_CHILD_LESSON,0));
        }
    }

    private void initFirebase() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private void getUserResult() {
        if(listener.getCurrentResult()     != null &&
           listener.getTotalResult()       != null &&
           listener.getTotalNumberAnswer() != null &&
           listener.getNumberRightAnswer() != null &&
           listener.getIdChildLesson()     != null){

            currentResult       = listener.getCurrentResult();
            totalResult         = listener.getTotalResult();
            totalNumberOfAnswer = listener.getTotalNumberAnswer();
            numberOfRightAnswer = listener.getNumberRightAnswer();
            idChildLesson       = listener.getIdChildLesson();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lesson_details_test_result , container , false);

        initView(rootView);

        return rootView;
    }

    @SuppressLint("SetTextI18n")
    private void initView(View rootView) {
        progressBarLessonTestResult       = rootView.findViewById(R.id.progressBarLessonTestResult);
        txtCurrentResultLessonTest        = rootView.findViewById(R.id.txtCurrentResultLessonTest);
        txtTotalResultLessonTest          = rootView.findViewById(R.id.txtTotalResultLessonTest);
        txtNumberOfRightAnswerLessonTest  = rootView.findViewById(R.id.txtNumberOfRightAnswerLessonTest);
        txtPointTitle                     = rootView.findViewById(R.id.txtPointTitle);
        cardViewResultLessonTest          = rootView.findViewById(R.id.cardViewResultLessonTest);
        TextView txtTotalAnswerLessonTest = rootView.findViewById(R.id.txtTotalAnswerLessonTest);

        setProgressBarAnimation();

        setCurrentResultAnimation();
        setCurrentNumberRightAnswerAnimation();

        txtTotalResultLessonTest.setText("/" + totalResult);
        txtTotalAnswerLessonTest.setText("/" + totalNumberOfAnswer);

        dialog = new DialogLessonLevelUp();
        dialog.setListener(this);
    }

    private void setCurrentNumberRightAnswerAnimation() {
        ValueAnimator animator = ValueAnimator.ofInt(0, numberOfRightAnswer);
        animator.setDuration(5000);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //float animatedValue = (float) valueAnimator.getAnimatedValue();
                txtNumberOfRightAnswerLessonTest.setText(valueAnimator.getAnimatedValue().toString());
            }
        });

        animator.start();
    }

    private void setCurrentResultAnimation() {
        ValueAnimator animator = ValueAnimator.ofInt(0, currentResult);
        animator.setDuration(5000);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //float animatedValue = (float) valueAnimator.getAnimatedValue();
                txtCurrentResultLessonTest.setText(valueAnimator.getAnimatedValue().toString());
            }
        });

        animator.start();

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                checkIfGraduated();
            }
        });
    }

    private void setProgressBarAnimation() {
        progressBarLessonTestResult.setMax(totalResult);

        ObjectAnimator animation = ObjectAnimator.ofInt(progressBarLessonTestResult, "progress",0, currentResult);
        animation.setDuration(5000);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }

    private void checkIfGraduated() {
        /*
            Khi level up mở bài tiếp theo có 2 trường hợp:
                1.  bài tiếp theo chưa mở => hiện thông báo, level up bình thường
                2.  bài tiếp theo đã mỡ   => không làm gì
        */
        if(numberOfRightAnswer < totalNumberOfAnswer - 2 )
            dialogFail(getContext()).show();
        // best result
        else if(txtCurrentResultLessonTest.getText().toString().equals(totalResult+"")) {

            txtTotalResultLessonTest.setText("");

            // AND next lesson haven't unlocked yet
            if(idChildLesson == userLevel.getUnlockChildLesson())
                showLevelUpDialog();
            else
                showCongratDialog();
        }
        // enough result to graduate , >= 5/7 right answer => graduated (80%/100%)
        else if(numberOfRightAnswer >= totalNumberOfAnswer - 2 ){

            // AND next lesson haven't unlocked yet
            if(idChildLesson == userLevel.getUnlockChildLesson())
                showLevelUpDialog();
            else
                showCongratDialog();
        }
    }

    private void showCongratDialog() {
        DialogTestCongrats dialog = DialogTestCongrats.newInstance("Congratulation", "");
        dialog.show(getChildFragmentManager(),"LessonDetailsResultDialog");
    }

    private void showLevelUpDialog() {
        dialog.setCurrentLevel("From: " + userLevel.getLessonLevel());
        dialog.setCurrentUnlockLesson("From: " + userLevel.getUnlockLesson());
        dialog.setNextLevel("To: N5");
        dialog.setNextUnlockLesson("To: " + (userLevel.getUnlockLesson() + 1));
        dialog.show(getChildFragmentManager(),"");
    }

    private void updateUserLevel() {

        int numCurrentUnlockLesson = 0;
        int numCurrentUnlockChildLesson = 0;
        /*
        *   1 bài lớn có 4 bài con => phải ktra xem có phải đang ktra bài con thứ 4 hay không
        *   để biết có cần phải level up bài lớn
        *   bài thứ 4 trong 1 bài lớn luôn luôn chia hết cho 4
        *   1 % 4 != 0
        *   2 % 4 != 0
        *   3 % 4 != 0
        *   4 % 4 == 0
        */

        // đây là bài con thứ 4 => phải level bài lớn tiếp theo
        if( idChildLesson % 4 == 0 ){
            numCurrentUnlockLesson      = userLevel.getUnlockLesson() + 1;
            numCurrentUnlockChildLesson = userLevel.getUnlockChildLesson() + 1;
            mDatabase.child("user").child(userEmail).child("Info").child("UnlockLesson").setValue(numCurrentUnlockLesson);
            mDatabase.child("user").child(userEmail).child("Info").child("UnlockChildLesson").setValue(numCurrentUnlockChildLesson);

            updateCache(true, numCurrentUnlockLesson, numCurrentUnlockChildLesson);
        }
        // không phải bài con thứ 4 => chỉ level up bài con tiếp theo trong 1 bài lớn
        else {
            numCurrentUnlockChildLesson = userLevel.getUnlockChildLesson() + 1;
            mDatabase.child("user").child(userEmail).child("Info").child("UnlockChildLesson").setValue(numCurrentUnlockChildLesson);

            updateCache(false, numCurrentUnlockLesson, numCurrentUnlockChildLesson);
        }

    }

    private void updateCache(boolean isUpdateLesson, int numCurrentUnlockLesson, int numCurrentUnlockChildLesson) {

        @SuppressWarnings("ConstantConditions")
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(UserInfo.SHARED_REFERENCES_NAME, Context.MODE_PRIVATE);

        if(isUpdateLesson){

            sharedPreferences.edit().putInt(UserInfo.KEY_UNLOCK_LESSON      , numCurrentUnlockLesson).apply();
            sharedPreferences.edit().putInt(UserInfo.KEY_UNLOCK_CHILD_LESSON, numCurrentUnlockChildLesson).apply();

        } else {
            sharedPreferences.edit().putInt(UserInfo.KEY_UNLOCK_CHILD_LESSON, numCurrentUnlockChildLesson).apply();
        }

        Log.e("KEY_UNLOCK_LESSON_update",sharedPreferences.getInt(UserInfo.KEY_UNLOCK_LESSON,0) + "");
        Log.e("KEY_UNLOCK_CHILD_LESSON_update",sharedPreferences.getInt(UserInfo.KEY_UNLOCK_CHILD_LESSON,0) + "");
    }

    private AlertDialog.Builder dialogFail(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("Oops!!!");
        builder.setMessage("You don't have enough point to go to next level!!!\n" +
                           "");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //finish();
            }
        });

        return builder;
    }

    @Override
    public void onOK() {
        updateUserLevel();
        dialog.dismiss();
    }
}
