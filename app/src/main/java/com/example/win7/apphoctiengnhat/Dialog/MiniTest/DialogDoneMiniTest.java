package com.example.win7.apphoctiengnhat.Dialog.MiniTest;

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
import com.example.win7.apphoctiengnhat.interfaces.Dialog_MiniTest_Interface;

import java.util.Objects;

public class DialogDoneMiniTest extends DialogFragment{

    private int result = 0;
    private Dialog_MiniTest_Interface listener;
    private Button btnGoBack;

    public void setResult(int result) {
        this.result = result;
    }

    public void setListener(Dialog_MiniTest_Interface listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().requestWindowFeature(STYLE_NO_TITLE);
        setCancelable(false);
        Objects.requireNonNull(getDialog().getWindow()).getAttributes().windowAnimations = R.style.BounceDialog;
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.border_custom_dialog);

        return inflater.inflate(R.layout.dialog_mini_test_done, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    @SuppressLint("SetTextI18n")
    private void initView(View view) {
        TextView txtTongDiemDialog = view.findViewById(R.id.txtTongDiemDialog);
        txtTongDiemDialog.setText(String.valueOf(result));

        btnGoBack = view.findViewById(R.id.btnGoBack);
        setOnTouchTestButton();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setOnTouchTestButton() {
        View.OnTouchListener onTouch = new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        btnGoBack.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkOnTouch, null));
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        btnGoBack.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkOnTouch, null));
                        break;
                    case MotionEvent.ACTION_UP:
                        btnGoBack.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark, null));
                        goBack();
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        btnGoBack.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark, null));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        btnGoBack.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark, null));
                        break;
                }
                return true;
            }
        };
        btnGoBack.setOnTouchListener(onTouch);
    }

    private void goBack() {
        if(listener != null)
            listener.goBack();
    }
}
