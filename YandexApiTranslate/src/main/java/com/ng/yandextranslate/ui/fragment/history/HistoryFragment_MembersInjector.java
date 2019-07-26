package com.ng.yandextranslate.ui.fragment.history;

import com.ng.yandextranslate.controller.adapter.HistoryRecyclerViewAdapter;
import com.ng.yandextranslate.presentation.implementation.history.HistoryPresenterImpl;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
        value = "dagger.internal.codegen.ComponentProcessor",
        comments = "https://google.github.io/dagger"
)
public final class HistoryFragment_MembersInjector implements MembersInjector<HistoryFragment> {
    private final Provider<HistoryPresenterImpl> mPresenterProvider;

    private final Provider<HistoryRecyclerViewAdapter> mHistoryRecyclerViewAdapterProvider;

    public HistoryFragment_MembersInjector(
            Provider<HistoryPresenterImpl> mPresenterProvider,
            Provider<HistoryRecyclerViewAdapter> mHistoryRecyclerViewAdapterProvider) {
        assert mPresenterProvider != null;
        this.mPresenterProvider = mPresenterProvider;
        assert mHistoryRecyclerViewAdapterProvider != null;
        this.mHistoryRecyclerViewAdapterProvider = mHistoryRecyclerViewAdapterProvider;
    }

    public static MembersInjector<HistoryFragment> create(
            Provider<HistoryPresenterImpl> mPresenterProvider,
            Provider<HistoryRecyclerViewAdapter> mHistoryRecyclerViewAdapterProvider) {
        return new HistoryFragment_MembersInjector(
                mPresenterProvider, mHistoryRecyclerViewAdapterProvider);
    }

    @Override
    public void injectMembers(HistoryFragment instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        instance.mPresenter = mPresenterProvider.get();
        instance.mHistoryRecyclerViewAdapter = mHistoryRecyclerViewAdapterProvider.get();
    }

    public static void injectMPresenter(
            HistoryFragment instance, Provider<HistoryPresenterImpl> mPresenterProvider) {
        instance.mPresenter = mPresenterProvider.get();
    }

    public static void injectMHistoryRecyclerViewAdapter(
            HistoryFragment instance,
            Provider<HistoryRecyclerViewAdapter> mHistoryRecyclerViewAdapterProvider) {
        instance.mHistoryRecyclerViewAdapter = mHistoryRecyclerViewAdapterProvider.get();
    }
}
