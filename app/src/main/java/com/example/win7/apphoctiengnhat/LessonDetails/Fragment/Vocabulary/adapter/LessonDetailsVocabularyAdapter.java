package com.example.win7.apphoctiengnhat.LessonDetails.Fragment.Vocabulary.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.example.win7.apphoctiengnhat.LessonDetails.Fragment.Vocabulary.model.ClassLessonDetailsVocabulary;
import com.example.win7.apphoctiengnhat.VocabularyDetails.VocabularyDetailsActivity;
import com.example.win7.apphoctiengnhat.R;

import java.util.ArrayList;

/**
 * Created by ntkhai1996 on 19-Dec-17.
 */

public class LessonDetailsVocabularyAdapter extends RecyclerView.Adapter<LessonDetailsVocabularyAdapter.ViewHolder> implements View.OnClickListener{

    private Context mContext;
    private ArrayList<ClassLessonDetailsVocabulary> arrayVocabulary;

    public LessonDetailsVocabularyAdapter(Context mContext, ArrayList<ClassLessonDetailsVocabulary> arrayVocabulary) {
        this.mContext = mContext;
        this.arrayVocabulary = arrayVocabulary;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.item_lesson_details_vocabulary,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.item_vocabulary_character.setText(arrayVocabulary.get(position).getCharacter());
        holder.item_vocabulary_reading.setText(arrayVocabulary.get(position).getReading());
        holder.item_vocabulary_meaning.setText(arrayVocabulary.get(position).getMeaning());
        holder.item_lesson_details_vocabulary_card.setOnClickListener(this);

        if(position == 0)
            holder.item_lesson_details_vocabulary_card.setBackgroundResource(R.drawable.border_item_first_vocabulary_lesson);
        if(position == arrayVocabulary.size() - 1){
            holder.item_lesson_details_vocabulary_card.setBackgroundResource(R.drawable.border_item_last_vocabulary_lesson);
            holder.item_vocabulary_divider.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return arrayVocabulary.size();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.item_lesson_details_vocabulary_card:
                Intent intent = new Intent(mContext, VocabularyDetailsActivity.class);
                mContext.startActivity(intent);
                break;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView item_vocabulary_character;
        private TextView item_vocabulary_reading;
        private TextView item_vocabulary_meaning;
        private FrameLayout item_lesson_details_vocabulary_card;
        private View item_vocabulary_divider;

        ViewHolder(View itemView) {
            super(itemView);

            item_vocabulary_character = itemView.findViewById(R.id.item_vocabulary_character);
            item_vocabulary_reading   = itemView.findViewById(R.id.item_vocabulary_reading);
            item_vocabulary_meaning   = itemView.findViewById(R.id.item_vocabulary_meaning);
            item_lesson_details_vocabulary_card = itemView.findViewById(R.id.item_lesson_details_vocabulary_card);
            item_vocabulary_divider   = itemView.findViewById(R.id.item_vocabulary_divider);
        }
    }
}
