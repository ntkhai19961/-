package com.example.win7.apphoctiengnhat.Translate.controller.data.spreference;

import android.content.Context;

import com.example.win7.apphoctiengnhat.Translate.presentation.implementation.translate.TranslateScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by NG on 24.04.17.
 */

@Module
public class SPreferenceModule {

    @TranslateScope
    @Provides
    SPreferenceManager provideSPreferenceManager(Context context) {
        return new SPreferenceManager(context);
    }
}
