package com.example.win7.apphoctiengnhat.App.Fragment.KizunaAi.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.win7.apphoctiengnhat.App.Fragment.KizunaAi.Model.ChatModel;
import com.example.win7.apphoctiengnhat.CustomView.CustomLineChat;
import com.example.win7.apphoctiengnhat.R;

import java.util.ArrayList;

/**
 * Created by ntkhai1996 on 04-Jan-18.
 */

public class KizunaAiChatAdapter extends RecyclerView.Adapter<KizunaAiChatAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<ChatModel> arrayList;

    public KizunaAiChatAdapter(Context mContext, ArrayList<ChatModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_kazuna_ai, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.txtChatSend   .setText(arrayList.get(position).getChatSend());
        holder.txtChatReceive.setText(arrayList.get(position).getChatReceive());

        setLineChatSendColor(holder, position);
        setLineChatReceiveColor(holder, position);
        setAnimation(holder, position);
    }

    private void setAnimation(ViewHolder holder, int position) {
        if(arrayList.get(position).isNotSetAnimation()){
            arrayList.get(position).isNotSetAnimation(false);

            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_scale_out);
            holder.lltChat.startAnimation(animation);
        }
    }

    private void setLineChatReceiveColor(ViewHolder holder, int position) {

        /*
         * Line KAZUNA AI, 2 trường hợp
         * 1: ở cuối mảng
         * 2: không phải trường hợp 1
         * => chỉ quan tâm line below, line above màu không thay đổi (màu gradient đã set sẵn trong canvas)
         */
        if(position == arrayList.size() - 1)
            holder.lineChatReceive.setColorLineBelow(ResourcesCompat.getColor(mContext.getResources(), R.color.colorPrimaryDarkBackground, null));
        else
            holder.lineChatReceive.setColorLineBelow(ResourcesCompat.getColor(mContext.getResources(), R.color.KazunaAiChat_not_send, null));
    }

    private void setLineChatSendColor(ViewHolder holder, int position) {

        //Log.e("position",position+"");
        /*
         * Line USER, 2 trường hợp
         * 1: ở đầu mảng
         * 2: không phải trường hợp 1
         * => chỉ quan tâm đến line above, line below màu không thay đổi
         */
        if(position == 0)
            holder.lineChatSend.setColorLineAbove(ResourcesCompat.getColor(mContext.getResources(), R.color.colorPrimaryDarkBackground, null));
        else {
            int color1 = ResourcesCompat.getColor(mContext.getResources(), R.color.KazunaAiChat_not_send, null);
            int color2 = ResourcesCompat.getColor(mContext.getResources(), R.color.KazunaAiChat_send, null);
            holder.lineChatSend.setGradientColorLineAbove(color1, color2);
        }
    }

    @Override
    public int getItemCount() { return arrayList.size(); }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtChatSend, txtChatReceive;
        private CustomLineChat lineChatSend, lineChatReceive;
        private LinearLayout lltChat;

        ViewHolder(View itemView) {
            super(itemView);
            txtChatSend      = itemView.findViewById(R.id.txtChatSend);
            txtChatReceive   = itemView.findViewById(R.id.txtChatReceive);
            lineChatSend     = itemView.findViewById(R.id.lineChatSend);
            lineChatReceive  = itemView.findViewById(R.id.lineChatReceive);
            lltChat   = itemView.findViewById(R.id.lltChat);
        }
    }
}
