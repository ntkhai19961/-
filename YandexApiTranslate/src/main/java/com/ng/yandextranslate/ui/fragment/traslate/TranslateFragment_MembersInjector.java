package com.ng.yandextranslate.ui.fragment.traslate;

import com.ng.yandextranslate.presentation.implementation.translate.TranslatePresenterImpl;
import com.ng.yandextranslate.util.DebounceTextWatcher;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
        value = "dagger.internal.codegen.ComponentProcessor",
        comments = "https://google.github.io/dagger"
)
public final class TranslateFragment_MembersInjector implements MembersInjector<TranslateFragment> {
    private final Provider<TranslatePresenterImpl> mPresenterProvider;

    private final Provider<DebounceTextWatcher> mDebounceTextWatcherProvider;

    public TranslateFragment_MembersInjector(
            Provider<TranslatePresenterImpl> mPresenterProvider,
            Provider<DebounceTextWatcher> mDebounceTextWatcherProvider) {
        assert mPresenterProvider != null;
        this.mPresenterProvider = mPresenterProvider;
        assert mDebounceTextWatcherProvider != null;
        this.mDebounceTextWatcherProvider = mDebounceTextWatcherProvider;
    }

    public static MembersInjector<TranslateFragment> create(
            Provider<TranslatePresenterImpl> mPresenterProvider,
            Provider<DebounceTextWatcher> mDebounceTextWatcherProvider) {
        return new TranslateFragment_MembersInjector(mPresenterProvider, mDebounceTextWatcherProvider);
    }

    @Override
    public void injectMembers(TranslateFragment instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        instance.mPresenter = mPresenterProvider.get();
        instance.mDebounceTextWatcher = mDebounceTextWatcherProvider.get();
    }

    public static void injectMPresenter(
            TranslateFragment instance, Provider<TranslatePresenterImpl> mPresenterProvider) {
        instance.mPresenter = mPresenterProvider.get();
    }

    public static void injectMDebounceTextWatcher(
            TranslateFragment instance, Provider<DebounceTextWatcher> mDebounceTextWatcherProvider) {
        instance.mDebounceTextWatcher = mDebounceTextWatcherProvider.get();
    }
}
