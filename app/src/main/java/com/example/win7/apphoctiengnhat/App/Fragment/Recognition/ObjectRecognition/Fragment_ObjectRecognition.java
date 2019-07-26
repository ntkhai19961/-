package com.example.win7.apphoctiengnhat.App.Fragment.Recognition.ObjectRecognition;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenu;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.win7.apphoctiengnhat.CurrentFragmentUseTranslate.CurrentFragmentUseTranslatePresentationImpl;
import com.example.win7.apphoctiengnhat.R;
import com.example.win7.apphoctiengnhat.Translate.App;
import com.example.win7.apphoctiengnhat.Translate.presentation.contract.translate.TranslateContract;
import com.example.win7.apphoctiengnhat.Translate.presentation.implementation.translate.DaggerTranslateComponent;
import com.example.win7.apphoctiengnhat.Translate.presentation.implementation.translate.TranslateComponent;
import com.example.win7.apphoctiengnhat.Translate.presentation.implementation.translate.TranslateModule;
import com.example.win7.apphoctiengnhat.Translate.presentation.implementation.translate.TranslatePresenterImpl;
import com.example.win7.apphoctiengnhat.Translate.util.DebounceTextWatcher;
import com.example.win7.apphoctiengnhat.Translate.util.DoneOnEditorActionListener;
import com.microsoft.projectoxford.vision.VisionServiceClient;
import com.microsoft.projectoxford.vision.VisionServiceRestClient;
import com.google.gson.Gson;
import com.microsoft.projectoxford.vision.contract.AnalysisResult;
import com.microsoft.projectoxford.vision.contract.Caption;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.inject.Inject;

import io.github.yavski.fabspeeddial.FabSpeedDial;

/**
 * Created by ntkhai1996 on 06-Jan-18.
 */

public class Fragment_ObjectRecognition extends Fragment implements TranslateContract.View{

    final String TAG = "Result Description";
    final private int REQUEST_TAKE_PHOTO = 1024;
    final private int REQUEST_CHOOSE_PHOTO = 1023;
    final private int MY_CAMERA_REQUEST_CODE = 100;

    // Free trial : 30 days
    // From : 05/01/2018
    private VisionServiceClient visionServiceClient = new VisionServiceRestClient("5acd6cef17904003af930ad87bc6e95c","https://westcentralus.api.cognitive.microsoft.com/vision/v1.0");

    private ImageView imageView;
    private TextView txtIsThereAnyPicture;
    private TextView txtResultDescription;
    private TextView txtJapaneseTranslate;
    private TextView txtMeaning;
    private ProgressDialog mDialog;
    private FabSpeedDial fabSpeedButton;
    private AVLoadingIndicatorView avLoadingIndicatorViewJapanese;
    private AVLoadingIndicatorView avLoadingIndicatorViewMeaning;
    private Bitmap mBitmap;
    private Bitmap myCurrentImageBitMap;
    private ByteArrayInputStream inputStream;
    ByteArrayOutputStream outputStream;
    private String myCurrentResultDescription = "";


    private static TranslateComponent mTranslateComponent;
    @Inject
    TranslatePresenterImpl mPresenter;
    @Inject
    DebounceTextWatcher mDebounceTextWatcher;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_object_recognition, container, false);
        setHasOptionsMenu(true);

        initView(rootView);

        AddEventFabButon();

        configTranslate();

        return rootView;
    }

    private void configTranslate() {

        // build để biết View nào để translatePresenterImpl biết mà return kết quả translate về khi text view phía dưới thay đổi
        mTranslateComponent = buildTranslateComponent();
        mTranslateComponent.inject(this);

        txtResultDescription.setOnEditorActionListener(new DoneOnEditorActionListener());
        txtResultDescription.addTextChangedListener(mDebounceTextWatcher);

    }

    private void initView(View rootView){

        txtResultDescription = rootView.findViewById(R.id.txtDescription_FragmentObjectRecognition);
        txtIsThereAnyPicture = rootView.findViewById(R.id.isThereAnyPicture_FragmentObjectRecognition);
        txtJapaneseTranslate = rootView.findViewById(R.id.txtJapanese_FragmentObjectRecognition);
        txtMeaning = rootView.findViewById(R.id.txtMeaning_FragmentObjectRecognition);
        imageView = rootView.findViewById(R.id.imageView_Fragment_ObjectRecognition);
        fabSpeedButton = rootView.findViewById(R.id.fabSpeedButton_ObjectRecognition);
        avLoadingIndicatorViewJapanese = rootView.findViewById(R.id.AVLoadingIndicatorView_Japanese_FragmentObjectRecognition);
        avLoadingIndicatorViewMeaning = rootView.findViewById(R.id.AVLoadingIndicatorView_Meaning_FragmentObjectRecognition);

        mDialog = new ProgressDialog(getContext());
    }

    private void AddEventFabButon() {

        fabSpeedButton.setMenuListener(new FabSpeedDial.MenuListener() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                // false dont show menu
                return true;
            }

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {

                final String menuItemTitle = String.valueOf(menuItem.getTitle());
                menuItemAction(menuItemTitle);
                return true;
            }

            @Override
            public void onMenuClosed() {
            }
        });
    }

    private void menuItemAction(String menuItemTitle) {

        switch (menuItemTitle)
        {
            case "Take a picture":
                checkPermission();
                startCameraActivity();
                break;
            case "Choose a picture":
                ChoosePhoto();
                break;
            case "Proccess":
                startProccess();
                break;
        }

    }

    private void startProccess() {

        if(imageView.getDrawable() != null && mBitmap != null)
        {
            ReceiveDescriptionTask task = new ReceiveDescriptionTask();
            task.execute(inputStream);
        }
        else
            txtIsThereAnyPicture.setText("Take or choose a picture");
    }

    private void ChoosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,REQUEST_CHOOSE_PHOTO);
    }

    private void startCameraActivity() {
        try{
            // Chuyển Qua CAMERA
            final Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(pictureIntent, REQUEST_TAKE_PHOTO);

        } catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == Activity.RESULT_OK)
        {
            if(requestCode == REQUEST_TAKE_PHOTO)
            {
                mBitmap = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(mBitmap);

                // kiểm tra xem hình có thay đổi để có setText thành "" hay không
                if(myCurrentImageBitMap == null){
                    myCurrentImageBitMap = mBitmap;
                }
                else if(!myCurrentImageBitMap.sameAs(mBitmap)){
                    myCurrentImageBitMap = mBitmap;
                    txtJapaneseTranslate.setText("");
                    txtResultDescription.setText("");
                    txtMeaning.setText("");
                    myCurrentResultDescription = "";
                }

            }
            else if(requestCode == REQUEST_CHOOSE_PHOTO)
            {
                try {
                    Uri imageUri = data.getData();
                    InputStream is = getActivity().getContentResolver().openInputStream(imageUri);
                    mBitmap = BitmapFactory.decodeStream(is);
                    imageView.setImageBitmap(mBitmap);

                    // kiểm tra xem hình có thay đổi để có setText thành "" hay không
                    if(myCurrentImageBitMap == null){
                        myCurrentImageBitMap = mBitmap;
                    }
                    else if(!myCurrentImageBitMap.sameAs(mBitmap)){
                        myCurrentImageBitMap = mBitmap;
                        txtJapaneseTranslate.setText("");
                        txtResultDescription.setText("");
                        txtMeaning.setText("");
                        myCurrentResultDescription = "";
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            convertImageToStream();

        }

        txtIsThereAnyPicture.setText("");
    }

    private void convertImageToStream() {
        //Convert image to stream
        outputStream = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        inputStream = new ByteArrayInputStream(outputStream.toByteArray());
    }

    @SuppressLint("StaticFieldLeak")
    class ReceiveDescriptionTask extends AsyncTask<InputStream,String,String>{
        @Override
        protected String doInBackground(InputStream... params) {
            try{
                publishProgress("Recognizing....");
                String[] features = {"Description"};
                String[] details = {};

                AnalysisResult result = visionServiceClient.analyzeImage(params[0],features,details);

                String strResult = new Gson().toJson(result);
                return strResult;

            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            mDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            mDialog.dismiss();

            AnalysisResult result = new Gson().fromJson(s,AnalysisResult.class);
            StringBuilder stringBuilder = new StringBuilder();

            try{
                for(Caption caption:result.description.captions)
                {
                    stringBuilder.append(caption.text);
                }
                txtResultDescription.setText(stringBuilder);
                avLoadingIndicatorViewJapanese.setVisibility(View.VISIBLE);
                avLoadingIndicatorViewMeaning.setVisibility(View.VISIBLE);
            }
            catch(Exception ex){
                Log.e(TAG,ex.toString());
            }

        }

        @Override
        protected void onProgressUpdate(String... values) {
            mDialog.setMessage(values[0]);
        }
    }


    //----------------------------------------------------------------------------------------------
    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public void showTranslateResult(String message) {

        // dùng 1 biến phụ "myCurrentResultDescription" kiểm tra sự thay đổi của text description
        // lần translate thứ 1 : là từ en-ja (text Japanese)
        // lần translate thứ 2 : là từ en-vi (text Meaning)
        // do khi translate nhiều lần , hàm này sẽ tái sử dụng nhiều lần
        // kiểm tra như vậy để biết được đang cần translate language pair nào
        if(!myCurrentResultDescription.equals(txtResultDescription.getText().toString())){

            myCurrentResultDescription = txtResultDescription.getText().toString();

            txtJapaneseTranslate.setText(message);
            avLoadingIndicatorViewJapanese.setVisibility(View.INVISIBLE);

            // để translate cần thay đổi text của description ( Text watcher )
            // dùng để translate lần 2 : en-vi (text meaning)
            txtResultDescription.setText("");
            CurrentFragmentUseTranslatePresentationImpl.CURRENT_LANGUAGE_PAIR = 4; // 4: en-vi
            txtResultDescription.setText(myCurrentResultDescription);

        } else {
            txtMeaning.setText(message);
            avLoadingIndicatorViewMeaning.setVisibility(View.INVISIBLE);

            // đặt lại là 2 để translate : en-ja như ban đầu
            CurrentFragmentUseTranslatePresentationImpl.CURRENT_LANGUAGE_PAIR = 2;
        }
    }

    @Override
    public void setLanguages() {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    @Override
    public void showDialog(String message) {

    }

    @Override
    public int getFromSpinnerPosition() {
        return 0;
    }

    @Override
    public int getToSpinnerPosition() {
        return 0;
    }

    @Override
    public void setFromSpinnerSelection(int toSpinnerPosition) {

    }

    @Override
    public void setToSpinnerPosition(int tmpPosition) {

    }

    @Override
    public void invalidateSpinnerView() {

    }

    @Override
    public void swapText() {

    }

    @Override
    public void clearText() {

    }

    @Override
    public String getOriginal() {
        return null;
    }

    private TranslateComponent buildTranslateComponent() {
        return DaggerTranslateComponent.builder()
                .appComponent(App.getAppComponent())
                .translateModule(new TranslateModule(this))
                .build();
    }

    //----------------------------------------------------------------------------------------------


}
