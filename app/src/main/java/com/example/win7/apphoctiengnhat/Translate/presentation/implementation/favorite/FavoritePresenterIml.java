package com.example.win7.apphoctiengnhat.Translate.presentation.implementation.favorite;

import com.example.win7.apphoctiengnhat.Translate.controller.data.service.history.HistoryDataService;
import com.example.win7.apphoctiengnhat.Translate.model.pojo.HistoryData;
import com.example.win7.apphoctiengnhat.Translate.presentation.contract.favorite.FavoriteContract;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by NG on 20.04.17.
 */

public class FavoritePresenterIml implements FavoriteContract.Presenter {

    private HistoryDataService mHistoryDataService;
    private FavoriteContract.View mView;

    @Inject
    public FavoritePresenterIml(FavoriteContract.View view, HistoryDataService historyDataService) {
        mView = view;
        mHistoryDataService = historyDataService;
    }

    public List<HistoryData> getFavorite() {
        List<HistoryData> list = mHistoryDataService.getFavoriteHistoryDataList();
        if (list.isEmpty())
            showEmptyView();
        return list;
    }

    @Override
    public void unMackeFavorite(final int key, final boolean isChecked) {
        mHistoryDataService.makeFavorite(key, isChecked);
    }

    @Override
    public void notifyData() {
        mView.notifyData();
    }

    @Override
    public void unMakeAllFavorites() {
        mHistoryDataService.unMakeAllFavorites();
        showEmptyView();
    }

    private void showEmptyView() {
        mView.showEmptyView();
    }
}
