package com.example.win7.apphoctiengnhat.App.Activity.Kanji.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.win7.apphoctiengnhat.App.Activity.Kanji.model.ClassKanji;
import com.example.win7.apphoctiengnhat.Constant.UserInfo;
import com.example.win7.apphoctiengnhat.KanjiDetails.KanjiDetails;
import com.example.win7.apphoctiengnhat.R;

import java.util.ArrayList;

/**
 * Created by ntkhai1996 on 01-May-18.
 */

public class RecycleViewKanjiAdapter extends RecyclerView.Adapter<RecycleViewKanjiAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<ClassKanji> arrayKanji;
    private int numUnlockKanji;

    public RecycleViewKanjiAdapter(Context mContext, ArrayList<ClassKanji> arrayKanji) {
        this.mContext = mContext;
        this.arrayKanji = arrayKanji;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.item_kanji, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.txtKanji.setText(arrayKanji.get(position).getKanji());
        holder.txtPhienAmKanji.setText(arrayKanji.get(position).getOnyomi());
        holder.txtYNghiaKanji.setText(arrayKanji.get(position).getyNghia());

        checkUnlockKanji(holder, position);

        setOnTouchItem(holder, position);
    }

    private void setOnTouchItem(ViewHolder holder, int position) {
        View.OnTouchListener onTouch = new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        holder.rltItemKanji.setBackgroundColor(ResourcesCompat.getColor(mContext.getResources(),R.color.light_transparent,null));
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        holder.rltItemKanji.setBackgroundColor(ResourcesCompat.getColor(mContext.getResources(),R.color.light_transparent,null));
                        break;
                    case MotionEvent.ACTION_UP:
                        holder.rltItemKanji.setBackgroundResource(0);
                        startKanjiDetailActivity(position);
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        holder.rltItemKanji.setBackgroundResource(0);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        holder.rltItemKanji.setBackgroundResource(0);
                        break;
                }
                return true;
            }
        };

        holder.rltItemKanji.setOnTouchListener(onTouch);
    }

    private void startKanjiDetailActivity(int position) {
        if(arrayKanji.get(position).getId() <= numUnlockKanji){
            Intent Kanji_ChiTietKanji_Intent = new Intent(mContext, KanjiDetails.class);
            Kanji_ChiTietKanji_Intent.putExtra("id"     ,arrayKanji.get(position).getId());
            Kanji_ChiTietKanji_Intent.putExtra("Kanji"  ,arrayKanji.get(position).getKanji());
            Kanji_ChiTietKanji_Intent.putExtra("HanTu"  ,arrayKanji.get(position).getHanTu());
            Kanji_ChiTietKanji_Intent.putExtra("YNghia" ,arrayKanji.get(position).getyNghia());
            Kanji_ChiTietKanji_Intent.putExtra("Vidu"   ,arrayKanji.get(position).getViDu());
            Kanji_ChiTietKanji_Intent.putExtra("Level"  ,arrayKanji.get(position).getCapDo());
            Kanji_ChiTietKanji_Intent.putExtra("Onyomi" ,arrayKanji.get(position).getOnyomi());
            Kanji_ChiTietKanji_Intent.putExtra("Kunyomi",arrayKanji.get(position).getKunyomi());
            Kanji_ChiTietKanji_Intent.putExtra("KanjiVGFileName",arrayKanji.get(position).getKanjiVGFileName());
            mContext.startActivity(Kanji_ChiTietKanji_Intent);
        } else {
            Toast.makeText(mContext, "You haven't unlocked this kanji yet!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkUnlockKanji(ViewHolder holder, int position) {

        getUserLevel();

        if(arrayKanji.get(position).getId() <= numUnlockKanji)
            holder.item_kanji_status.setBackgroundResource(0);
        else
            holder.item_kanji_status.setBackgroundResource(R.drawable.pattern_diagonal_xml);
    }

    private void getUserLevel() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(UserInfo.SHARED_REFERENCES_NAME, Context.MODE_PRIVATE);
        numUnlockKanji = sharedPreferences.getInt(UserInfo.KEY_UNLOCK_KANJI,0);
    }

    @Override
    public int getItemCount() {
        return arrayKanji.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtKanji;
        private TextView txtPhienAmKanji;
        private TextView txtYNghiaKanji;
        private View item_kanji_status;
        private RelativeLayout rltItemKanji;

        ViewHolder(View itemView) {
            super(itemView);
            txtKanji          = itemView.findViewById(R.id.item_kanji_character);
            txtPhienAmKanji   = itemView.findViewById(R.id.item_kanji_reading);
            txtYNghiaKanji    = itemView.findViewById(R.id.item_kanji_meaning);
            item_kanji_status = itemView.findViewById(R.id.item_kanji_status);
            rltItemKanji      = itemView.findViewById(R.id.rltItemKanji);
        }
    }
}
