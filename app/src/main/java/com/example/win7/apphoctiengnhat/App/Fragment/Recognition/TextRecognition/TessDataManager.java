package com.example.win7.apphoctiengnhat.App.Fragment.Recognition.TextRecognition;

import android.app.Application;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by ntkhai1996 on 01-Nov-17.
 */

public class TessDataManager extends Application {

    public static TessDataManager instance = null;

    @Override
    public void onCreate() {
        super.onCreate();

        // start copy file here, copy vie.trainneddata from assets to external storage ../tessdata/vie.trainneddata
        // the data path, must contain sub folder call "tessdata", if not the lib will not work.
        instance = this;
        copyTessDataForTextRecognizor();
    }

    private String tessDataPath() {
        return TessDataManager.instance.getExternalFilesDir(null)+"/tessdata/";
    }

    public String getTessDataParentDirectory() {
        return TessDataManager.instance.getExternalFilesDir(null).getAbsolutePath();
    }


    private void copyTessDataForTextRecognizor() {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                AssetManager assetManager = TessDataManager.instance.getAssets();
                OutputStream out = null;
                try {
                    InputStream in = assetManager.open("jpn.traineddata");
                    String tesspath = instance.tessDataPath();
                    File tessFolder = new File(tesspath);
                    if (!tessFolder.exists())
                        tessFolder.mkdir();
                    String tessData = tesspath + "/" + "jpn.traineddata";
                    File tessFile = new File(tessData);
                    if (!tessFile.exists()) {
                        out = new FileOutputStream(tessData);
                        byte[] buffer = new byte[1024];
                        int read = in.read(buffer);
                        while (read != -1) {
                            out.write(buffer, 0, read);
                            read = in.read(buffer);
                        }
                        Log.e("TessDataManager", " Did finish copy tess file  ");


                    } else
                        Log.e("TessDataManager", " tess file exist  ");

                } catch (Exception e) {
                    Log.e("TessDataManager", "couldn't copy with the following error : " + e.toString());
                } finally {
                    try {
                        if (out != null)
                            out.close();
                    } catch (Exception exx) {
                        Log.e("TessDataManager",exx.toString());
                    }
                }
            }
        };
        new Thread(run).start();
    }
}
