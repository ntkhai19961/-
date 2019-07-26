package com.example.win7.apphoctiengnhat.Translate;

import com.example.win7.apphoctiengnhat.App.Fragment.Recognition.TextRecognition.TessDataManager;
import com.facebook.stetho.Stetho;

/**
 * Created by NG on 15.03.17.
 */

//test changes
    // extends TessDataManager do TessDataManager đã extends "Application" rồi
    // https://stackoverflow.com/questions/32395482/how-to-add-two-androidname-attributes-to-application-tag-in-manifest-file
    // Vì vậy trong manifest chỉ cần 1 android:name = "" là ".App" này thôi
    // Cũng không cần android:name = "" là ".TessDataManager" nữa do Class App này đã thừa kế và gọi rồi.
public class App extends TessDataManager {

    private static AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);

        mAppComponent = buildAppComponent();
    }

    private AppComponent buildAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static AppComponent getAppComponent() {
        return mAppComponent;
    }
}
