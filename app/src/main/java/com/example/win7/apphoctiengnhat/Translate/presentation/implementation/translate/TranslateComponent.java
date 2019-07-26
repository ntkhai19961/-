package com.example.win7.apphoctiengnhat.Translate.presentation.implementation.translate;

import com.example.win7.apphoctiengnhat.App.Fragment.Recognition.ObjectRecognition.Fragment_ObjectRecognition;
import com.example.win7.apphoctiengnhat.App.Fragment.Recognition.TextRecognition.NhanDienChuCai;
import com.example.win7.apphoctiengnhat.Translate.AppComponent;
import com.example.win7.apphoctiengnhat.Translate.controller.data.spreference.SPreferenceModule;
import com.example.win7.apphoctiengnhat.Translate.ui.fragment.translate.TranslateFragment;
import com.example.win7.apphoctiengnhat.Translate.ui.view.LanguageSelectView;

import dagger.Component;

/**
 * Created by NGusarov on 17/03/17.
 */

@TranslateScope
@Component(dependencies = AppComponent.class,
            modules = {TranslateModule.class, SPreferenceModule.class})
public interface TranslateComponent {
    void inject(TranslateFragment fragment);
    void inject(LanguageSelectView selectView);
    void inject(NhanDienChuCai fragment);
    void inject(Fragment_ObjectRecognition fragment);
}
