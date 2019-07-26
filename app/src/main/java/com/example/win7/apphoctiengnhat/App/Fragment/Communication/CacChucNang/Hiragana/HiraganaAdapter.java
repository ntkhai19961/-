package com.example.win7.apphoctiengnhat.App.Fragment.Communication.CacChucNang.Hiragana;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.win7.apphoctiengnhat.R;

import java.util.List;

/**
 * Created by WIN7 on 08-Jun-17.
 */

public class HiraganaAdapter extends BaseAdapter{

    Context myConText;
    int myLayout;
    List<ClassHiragana> arrayHiragana;

    public HiraganaAdapter(Context myConText, int myLayout, List<ClassHiragana> arrayHiragana) {
        this.myConText = myConText;
        this.myLayout = myLayout;
        this.arrayHiragana = arrayHiragana;
    }

    @Override
    public int getCount() {
        return arrayHiragana.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) myConText.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(myLayout,null);

        //Ánh Xạ Và Gán Giá Trị

        TextView txtHiragana = (TextView) convertView.findViewById(R.id.txtHiragana);
        txtHiragana.setText(arrayHiragana.get(position).Hiragana);

        TextView txtRomanjiHiragana = (TextView) convertView.findViewById(R.id.txtRomanjiHiragana);
        txtRomanjiHiragana.setText(arrayHiragana.get(position).RomanjiHiragana);

        return convertView;
    }
}
