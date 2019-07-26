package com.example.win7.apphoctiengnhat.Lesson.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.battleent.ribbonviews.RibbonLayout;
import com.example.win7.apphoctiengnhat.Lesson.ChildLessonActivity;
import com.example.win7.apphoctiengnhat.Lesson.Model.ItemLesson;
import com.example.win7.apphoctiengnhat.LessonDetails.Activity.LessonDetailsActivity;
import com.example.win7.apphoctiengnhat.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.supercharge.shimmerlayout.ShimmerLayout;

/**
 * Created by ntkhai1996 on 02-Dec-17.
 */

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.CustomViewHolder>{

    private Context context;
    private List<ItemLesson> itemLessonList;

    public LessonAdapter(Context context, List<ItemLesson> itemLessonList) {
        this.context = context;
        this.itemLessonList = itemLessonList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_lesson , parent , false);

        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        ItemLesson itemLesson = itemLessonList.get(position);

        holder.shimmer_layout_item_lesson.setShimmerAngle(10);
        holder.shimmer_layout_item_lesson.setShimmerColor(ResourcesCompat.getColor(context.getResources(),R.color.colorRowAakiraExpandable,null));
        holder.shimmer_layout_item_lesson.startShimmerAnimation();

        // We have: 0 = AVAILABLE type ; 1 = LOCK type
        if(itemLesson.getType() == 0) // AVAILABLE
        {
            holder.ribbonLayout.setShowBottom(true);
            holder.ribbonLayout.setShowHeader(true);

            holder.ribbonLayout.setHeaderRibbonColor(Color.parseColor("#2B323A"));
            holder.ribbonLayout.setHeaderTextColor(Color.parseColor("#FFFFFF"));

            holder.ribbonLayout.setHeaderText(itemLesson.getHeaderText());
            holder.ribbonLayout.setBottomText(itemLesson.getBottomText());

            setImage(itemLesson.getImage() , holder);

            //holder.cardViewLessonItemOnClick(itemLesson.getType() , itemLesson.getBottomText());
            holder.setOnTouchItem(itemLesson.getType() , itemLesson.getBottomText());
        }
        else if(itemLesson.getType() == 1) // LOCK
        {
            holder.ribbonLayout.setShowBottom(true);
            holder.ribbonLayout.setShowHeader(true);

            holder.ribbonLayout.setHeaderRibbonColor(Color.parseColor("#B94948"));
            holder.ribbonLayout.setHeaderTextColor(Color.parseColor("#FFFFFF"));

            holder.ribbonLayout.setHeaderText(itemLesson.getHeaderText());
            holder.ribbonLayout.setBottomText(itemLesson.getBottomText());

            setImage(itemLesson.getImage() , holder);

            //holder.cardViewLessonItemOnClick(itemLesson.getType() , itemLesson.getBottomText());
            holder.setOnTouchItem(itemLesson.getType() , itemLesson.getBottomText());
        }
    }

    private void setImage(String imageName , CustomViewHolder holder) {

        Picasso.with(context)
                .load(imageName)
                .placeholder(android.R.color.darker_gray)
                .config(Bitmap.Config.RGB_565)
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.imageView.setVisibility(View.VISIBLE);
                        holder.shimmer_layout_item_lesson.stopShimmerAnimation();
                        holder.shimmer_layout_item_lesson.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return itemLessonList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{

        private Context context;
        private RibbonLayout ribbonLayout;
        private ImageView imageView;
        private ShimmerLayout shimmer_layout_item_lesson;
        private View viewItemLesson;

        CustomViewHolder(View itemView) {
            super(itemView);

            context = itemView.getContext();

            ribbonLayout               = itemView.findViewById(R.id.ribbonLayoutLesson);
            imageView                  = itemView.findViewById(R.id.imageViewLesson);
            shimmer_layout_item_lesson = itemView.findViewById(R.id.shimmer_layout_item_lesson);
            viewItemLesson             = itemView.findViewById(R.id.viewItemLesson);
        }

        void startChildLessonActivity(int lessonType , String lessonName) {

            // 0 : Available
            // 1 : Locked
            if(lessonType == 0) {
                Intent intent = new Intent(context, ChildLessonActivity.class);
                intent.putExtra("lessonName",lessonName);
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "This lesson is locked !!! ", Toast.LENGTH_SHORT).show();
            }
        }

        void setOnTouchItem(int lessonType , String lessonName){
            View.OnTouchListener onTouch = new View.OnTouchListener() {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getActionMasked()){
                        case MotionEvent.ACTION_DOWN:
                            viewItemLesson.setBackgroundColor(ResourcesCompat.getColor(context.getResources(),R.color.dark_transparent,null));
                            break;
                        case MotionEvent.ACTION_POINTER_DOWN:
                            viewItemLesson.setBackgroundColor(ResourcesCompat.getColor(context.getResources(),R.color.dark_transparent,null));
                            break;
                        case MotionEvent.ACTION_UP:
                            viewItemLesson.setBackgroundResource(0);
                            startChildLessonActivity(lessonType, lessonName);
                            break;
                        case MotionEvent.ACTION_POINTER_UP:
                            viewItemLesson.setBackgroundResource(0);
                            break;
                        case MotionEvent.ACTION_CANCEL:
                            viewItemLesson.setBackgroundResource(0);
                            break;
                    }
                    return true;
                }
            };
            viewItemLesson.setOnTouchListener(onTouch);
        }
    }
}
