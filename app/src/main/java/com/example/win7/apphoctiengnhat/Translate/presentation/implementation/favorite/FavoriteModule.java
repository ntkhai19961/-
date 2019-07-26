package com.example.win7.apphoctiengnhat.Translate.presentation.implementation.favorite;

import com.example.win7.apphoctiengnhat.Translate.presentation.contract.favorite.FavoriteContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by NG on 20.04.17.
 */

@Module
public class FavoriteModule {

    private final FavoriteContract.View mView;

    public FavoriteModule(final FavoriteContract.View view) {
        mView = view;
    }

    @Provides
    @FavoriteScope
    FavoriteContract.View provideView() {
        return mView;
    }
}
