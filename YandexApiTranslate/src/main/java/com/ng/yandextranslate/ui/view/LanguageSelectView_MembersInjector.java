package com.ng.yandextranslate.ui.view;

import com.ng.yandextranslate.presentation.implementation.translate.TranslatePresenterImpl;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
        value = "dagger.internal.codegen.ComponentProcessor",
        comments = "https://google.github.io/dagger"
)
public final class LanguageSelectView_MembersInjector
        implements MembersInjector<LanguageSelectView> {
    private final Provider<TranslatePresenterImpl> mTranslatePresenterProvider;

    public LanguageSelectView_MembersInjector(
            Provider<TranslatePresenterImpl> mTranslatePresenterProvider) {
        assert mTranslatePresenterProvider != null;
        this.mTranslatePresenterProvider = mTranslatePresenterProvider;
    }

    public static MembersInjector<LanguageSelectView> create(
            Provider<TranslatePresenterImpl> mTranslatePresenterProvider) {
        return new LanguageSelectView_MembersInjector(mTranslatePresenterProvider);
    }

    @Override
    public void injectMembers(LanguageSelectView instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        instance.mTranslatePresenter = mTranslatePresenterProvider.get();
    }

    public static void injectMTranslatePresenter(
            LanguageSelectView instance, Provider<TranslatePresenterImpl> mTranslatePresenterProvider) {
        instance.mTranslatePresenter = mTranslatePresenterProvider.get();
    }
}
