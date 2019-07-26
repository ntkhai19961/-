package com.example.win7.apphoctiengnhat.Translate.presentation.implementation.favorite;

import com.example.win7.apphoctiengnhat.Translate.AppComponent;
import com.example.win7.apphoctiengnhat.Translate.ui.fragment.favorite.FavoriteFragment;

import dagger.Component;

/**
 * Created by NG on 20.04.17.
 */

@FavoriteScope
@Component(dependencies = AppComponent.class, modules = FavoriteModule.class)
public interface FavoriteComponent {
    void inject(FavoriteFragment favoriteFragment);
}
