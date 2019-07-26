package com.example.win7.apphoctiengnhat.App.Fragment.Communication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.win7.apphoctiengnhat.App.Fragment.Communication.Like.LikeActivity;
import com.example.win7.apphoctiengnhat.App.Fragment.Communication.Searching.Searching;
import com.example.win7.apphoctiengnhat.R;

import java.util.ArrayList;

/**
 * Created by ntkhai1996 on 07-May-18.
 */

public class FragmentCommunicationAdapter extends RecyclerView.Adapter<FragmentCommunicationAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<ChucNang> arrayChucNang;

    public FragmentCommunicationAdapter(Context mContext, ArrayList<ChucNang> arrayChucNang) {
        this.mContext = mContext;
        this.arrayChucNang = arrayChucNang;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.item_communication,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtChucNang.setText(arrayChucNang.get(position).getTenChucNang());
        holder.imgViewChucNang.setImageResource(arrayChucNang.get(position).getAnhChucNang());

        setOnTouchCardView(holder, position);
    }

    private void setOnClickCardView(int position) {

        switch (position){
            case 0:
                mContext.startActivity(new Intent(mContext, Searching.class));
                break;
            case 1:
                mContext.startActivity(new Intent(mContext, LikeActivity.class));
                break;
            default:
                // not define in manifest this activity ???
                Intent intent = new Intent(mContext, com.example.win7.apphoctiengnhat.App.Fragment.Communication.CacChucNang.CacChucNangKhac.ChucNang.class);
                intent.putExtra("TenTableSQLiteFromFragment_CoBan",arrayChucNang.get(position).getTenTableSQLite());
                intent.putExtra("TenChucNangFromFragment_CoBan",arrayChucNang.get(position).getTenChucNang());
                mContext.startActivity(intent);
                break;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setOnTouchCardView(ViewHolder holder, int position) {

        View.OnTouchListener onTouch = new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        holder.cardViewItemCommunication.setCardBackgroundColor(ResourcesCompat.getColor(mContext.getResources(),R.color.colorPrimaryBackgroundOnTouch,null));
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        holder.cardViewItemCommunication.setCardBackgroundColor(ResourcesCompat.getColor(mContext.getResources(),R.color.colorPrimaryBackgroundOnTouch,null));
                        break;
                    case MotionEvent.ACTION_UP:
                        holder.cardViewItemCommunication.setCardBackgroundColor(ResourcesCompat.getColor(mContext.getResources(),R.color.colorPrimaryBackground,null));
                        setOnClickCardView(position);
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        holder.cardViewItemCommunication.setCardBackgroundColor(ResourcesCompat.getColor(mContext.getResources(),R.color.colorPrimaryBackground,null));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        holder.cardViewItemCommunication.setCardBackgroundColor(ResourcesCompat.getColor(mContext.getResources(),R.color.colorPrimaryBackground,null));
                        break;
                }
                return true;
            }
        };

        holder.cardViewItemCommunication.setOnTouchListener(onTouch);
    }

    @Override
    public int getItemCount() {
        return arrayChucNang.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtChucNang;
        private ImageView imgViewChucNang;
        private CardView cardViewItemCommunication;

        ViewHolder(View itemView) {
            super(itemView);

            imgViewChucNang           = itemView.findViewById(R.id.imageCardViewFragment_CoBan);
            txtChucNang               = itemView.findViewById(R.id.txtCardViewFragment_CoBan);
            cardViewItemCommunication = itemView.findViewById(R.id.cardViewItemCommunication);
        }
    }
}
