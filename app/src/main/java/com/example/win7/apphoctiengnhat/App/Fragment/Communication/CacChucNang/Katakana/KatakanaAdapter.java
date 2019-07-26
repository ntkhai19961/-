package com.example.win7.apphoctiengnhat.App.Fragment.Communication.CacChucNang.Katakana;

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

public class KatakanaAdapter extends BaseAdapter {

    Context myConText;
    int myLayout;
    List<ClassKatakana> arrayKatakana;

    public KatakanaAdapter(Context myConText, int myLayout, List<ClassKatakana> arrayKatakana) {
        this.myConText = myConText;
        this.myLayout = myLayout;
        this.arrayKatakana = arrayKatakana;
    }

    @Override
    public int getCount() {
        return arrayKatakana.size();
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

        TextView txtKatakana = (TextView) convertView.findViewById(R.id.txtKatakana);
        txtKatakana.setText(arrayKatakana.get(position).Katakana);

        TextView txtRomanjiKatakana = (TextView) convertView.findViewById(R.id.txtRomanjiKatakana);
        txtRomanjiKatakana.setText(arrayKatakana.get(position).RomanjiKatakana);

        return convertView;
    }
}
