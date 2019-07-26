package com.example.win7.apphoctiengnhat.Translate.ui.fragment.translate;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.win7.apphoctiengnhat.CurrentFragmentUseTranslate.CurrentFragmentUseTranslatePresentationImpl;
import com.example.win7.apphoctiengnhat.Translate.App;
import com.example.win7.apphoctiengnhat.R;
import com.example.win7.apphoctiengnhat.Translate.presentation.contract.translate.TranslateContract;
import com.example.win7.apphoctiengnhat.Translate.presentation.implementation.translate.DaggerTranslateComponent;
import com.example.win7.apphoctiengnhat.Translate.presentation.implementation.translate.TranslateComponent;
import com.example.win7.apphoctiengnhat.Translate.presentation.implementation.translate.TranslateModule;
import com.example.win7.apphoctiengnhat.Translate.presentation.implementation.translate.TranslatePresenterImpl;
import com.example.win7.apphoctiengnhat.Translate.ui.fragment.BaseFragment;
import com.example.win7.apphoctiengnhat.Translate.ui.view.LanguageSelectView;
import com.example.win7.apphoctiengnhat.Translate.util.DebounceTextWatcher;
import com.example.win7.apphoctiengnhat.Translate.util.DoneOnEditorActionListener;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by NG on 15.03.17.
 */

public class TranslateFragment extends BaseFragment implements TranslateContract.View , ScreenShotable{

    private static final String TAG = TranslateFragment.class.getSimpleName();
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @BindView(R.id.translate_language_select)
    LanguageSelectView mLanguageSelectView;
    @BindView(R.id.translate_edit_text_in)
    EditText mEditTextIn;
    @BindView(R.id.translate_text_view_out)
    TextView mTextViewOut;
    @BindView(R.id.translate_progress_for_text)
    ProgressBar mProgressBar;
    @BindView(R.id.translate_button_clear)
    ImageButton mButtonClear;
    @BindView(R.id.translate_button_add_to_favor)
    ImageButton mButtonAddToFavor;
    @BindView(R.id.btnSpeak_FragmentTranslate)
    ImageButton btnSpeak_FragmentTranslate;

    private static TranslateComponent mTranslateComponent;

    @Inject
    TranslatePresenterImpl mPresenter;

    @Inject
    DebounceTextWatcher mDebounceTextWatcher;

    public static Fragment newInstance(Bundle args) {
        return new TranslateFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_translate_yandex_api_translate, container, false);

        // khởi tạo cho biết fragment này sử dụng
        CurrentFragmentUseTranslatePresentationImpl.CURRENT_LANGUAGE_PAIR = 3;

        ButterKnife.bind(this, rootView);


        mTranslateComponent = buildTranslateComponent();
        mTranslateComponent.inject(this);

        mEditTextIn.setOnEditorActionListener(new DoneOnEditorActionListener());
        mEditTextIn.addTextChangedListener(mDebounceTextWatcher);


        mLanguageSelectView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, final MotionEvent event) {
                InputMethodManager inputMethodManager = (InputMethodManager)  getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                return false;
            }
        });

        mButtonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                clearText();
            }
        });

        mButtonAddToFavor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (!TextUtils.isEmpty(mEditTextIn.getText().toString())) {
                    mPresenter.makeLastFavorite();
                }
            }
        });

        btnSpeak_FragmentTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        return rootView;
    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Online Dictionnary");
//    }

    public static TranslateComponent getTranslateComponent() {
        return mTranslateComponent;
    }

    private TranslateComponent buildTranslateComponent() {
        return DaggerTranslateComponent.builder()
                .appComponent(App.getAppComponent())
                .translateModule(new TranslateModule(this))
                .build();
    }

    public void showDialog(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getFromSpinnerPosition() {
        return mLanguageSelectView.getFromSpinnerPosition();
    }

    @Override
    public int getToSpinnerPosition() {
        return mLanguageSelectView.getToSpinnerPosition();
    }

    @Override
    public void setFromSpinnerSelection(final int position) {
        mLanguageSelectView.setFromSpinnerPosition(position);
    }

    @Override
    public void setToSpinnerPosition(final int position) {
        mLanguageSelectView.setToSpinnerPosition(position);
    }

    @Override
    public void showProgressBar() {
        Log.d(TAG, "showProgressBar");
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mButtonAddToFavor.setEnabled(false);
                mTextViewOut.setVisibility(View.INVISIBLE);
                mProgressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void dismissProgressBar() {
        Log.d(TAG, "dismissProgressBar");
        mButtonAddToFavor.setEnabled(true);
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mButtonAddToFavor.setEnabled(true);
                mTextViewOut.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void setLanguages() {
        mLanguageSelectView.setLanguages();
    }

    @Override
    public void showTranslateResult(String message) {
        mTextViewOut.setText(message);
    }

    @Override
    public void invalidateSpinnerView() {
        mLanguageSelectView.invalidate();
    }

    @Override
    public void swapText() {
        String tmp = mEditTextIn.getText().toString();
        mEditTextIn.setText(mTextViewOut.getText().toString());
        mTextViewOut.setText(tmp);
    }

    @Override
    public void clearText() {
        mEditTextIn.getText().clear();
        mTextViewOut.setText(null);
    }

    @Override
    public String getOriginal() {
        String result = mEditTextIn.getText().toString();
        Log.d(TAG, "result: " + result);
        return result;
    }

    @Override
    public void showError(final String errorMessage) {
        showDialog(errorMessage);
    }

    //----------------------------------------------------------------------------------------------

    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }

    //----------------------------------------------------------------------------------------------

    /**
     * Showing google speech input dialog
     */
    private void promptSpeechInput() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ja");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Say Something");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getContext(),
                    "orry! Your device doesn\\'t support speech input",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == Activity.RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String userQuery=result.get(0);
                    mEditTextIn.setText(userQuery);
                }
                break;
            }

        }
    }

    //----------------------------------------------------------------------------------------------
}
