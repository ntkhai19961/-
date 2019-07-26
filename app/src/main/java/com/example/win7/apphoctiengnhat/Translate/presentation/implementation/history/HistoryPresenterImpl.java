package com.example.win7.apphoctiengnhat.Translate.presentation.implementation.history;

import com.example.win7.apphoctiengnhat.Translate.controller.data.service.history.HistoryDataService;
import com.example.win7.apphoctiengnhat.Translate.model.pojo.HistoryData;
import com.example.win7.apphoctiengnhat.Translate.presentation.contract.history.HistoryContract;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by NG on 11.04.17.
 */

public class HistoryPresenterImpl implements HistoryContract.Presenter {

    public static final String TAG = HistoryPresenterImpl.class.getSimpleName();

    HistoryContract.View mView;
    HistoryDataService mHistoryDataService;

    @Inject
    public HistoryPresenterImpl(HistoryContract.View view, HistoryDataService historyDataService) {
        this.mView = view;
        this.mHistoryDataService = historyDataService;
    }

    @Override
    public List<HistoryData> getHistory() {
        List<HistoryData> data = mHistoryDataService.getHistoryDataList();
        if (data.isEmpty()) {
            showEmptyView();
        }

        return data;
    }

    @Override
    public void clearHistory() {
        mHistoryDataService.deleteAllHistoryData();
        showEmptyView();
    }

    @Override
    public void makeFavorite(final int key, final boolean isChecked) {
        mHistoryDataService.makeFavorite(key, isChecked);
    }

    private void showEmptyView() {
        mView.showEmptyView();
    }
}
