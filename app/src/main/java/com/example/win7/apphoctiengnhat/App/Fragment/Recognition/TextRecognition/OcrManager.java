package com.example.win7.apphoctiengnhat.App.Fragment.Recognition.TextRecognition;

import android.graphics.Bitmap;

import com.googlecode.tesseract.android.TessBaseAPI;

/**
 * Created by ntkhai1996 on 01-Nov-17.
 */

public class OcrManager {

    TessBaseAPI baseAPI = null;

    public void initAPI() {
        baseAPI = new TessBaseAPI();

        // after copy, my path to trainned data is getExternalFilesDir(null)+"/tessdata/"+"vie.traineddata";
        // but init() function just need parent folder path of "tessdata", so it is getExternalFilesDir(null)
        String dataPath = TessDataManager.instance.getTessDataParentDirectory();
        baseAPI.init(dataPath,"jpn");
        // language code is name of trainned data file, except extendsion part
        // "vie.traineddata" => language code is "vie"


        // first param is datapath which is  part to the your trainned data, second is language code
        // now, your trainned data stored in assets folder, we need to copy it to another external storage folder.
        // It is better do this work when application start firt time
    }

    public String startRecognize(Bitmap bitmap)
    {
        if(baseAPI ==null)
            initAPI();
        baseAPI.setImage(bitmap);
        return baseAPI.getUTF8Text();
    }
}
