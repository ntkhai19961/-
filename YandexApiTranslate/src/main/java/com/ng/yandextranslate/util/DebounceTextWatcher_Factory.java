package com.ng.yandextranslate.util;

import com.ng.yandextranslate.presentation.implementation.translate.TranslatePresenterImpl;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
        value = "dagger.internal.codegen.ComponentProcessor",
        comments = "https://google.github.io/dagger"
)
public final class DebounceTextWatcher_Factory implements Factory<DebounceTextWatcher> {
    private final Provider<TranslatePresenterImpl> presenterProvider;

    public DebounceTextWatcher_Factory(Provider<TranslatePresenterImpl> presenterProvider) {
        assert presenterProvider != null;
        this.presenterProvider = presenterProvider;
    }

    @Override
    public DebounceTextWatcher get() {
        return new DebounceTextWatcher(presenterProvider.get());
    }

    public static Factory<DebounceTextWatcher> create(
            Provider<TranslatePresenterImpl> presenterProvider) {
        return new DebounceTextWatcher_Factory(presenterProvider);
    }
}
