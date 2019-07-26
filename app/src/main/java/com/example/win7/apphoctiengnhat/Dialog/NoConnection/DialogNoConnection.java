package com.example.win7.apphoctiengnhat.Dialog.NoConnection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.win7.apphoctiengnhat.R;

import java.util.Objects;

/**
 * Created by ntkhai1996 on 30-Apr-18.
 */

public class DialogNoConnection extends DialogFragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().requestWindowFeature(STYLE_NO_TITLE);
        setCancelable(false);
        Objects.requireNonNull(getDialog().getWindow()).getAttributes().windowAnimations = R.style.FadeScaleOutIn;
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.border_custom_dialog);

        return inflater.inflate(R.layout.dialog_no_connection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnNoConnectionOK = view.findViewById(R.id.btnNoConnectionOK);
        btnNoConnectionOK.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnNoConnectionOK:
                dismiss();
                break;
        }
    }
}
