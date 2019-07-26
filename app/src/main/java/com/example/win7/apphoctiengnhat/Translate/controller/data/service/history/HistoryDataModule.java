package com.example.win7.apphoctiengnhat.Translate.controller.data.service.history;

import com.example.win7.apphoctiengnhat.Translate.controller.data.db.Repository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by NG on 11.04.17.
 */

@Module
public class HistoryDataModule {

    @Provides
    @Singleton
    HistoryDataService provideHistoryDataModule(Repository repository) {
        return new HistoryDataService(repository);
    }
}
