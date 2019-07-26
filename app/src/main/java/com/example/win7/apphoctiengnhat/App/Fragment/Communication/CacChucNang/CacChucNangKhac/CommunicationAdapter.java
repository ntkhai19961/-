package com.example.win7.apphoctiengnhat.App.Fragment.Communication.CacChucNang.CacChucNangKhac;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.win7.apphoctiengnhat.DocDuLieuSQLite.Database;
import com.example.win7.apphoctiengnhat.R;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.ArrayList;

/**
 * Created by ntkhai1996 on 29-Apr-18.
 */

public class CommunicationAdapter extends RecyclerView.Adapter<CommunicationAdapter.ViewHolder> {

    private Activity mContext;
    private ArrayList<ClassChucNangCustomAdapter> arrayChucNang;

    public CommunicationAdapter(Activity mContext, ArrayList<ClassChucNangCustomAdapter> arrayChucNang) {
        this.mContext = mContext;
        this.arrayChucNang = arrayChucNang;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.iten_rcv_communication_fragment, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtYNghia.setText(arrayChucNang.get(position).getYNghia());

        setupShineButton(holder, position);

        setupExpandableView(holder);

        holder.txtRomanji.setText(arrayChucNang.get(position).getRomanji());
        holder.txtTiengNhat.setText(arrayChucNang.get(position).getTiengNhat());
    }

    private void setupShineButton(ViewHolder holder, int position) {

        if(arrayChucNang.get(position).getYeuThich() == 0)
            holder.sbtnLike.setChecked(false);
        else
            holder.sbtnLike.setChecked(true);

        holder.sbtnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shineButtonOnClick(holder, position);
            }
        });
    }

    private void shineButtonOnClick(ViewHolder holder, int position) {
        if(holder.sbtnLike.isChecked()){

            Log.e("postion - true",position + " " + holder.sbtnLike.isChecked());

            arrayChucNang.get(position).setYeuThich(1);

            // update Yêu thích trong Database
            ContentValues contentValues = new ContentValues();
            contentValues.put("YeuThich" , arrayChucNang.get(position).getYeuThich());

            SQLiteDatabase database = Database.initDatabase(mContext , "apphoctiengnhat.sqlite");
            database.update("ChucNang" , contentValues , "id = ?" , new String[]{arrayChucNang.get(position).getId() +"" });

        } else {

            Log.e("postion - false",position + " " + holder.sbtnLike.isChecked());

            arrayChucNang.get(position).setYeuThich(0);

            // update Yêu thích trong Database
            ContentValues contentValues = new ContentValues();
            contentValues.put("YeuThich" , arrayChucNang.get(position).getYeuThich());

            SQLiteDatabase database = Database.initDatabase(mContext , "apphoctiengnhat.sqlite");
            database.update("ChucNang" , contentValues , "id = ?" , new String[]{arrayChucNang.get(position).getId() +"" });
        }
    }

    private void setupExpandableView(ViewHolder holder) {
        holder.setIsRecyclable(false);
        holder.expandableCommunication.setInRecyclerView(true);
        holder.expandableCommunication.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        holder.expandableCommunication.setDuration(200);

        holder.rltItemCommunication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.expandableCommunication.setVisibility(View.VISIBLE);
                holder.expandableCommunication.toggle();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayChucNang.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtYNghia;
        private TextView txtTiengNhat;
        private TextView txtRomanji;
        private RelativeLayout rltItemCommunication;
        private ShineButton sbtnLike;
        private ExpandableLinearLayout expandableCommunication;

        ViewHolder(View itemView) {
            super(itemView);

            initView(itemView);
        }

        private void initView(View itemView) {
            txtYNghia    = itemView.findViewById(R.id.txtYNghiaChucNangFragment_CoBan);
            txtRomanji   = itemView.findViewById(R.id.txtRomanji);
            txtTiengNhat = itemView.findViewById(R.id.txtTiengNhat);
            sbtnLike     = itemView.findViewById(R.id.sbtnLike);
            expandableCommunication = itemView.findViewById(R.id.expandableCommunication);
            rltItemCommunication = itemView.findViewById(R.id.rltItemCommunication);

            Typeface typeface = Typeface.createFromAsset(mContext.getAssets(),"fonts/NABILA.TTF");
            txtYNghia.setTypeface(typeface);
        }
    }
}
