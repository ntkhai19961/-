package com.ng.yandextranslate.ui.fragment.favorite;

import com.ng.yandextranslate.controller.adapter.FavoriteRecyclerViewAdapter;
import com.ng.yandextranslate.presentation.implementation.favorite.FavoritePresenterIml;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
        value = "dagger.internal.codegen.ComponentProcessor",
        comments = "https://google.github.io/dagger"
)
public final class FavoriteFragment_MembersInjector implements MembersInjector<FavoriteFragment> {
    private final Provider<FavoritePresenterIml> mPresenterProvider;

    private final Provider<FavoriteRecyclerViewAdapter> mAdapterProvider;

    public FavoriteFragment_MembersInjector(
            Provider<FavoritePresenterIml> mPresenterProvider,
            Provider<FavoriteRecyclerViewAdapter> mAdapterProvider) {
        assert mPresenterProvider != null;
        this.mPresenterProvider = mPresenterProvider;
        assert mAdapterProvider != null;
        this.mAdapterProvider = mAdapterProvider;
    }

    public static MembersInjector<FavoriteFragment> create(
            Provider<FavoritePresenterIml> mPresenterProvider,
            Provider<FavoriteRecyclerViewAdapter> mAdapterProvider) {
        return new FavoriteFragment_MembersInjector(mPresenterProvider, mAdapterProvider);
    }

    @Override
    public void injectMembers(FavoriteFragment instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        instance.mPresenter = mPresenterProvider.get();
        instance.mAdapter = mAdapterProvider.get();
    }

    public static void injectMPresenter(
            FavoriteFragment instance, Provider<FavoritePresenterIml> mPresenterProvider) {
        instance.mPresenter = mPresenterProvider.get();
    }

    public static void injectMAdapter(
            FavoriteFragment instance, Provider<FavoriteRecyclerViewAdapter> mAdapterProvider) {
        instance.mAdapter = mAdapterProvider.get();
    }
}
