package com.example.win7.apphoctiengnhat.Dialog.LessonTest.Congrats;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.win7.apphoctiengnhat.R;

import java.util.Objects;

/**
 * Created by ntkhai1996 on 28-Apr-18.
 */

public class DialogTestCongrats extends DialogFragment{

    private String title;
    private TextView txtCustomDialogFragmentTitle;
    private Button customDialogFragmentOK;

    public static DialogTestCongrats newInstance(String title, String message) {
        Bundle args = new Bundle();
        args.putString("title",title);
        args.putString("message",message);
        DialogTestCongrats fragment = new DialogTestCongrats();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = Objects.requireNonNull(this.getArguments()).getString("title");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().requestWindowFeature(STYLE_NO_TITLE);
        setCancelable(false);
        Objects.requireNonNull(getDialog().getWindow()).getAttributes().windowAnimations = R.style.BounceDialog;
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.border_custom_dialog);

        return inflater.inflate(R.layout.dialog_lesson_test_congrat,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        customDialogFragmentOK       = view.findViewById(R.id.customDialogFragmentOK);
        txtCustomDialogFragmentTitle = view.findViewById(R.id.txtCustomDialogFragmentTitle);
        setOnTouchTestButton();
        setTitleAndMessage();
    }

    private void setTitleAndMessage() {
        txtCustomDialogFragmentTitle.setText(title);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setOnTouchTestButton() {
        View.OnTouchListener onTouch = new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        customDialogFragmentOK.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkOnTouch, null));
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        customDialogFragmentOK.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkOnTouch, null));
                        break;
                    case MotionEvent.ACTION_UP:
                        customDialogFragmentOK.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark, null));
                        dismiss();
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        customDialogFragmentOK.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark, null));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        customDialogFragmentOK.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark, null));
                        break;
                }
                return true;
            }
        };
        customDialogFragmentOK.setOnTouchListener(onTouch);
    }
}
