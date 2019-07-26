package com.ng.yandextranslate.controller.adapter;

import com.ng.yandextranslate.presentation.implementation.history.HistoryPresenterImpl;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
        value = "dagger.internal.codegen.ComponentProcessor",
        comments = "https://google.github.io/dagger"
)
public final class HistoryRecyclerViewAdapter_Factory
        implements Factory<HistoryRecyclerViewAdapter> {
    private final MembersInjector<HistoryRecyclerViewAdapter>
            historyRecyclerViewAdapterMembersInjector;

    private final Provider<HistoryPresenterImpl> presenterProvider;

    public HistoryRecyclerViewAdapter_Factory(
            MembersInjector<HistoryRecyclerViewAdapter> historyRecyclerViewAdapterMembersInjector,
            Provider<HistoryPresenterImpl> presenterProvider) {
        assert historyRecyclerViewAdapterMembersInjector != null;
        this.historyRecyclerViewAdapterMembersInjector = historyRecyclerViewAdapterMembersInjector;
        assert presenterProvider != null;
        this.presenterProvider = presenterProvider;
    }

    @Override
    public HistoryRecyclerViewAdapter get() {
        return MembersInjectors.injectMembers(
                historyRecyclerViewAdapterMembersInjector,
                new HistoryRecyclerViewAdapter(presenterProvider.get()));
    }

    public static Factory<HistoryRecyclerViewAdapter> create(
            MembersInjector<HistoryRecyclerViewAdapter> historyRecyclerViewAdapterMembersInjector,
            Provider<HistoryPresenterImpl> presenterProvider) {
        return new HistoryRecyclerViewAdapter_Factory(
                historyRecyclerViewAdapterMembersInjector, presenterProvider);
    }
}
