package com.example.win7.apphoctiengnhat.App.Fragment.Recognition.TextRecognition;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenu;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


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
import com.wang.avi.AVLoadingIndicatorView;

import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.inject.Inject;

import io.github.yavski.fabspeeddial.FabSpeedDial;


public class NhanDienChuCai extends Fragment implements TranslateContract.View{

    private static final String TAG = NhanDienChuCai.class.getSimpleName();

    final private int REQUEST_TAKE_PHOTO = 1024;
    final private int REQUEST_CHOOSE_PHOTO = 1023;
    final private int MY_CAMERA_REQUEST_CODE = 100;
    private TextView txtChuCaiNhanDienQuaCAMERA;
    private TextView txtMeaning;
    private TextView txtIsThereAnyPicture;
    private TextView txtEnglish;
    private ImageView imgHinhAnhChupQuaCAMERA;
    private FabSpeedDial fabSpeedButton_Text_Recognition;
    private AVLoadingIndicatorView avLoadingIndicatorViewMeaning;
    private AVLoadingIndicatorView avLoadingIndicatorViewEnglish;
    private Bitmap myCurrentImageBitMap;
    private String myCurrentResultRecognition = "";
    OcrManager manager;


    private static TranslateComponent mTranslateComponent;
    @Inject
    TranslatePresenterImpl mPresenter;
    @Inject
    DebounceTextWatcher mDebounceTextWatcher;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_text_recogniton, container, false);

        initView(rootView);

        // no error, init api ok.
        // how to using it roconignize text
        manager = new OcrManager();
        manager.initAPI();

        checkPermission();
        AddEventFabButon();

        configTranslate();

        return rootView;
    }

    private void configTranslate(){

        // build để biết View nào để translatePresenterImpl biết mà return kết quả translate về khi text view phía dưới thay đổi
        mTranslateComponent = buildTranslateComponent();
        mTranslateComponent.inject(this);

        txtChuCaiNhanDienQuaCAMERA.setOnEditorActionListener(new DoneOnEditorActionListener());
        txtChuCaiNhanDienQuaCAMERA.addTextChangedListener(mDebounceTextWatcher);
    }

    private void initView(View rootview) {

        txtChuCaiNhanDienQuaCAMERA = rootview.findViewById(R.id.txtChuCaiNhanDienQuaCAMERA);
        txtMeaning = rootview.findViewById(R.id.txtMeaning_FragmentTextRecognition);
        txtIsThereAnyPicture = rootview.findViewById(R.id.isThereAnyPicture_FragmentTextRecognition);
        txtEnglish = rootview.findViewById(R.id.txtEnglish_FragmentTextRecognition);
        imgHinhAnhChupQuaCAMERA = rootview.findViewById(R.id.imgHinhAnhChupQuaCAMERA);
        fabSpeedButton_Text_Recognition = rootview.findViewById(R.id.fabSpeedButton_Text_Recognition);
        avLoadingIndicatorViewMeaning = rootview.findViewById(R.id.AVLoadingIndicatorView_Translate_FragmentTextRecognition);
        avLoadingIndicatorViewEnglish = rootview.findViewById(R.id.AVLoadingIndicatorView_English_FragmentTextRecognition);
    }

    private void ChoosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,REQUEST_CHOOSE_PHOTO);
    }

    private void AddEventFabButon() {

        fabSpeedButton_Text_Recognition.setMenuListener(new FabSpeedDial.MenuListener() {
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

        switch (menuItemTitle) {
            case "Take a picture":
                checkPermission();
                startCameraActivity();
                break;
            case "Choose a picture":
                ChoosePhoto();
                break;
        }
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
    }

    private void startCameraActivity(){
        try{
            // Chuyển Qua CAMERA
            final Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(pictureIntent, REQUEST_TAKE_PHOTO);

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == Activity.RESULT_OK) {
            avLoadingIndicatorViewEnglish.setVisibility(View.VISIBLE);

            if(requestCode == REQUEST_TAKE_PHOTO) {
                try {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    txtChuCaiNhanDienQuaCAMERA.setText(manager.startRecognize(bitmap));
                    imgHinhAnhChupQuaCAMERA.setImageBitmap(bitmap);

                    // kiểm tra xem hình có thay đổi để có setText thành "" hay không
                    if(myCurrentImageBitMap == null){
                        myCurrentImageBitMap = bitmap;
                        avLoadingIndicatorViewMeaning.setVisibility(View.VISIBLE);

                    } else if(!myCurrentImageBitMap.sameAs(bitmap)){
                        myCurrentImageBitMap = bitmap;
                        txtMeaning.setText("");
                        txtEnglish.setText("");
                        avLoadingIndicatorViewMeaning.setVisibility(View.VISIBLE);
                    }


                } catch(Exception e) {
                    Toast.makeText(getContext(),"Vui Lòng Chụp Hình Muốn Nhận Diện",Toast.LENGTH_SHORT).show();
                }

            } else if(requestCode == REQUEST_CHOOSE_PHOTO) {
                try {
                    Uri imageUri = data.getData();
                    InputStream is = getActivity().getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    txtChuCaiNhanDienQuaCAMERA.setText(manager.startRecognize(bitmap));
                    imgHinhAnhChupQuaCAMERA.setImageBitmap(bitmap);

                    // kiểm tra xem hình có thay đổi để có setText thành "" hay không
                    if(myCurrentImageBitMap == null){
                        myCurrentImageBitMap = bitmap;
                        avLoadingIndicatorViewMeaning.setVisibility(View.VISIBLE);

                    } else if(!myCurrentImageBitMap.sameAs(bitmap)){
                        myCurrentImageBitMap = bitmap;
                        txtMeaning.setText("");
                        txtEnglish.setText("");
                        avLoadingIndicatorViewMeaning.setVisibility(View.VISIBLE);
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }

        txtIsThereAnyPicture.setText("");
    }

    //----------------------------------------------------------------------------------------------
    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public void showTranslateResult(String message) {
//        txtTranslate.setText(message);
//        avLoadingIndicatorViewMeaning.setVisibility(View.INVISIBLE);

        // dùng 1 biến phụ "myCurrentResultDescription" kiểm tra sự thay đổi của text description
        // lần translate thứ 1 : là từ ja-en (text English)
        // lần translate thứ 2 : là từ ja-vi (text Meaning)
        // do khi translate nhiều lần , hàm này sẽ tái sử dụng nhiều lần
        // kiểm tra như vậy để biết được đang cần translate language pair nào
        if(!myCurrentResultRecognition.equals(txtChuCaiNhanDienQuaCAMERA.getText().toString())){

            myCurrentResultRecognition = txtChuCaiNhanDienQuaCAMERA.getText().toString();

            txtEnglish.setText(message);
            avLoadingIndicatorViewEnglish.setVisibility(View.INVISIBLE);

            // để translate cần thay đổi text của txtChuCaiNhanDienQuaCAMERA ( Text watcher )
            // dùng để translate lần 2 : ja-vi (text meaning)
            txtChuCaiNhanDienQuaCAMERA.setText("");
            CurrentFragmentUseTranslatePresentationImpl.CURRENT_LANGUAGE_PAIR = 1; // 1: ja-vi
            txtChuCaiNhanDienQuaCAMERA.setText(myCurrentResultRecognition);

        } else {
            txtMeaning.setText(message);
            avLoadingIndicatorViewMeaning.setVisibility(View.INVISIBLE);

            // đặt lại là 5 để translate : ja-en như ban đầu
            CurrentFragmentUseTranslatePresentationImpl.CURRENT_LANGUAGE_PAIR = 5;
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
