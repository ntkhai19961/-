package com.ng.yandextranslate.presentation.implementation.favorite;

import com.ng.yandextranslate.controller.data.service.history.HistoryDataService;
import com.ng.yandextranslate.presentation.contract.favorite.FavoriteContract;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
        value = "dagger.internal.codegen.ComponentProcessor",
        comments = "https://google.github.io/dagger"
)
public final class FavoritePresenterIml_Factory implements Factory<FavoritePresenterIml> {
    private final Provider<FavoriteContract.View> viewProvider;

    private final Provider<HistoryDataService> historyDataServiceProvider;

    public FavoritePresenterIml_Factory(
            Provider<FavoriteContract.View> viewProvider,
            Provider<HistoryDataService> historyDataServiceProvider) {
        assert viewProvider != null;
        this.viewProvider = viewProvider;
        assert historyDataServiceProvider != null;
        this.historyDataServiceProvider = historyDataServiceProvider;
    }

    @Override
    public FavoritePresenterIml get() {
        return new FavoritePresenterIml(viewProvider.get(), historyDataServiceProvider.get());
    }

    public static Factory<FavoritePresenterIml> create(
            Provider<FavoriteContract.View> viewProvider,
            Provider<HistoryDataService> historyDataServiceProvider) {
        return new FavoritePresenterIml_Factory(viewProvider, historyDataServiceProvider);
    }
}
