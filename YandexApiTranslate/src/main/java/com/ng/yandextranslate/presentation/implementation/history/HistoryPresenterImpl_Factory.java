package com.ng.yandextranslate.presentation.implementation.history;

import com.ng.yandextranslate.controller.data.service.history.HistoryDataService;
import com.ng.yandextranslate.presentation.contract.history.HistoryContract;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
        value = "dagger.internal.codegen.ComponentProcessor",
        comments = "https://google.github.io/dagger"
)
public final class HistoryPresenterImpl_Factory implements Factory<HistoryPresenterImpl> {
    private final Provider<HistoryContract.View> viewProvider;

    private final Provider<HistoryDataService> historyDataServiceProvider;

    public HistoryPresenterImpl_Factory(
            Provider<HistoryContract.View> viewProvider,
            Provider<HistoryDataService> historyDataServiceProvider) {
        assert viewProvider != null;
        this.viewProvider = viewProvider;
        assert historyDataServiceProvider != null;
        this.historyDataServiceProvider = historyDataServiceProvider;
    }

    @Override
    public HistoryPresenterImpl get() {
        return new HistoryPresenterImpl(viewProvider.get(), historyDataServiceProvider.get());
    }

    public static Factory<HistoryPresenterImpl> create(
            Provider<HistoryContract.View> viewProvider,
            Provider<HistoryDataService> historyDataServiceProvider) {
        return new HistoryPresenterImpl_Factory(viewProvider, historyDataServiceProvider);
    }
}
