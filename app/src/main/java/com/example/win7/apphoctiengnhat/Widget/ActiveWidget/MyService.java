package com.example.win7.apphoctiengnhat.Widget.ActiveWidget;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by ntkhai1996 on 07-Nov-17.
 */

public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

//        AppWidget.arrayList = ManHinhChinh.arrayDataWidget;
//        Toast.makeText(this,"Service Actived",Toast.LENGTH_LONG).show();
//
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
