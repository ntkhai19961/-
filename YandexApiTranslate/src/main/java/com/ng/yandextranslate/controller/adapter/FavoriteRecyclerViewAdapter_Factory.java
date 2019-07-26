package com.ng.yandextranslate.controller.adapter;

import com.ng.yandextranslate.presentation.implementation.favorite.FavoritePresenterIml;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
        value = "dagger.internal.codegen.ComponentProcessor",
        comments = "https://google.github.io/dagger"
)
public final class FavoriteRecyclerViewAdapter_Factory
        implements Factory<FavoriteRecyclerViewAdapter> {
    private final MembersInjector<FavoriteRecyclerViewAdapter>
            favoriteRecyclerViewAdapterMembersInjector;

    private final Provider<FavoritePresenterIml> presenterImlProvider;

    public FavoriteRecyclerViewAdapter_Factory(
            MembersInjector<FavoriteRecyclerViewAdapter> favoriteRecyclerViewAdapterMembersInjector,
            Provider<FavoritePresenterIml> presenterImlProvider) {
        assert favoriteRecyclerViewAdapterMembersInjector != null;
        this.favoriteRecyclerViewAdapterMembersInjector = favoriteRecyclerViewAdapterMembersInjector;
        assert presenterImlProvider != null;
        this.presenterImlProvider = presenterImlProvider;
    }

    @Override
    public FavoriteRecyclerViewAdapter get() {
        return MembersInjectors.injectMembers(
                favoriteRecyclerViewAdapterMembersInjector,
                new FavoriteRecyclerViewAdapter(presenterImlProvider.get()));
    }

    public static Factory<FavoriteRecyclerViewAdapter> create(
            MembersInjector<FavoriteRecyclerViewAdapter> favoriteRecyclerViewAdapterMembersInjector,
            Provider<FavoritePresenterIml> presenterImlProvider) {
        return new FavoriteRecyclerViewAdapter_Factory(
                favoriteRecyclerViewAdapterMembersInjector, presenterImlProvider);
    }
}
