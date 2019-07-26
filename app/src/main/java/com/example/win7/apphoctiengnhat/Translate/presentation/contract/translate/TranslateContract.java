package com.example.win7.apphoctiengnhat.Translate.presentation.contract.translate;

import com.example.win7.apphoctiengnhat.Translate.presentation.contract.BaseContract;

/**
 * Created by NGusarov on 17/03/17.
 */

public interface TranslateContract extends BaseContract {

    interface View extends BaseContract.BaseView {
        void showTranslateResult(String message);
        void setLanguages();

        void showProgressBar();
        void dismissProgressBar();

        void showDialog(String message);

        int getFromSpinnerPosition();

        int getToSpinnerPosition();

        void setFromSpinnerSelection(int toSpinnerPosition);

        void setToSpinnerPosition(int tmpPosition);

        void invalidateSpinnerView();

        void swapText();

        void clearText();

        String getOriginal();
    }

    interface Presenter extends BaseContract.BasePresenter {
        void getTranslate(String message);
    }
}
