package com.example.win7.apphoctiengnhat.Translate;

import android.content.Context;

import com.example.win7.apphoctiengnhat.Translate.controller.data.db.Repository;
import com.example.win7.apphoctiengnhat.Translate.controller.data.db.RepositoryModule;
import com.example.win7.apphoctiengnhat.Translate.controller.data.service.history.HistoryDataModule;
import com.example.win7.apphoctiengnhat.Translate.controller.data.service.history.HistoryDataService;
import com.example.win7.apphoctiengnhat.Translate.controller.network.NetworkModule;
import com.example.win7.apphoctiengnhat.Translate.controller.network.YandexTranslateApi;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by NGusarov on 17/03/17.
 */

// component is alive while the application is live. Global
@Component(modules = {AppModule.class, NetworkModule.class,
                        RepositoryModule.class, HistoryDataModule.class})
@Singleton
public interface AppComponent {
    YandexTranslateApi yandexApi();
    Repository repository();
    HistoryDataService historyDataService();
    Context context();

}

