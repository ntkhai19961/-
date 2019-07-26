package com.example.win7.apphoctiengnhat.App.Fragment.Communication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;

import com.example.win7.apphoctiengnhat.App.Fragment.Communication.Searching.Searching;
import com.example.win7.apphoctiengnhat.R;
import com.example.win7.apphoctiengnhat.App.Fragment.Communication.Like.LikeActivity;

import java.util.ArrayList;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by ntkhai1996 on 18-Nov-17.
 */

public class FragmentCoBan extends Fragment implements ScreenShotable{

    private RecyclerView rcvCommunication;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_communication, container, false);

        initView(rootView);
        ThemChucNangChoGridView();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Communication");
        }
    }

    private void initView(View view) {
        rcvCommunication = view.findViewById(R.id.rcvCommunication);
        setAnimation();
    }

    private void setAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.anim_fade_out_slide_up);

        //gridViewFragment_CoBan.setAnimation(animation);
    }

    private void ThemChucNangChoGridView() {
        ArrayList<ChucNang> arrayChucNang = new ArrayList<>();
        arrayChucNang.add(new ChucNang("Search",R.drawable.ic_search_black_24dp));
        arrayChucNang.add(new ChucNang("Like"  ,R.drawable.ic_star_black_24dp));
        //arrayChucNang.add(new ChucNang("Hiragana",R.drawable.hiragana));
        //arrayChucNang.add(new ChucNang("Katakana",R.drawable.katakana));
        arrayChucNang.add(new ChucNang("Greeting"    ,R.drawable.shake_hand  ,"ChaoHoi"));
        arrayChucNang.add(new ChucNang("Conversation",R.drawable.conversation,"HoiThoaiThuongDung"));
        arrayChucNang.add(new ChucNang("Number"      ,R.drawable.number      ,"So"));
        arrayChucNang.add(new ChucNang("DateTime"    ,R.drawable.datetime    ,"NgayGio"));

        arrayChucNang.add(new ChucNang("Direction&Place",R.drawable.map     ,"PhuongHuongDiaDiem"));
        arrayChucNang.add(new ChucNang("Vehicle"        ,R.drawable.car     ,"PhuongTienDiLai"));
        arrayChucNang.add(new ChucNang("Distress Place" ,R.drawable.vacation,"DiaDiemNghiNgoi"));
        arrayChucNang.add(new ChucNang("Eating"         ,R.drawable.eat     ,"AnUong"));
        arrayChucNang.add(new ChucNang("Shopping"       ,R.drawable.shopping,"MuaSam"));
        arrayChucNang.add(new ChucNang("Color"          ,R.drawable.color   ,"MauSac"));
        arrayChucNang.add(new ChucNang("City & township",R.drawable.city    ,"ThanhPhoTinh"));


        rcvCommunication.setLayoutManager(new GridLayoutManager(getContext(),2));
        FragmentCommunicationAdapter adapter = new FragmentCommunicationAdapter(getContext(), arrayChucNang);
        rcvCommunication.setAdapter(adapter);
}


    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }
}
