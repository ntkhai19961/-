package com.example.win7.apphoctiengnhat.Translate.presentation.contract.history;

import com.example.win7.apphoctiengnhat.Translate.model.pojo.HistoryData;
import com.example.win7.apphoctiengnhat.Translate.presentation.contract.BaseContract;

import java.util.List;

/**
 * Created by NG on 11.04.17.
 */

public interface HistoryContract extends BaseContract {

    interface View extends BaseContract.BaseView {
        void showEmptyView();
    }

    interface Presenter extends BaseContract.BasePresenter {
        List<HistoryData> getHistory();

        void clearHistory();

        void makeFavorite(int key, boolean isChecked);
    }
}
