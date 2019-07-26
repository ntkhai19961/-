package com.example.win7.apphoctiengnhat.Translate.presentation.implementation.translate;

import com.example.win7.apphoctiengnhat.Translate.controller.data.service.history.HistoryDataService;
import com.example.win7.apphoctiengnhat.Translate.controller.data.spreference.SPreferenceManager;
import com.example.win7.apphoctiengnhat.Translate.controller.network.YandexTranslateApi;
import com.example.win7.apphoctiengnhat.Translate.presentation.contract.translate.TranslateContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by NGusarov on 17/03/17.
 */

@Module
public class TranslateModule {

    private final TranslateContract.View mView;

    public TranslateModule(TranslateContract.View view) {
        this.mView = view;
    }

    @Provides
    @TranslateScope
    TranslateContract.View provideView() {
        return mView;
    }

    @Provides
    @TranslateScope
    TranslatePresenterImpl providePresenter(YandexTranslateApi api, HistoryDataService historyDataService, SPreferenceManager preferenceManager) {
        return new TranslatePresenterImpl(api, historyDataService, preferenceManager, this.mView);
    }
}
