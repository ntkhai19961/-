package com.example.win7.apphoctiengnhat.Translate.presentation.implementation.history;

import com.example.win7.apphoctiengnhat.Translate.AppComponent;
import com.example.win7.apphoctiengnhat.Translate.ui.fragment.history.HistoryFragment;

import dagger.Component;

/**
 * Created by NG on 11.04.17.
 */

@HistoryScope
@Component(dependencies = AppComponent.class, modules = HistoryModule.class)
public interface HistoryComponent {
    void inject(HistoryFragment fragment);
}
