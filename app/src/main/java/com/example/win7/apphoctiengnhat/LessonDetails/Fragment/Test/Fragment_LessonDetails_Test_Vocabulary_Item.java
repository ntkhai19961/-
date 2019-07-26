package com.example.win7.apphoctiengnhat.LessonDetails.Fragment.Test;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.win7.apphoctiengnhat.Dialog.LessonTest.GoToGrammarTest.DialogGoToGrammarTest;
import com.example.win7.apphoctiengnhat.LessonDetails.Fragment.Test.model.VocabularyQuest;
import com.example.win7.apphoctiengnhat.R;
import com.example.win7.apphoctiengnhat.interfaces.Dialog_Lesson_Test_Go_Grammar_Test_Interface;
import com.example.win7.apphoctiengnhat.interfaces.Fragment_Lesson_Test_Item_Interface;
import com.github.florent37.viewtooltip.ViewTooltip;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import static com.github.florent37.viewtooltip.ViewTooltip.ALIGN.CENTER;

/**
 * Created by ntkhai1996 on 23-Apr-18.
 */

public class Fragment_LessonDetails_Test_Vocabulary_Item extends Fragment implements View.OnClickListener, Dialog_Lesson_Test_Go_Grammar_Test_Interface{

    private ImageView imgVocabularyQuest;
    private ImageButton imgBtnTip;
    private ProgressBar progressBar_VocabularyTest;
    private VocabularyQuest quest;
    private LinearLayout lltBtnContainer, lltAnswerContainer;
    private LinearLayout firstRow, newRow, firstRowInput, newRowAnswerInput;
    private ImageButton imgBtnNextQuest, imgBtnPrevQuest, imgBtnRefresh;
    private Fragment_Lesson_Test_Item_Interface listener;
    private DialogGoToGrammarTest dialog;
    private boolean isRightAnswer;
    private int idQuest;
    private int numQuest;
    private final String TAG = getClass().getName();


    // 12: (arrayCharQuest): tổng char trả lời (chưa tới 12) và char random
    // 12: (textview)      : tùy câu trả lời có 3,4,5... ô textview. Max là 12
    private char arrayCharQuest[]    = new char[12];
    private TextView[] arrayTextView = new TextView[20];
    private Button[]   arrayButton   = new Button[12];
    private Boolean[]  isFadeOut     = new Boolean[12];

    // Do trong quá trình tạo textview và button , id bắt đầu từ 1
    private int currentInputTextView = 1;

    public static Fragment_LessonDetails_Test_Vocabulary_Item newInstance(VocabularyQuest quest, int idQuest, int numQuest) {

        Fragment_LessonDetails_Test_Vocabulary_Item myFragment = new Fragment_LessonDetails_Test_Vocabulary_Item();

        Bundle bundle  = new Bundle();
        bundle.putSerializable("quest",quest);
        bundle.putInt("idQuest",idQuest);
        bundle.putInt("numQuest",numQuest);
        myFragment.setArguments(bundle);

        return myFragment;
    }

    public void setListener(Fragment_Lesson_Test_Item_Interface listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getQuest();
    }

    private void getQuest() {
        try {
            quest = (VocabularyQuest) Objects.requireNonNull(getArguments()).getSerializable("quest");
            idQuest = getArguments().getInt("idQuest");
            numQuest = getArguments().getInt("numQuest");
        }
        catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_lesson_detail_test_vocabulary,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        setImage(quest.getImage());

        createView();
    }

    private void resetButton() {

        int size = arrayButton.length;

        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.anim_scale_out_fade_out);

        for (int i = 0 ; i < size ; i++){
            if(isFadeOut[i]){
                arrayButton[i].setVisibility(View.VISIBLE);
                arrayButton[i].startAnimation(animation);
                isFadeOut[i] = false;
            }
        }
    }

    private void onNextQuest() {

        int length = quest.getAnswer().length();

        StringBuilder sb = new StringBuilder();

        for(int i = 1 ; i <= length ; i++)
            if(arrayTextView[i] != null)
                sb.append(arrayTextView[i].getText().toString());

        if(isThereAnyEmptyInput(length))
            pleaseFillTheAnswer();

        else if(listener != null){

            // câu cuối => hỏi trước khi qua grammar test
            if(idQuest == numQuest - 1){
                if(quest.getAnswer().equals(sb.toString())) {
                    //dialog(getContext(),true).show();
                    isRightAnswer = true;
                    dialog.show(getChildFragmentManager(), "");
                } else {
                    //dialog(getContext(),false).show();
                    isRightAnswer = false;
                    dialog.show(getChildFragmentManager(), "");
                }
            }
            // không phải câu cuối
            else if(quest.getAnswer().equals(sb.toString()))
                listener.onNextQuest(idQuest,true);
            else
                listener.onNextQuest(idQuest,false);
        }
    }

//    private AlertDialog.Builder dialog(Context c, boolean isRightAnswer) {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(c);
//        builder.setTitle("Are you sure??");
//        builder.setMessage("We will go to Grammar Quest. Are you sure with these Vocabulary quest??");
//
//        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                listener.onNextQuest(idQuest,isRightAnswer);
//            }
//        }).setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
//
//        return builder;
//    }

    private boolean isThereAnyEmptyInput(int length) {

        for(int i = 1 ; i <= length ; i++)
            if(arrayTextView[i] != null)
                if(arrayTextView[i].getText().toString().equals("")) {
                    //Log.e("i",i+"");
                    return true;
                }
        return false;
    }

    private void pleaseFillTheAnswer(){

        ViewTooltip
                .on(this, imgBtnNextQuest)
                .autoHide(true, 900)
                .clickToHide(true)
                .align(CENTER)
                .position(ViewTooltip.Position.LEFT)
                .text("Please fill the answer")
                .textSize(2, 20)
                .textColor(ResourcesCompat.getColor(this.getResources(), R.color.colorWhite, null))
                .color(ResourcesCompat.getColor(this.getResources(), R.color.colorPrimaryDark, null))
                .corner(10)
                .arrowWidth(15)
                .arrowHeight(15)
                .distanceWithView(0)
                .animation(new ViewTooltip.FadeTooltipAnimation(500))
                .show();
    }

    private void clearText() {

        // n = arrayQuest.size
        // check null: mảng 20 phần tử, nhưng chỉ có n phần tử dc khởi tạo
        for (TextView anArrayTextView : arrayTextView)
            if(anArrayTextView != null){

                Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.anim_scale_in_fade_in);

                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        anArrayTextView.setText("");
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                anArrayTextView.startAnimation(animation);
            }

        currentInputTextView = 1;
    }

    private void createView() {

        // B1: tạo mảng char gồm tổng 12 chữ cái (chữ trong câu trả lời và chữ random) xáo trộn
        createCharArray(quest.getAnswer(), quest.getAnswer().length());

        // B2: tạo số button tùy vào size mảng char (đây cố định 12)
        createFirstRow();
        createAllRandomButton();

        // B3: tạo số textview tùy vào size câu trả lời (câu trả lời 3 chữ cái => 3 textview)
        createFirstRowInput(quest.getAnswer().length());
        createAllInput(quest.getAnswer().length());
    }

    private void createAllInput(int answerLength) {

        newRowAnswerInput = null;

        Log.e("answerLength",answerLength+"");

        // weight sum = 10 => tối đa 5 ô là đẹp nhất
        // => nếu quá 5 ô thì tối đa 4 ô 1 hàng
        if(answerLength >= 6){

            int numInputOneRow;

            if(answerLength % 2 ==0)
                numInputOneRow = answerLength/2;
            else
                numInputOneRow = answerLength/2 + 1;

            if(numInputOneRow > 4)
                numInputOneRow = 4;

            for(int i = 1 ; i <= answerLength ; i++){
                // TH2: thêm 1 hàng mới
                if( (i-1) % numInputOneRow == 0 && i > numInputOneRow) {

                    createNewRowInput();

                    createAnswerInput(i,false);

                }
                // TH3: đang ở hàng mới (sau khi thêm hàng mới)
                else if( (i-1) % numInputOneRow != 0 && i > numInputOneRow )
                    createAnswerInput(i, false);

                    // TH1: hàng đầu tiên (mới vô)
                else if(i <= numInputOneRow)
                    createAnswerInput(i,true);
            }
        }
        else
            for(int i = 1 ; i <= answerLength ; i++)
                createAnswerInput(i,true);
    }

    private void createNewRowInput(){

        newRowAnswerInput = new LinearLayout(getContext());

        LinearLayout.LayoutParams paramNewRow = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1);

        newRowAnswerInput.setGravity(Gravity.CENTER);
        newRowAnswerInput.setLayoutParams(paramNewRow);
        newRowAnswerInput.setWeightSum(8);
        newRowAnswerInput.setOrientation(LinearLayout.HORIZONTAL);

        lltAnswerContainer.addView(newRowAnswerInput);
    }

    private void createAnswerInput(int id, boolean isFirstRow) {

        Log.e("id",id+"");

        //=================================================================
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                2);

        params.setMargins(10, 10, 10, 10);

        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(params);
        linearLayout.setBackground(ResourcesCompat.getDrawable(Objects.requireNonNull(getContext()).getResources(),R.drawable.border_answer_input,null));
        //=================================================================

        LinearLayout.LayoutParams paramTextView = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        paramTextView.gravity = Gravity.CENTER_VERTICAL;

        arrayTextView[id] = new TextView(getContext());
        arrayTextView[id].setLayoutParams(paramTextView);
        arrayTextView[id].setText("");
        arrayTextView[id].setTextSize(24f);
        arrayTextView[id].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        arrayTextView[id].setTextColor(ResourcesCompat.getColor(getContext().getResources(),R.color.colorWhite,null));

        linearLayout.addView(arrayTextView[id]);

        if(isFirstRow)
            firstRowInput.addView(linearLayout);
        else
            newRowAnswerInput.addView(linearLayout);
    }

    private void createFirstRowInput(int answerLength) {

        float weightSum;

        if(answerLength >= 6)
            weightSum = 8;
        else
            weightSum = 10;

        firstRowInput = new LinearLayout(getContext());

        LinearLayout.LayoutParams paramFirstRow = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1);

        firstRowInput.setGravity(Gravity.CENTER);
        firstRowInput.setLayoutParams(paramFirstRow);
        firstRowInput.setOrientation(LinearLayout.HORIZONTAL);
        firstRowInput.setWeightSum(weightSum);

        lltAnswerContainer.addView(firstRowInput);
    }

    private void createAllRandomButton() {

        newRow = null;

        final int MAX_CHAR_ONE_ROW = 4;

        for(int i = 1; i <= arrayCharQuest.length ; i++) {

            // TH2: thêm 1 hàng mới
            if( (i-1) % MAX_CHAR_ONE_ROW == 0 && i > MAX_CHAR_ONE_ROW) {

                createNewRow();

                createNewButton(i, String.valueOf(arrayCharQuest[i-1]), false);

            }
            // TH3: đang ở hàng mới (sau khi thêm hàng mới)
            else if( (i-1) % MAX_CHAR_ONE_ROW != 0 && i > MAX_CHAR_ONE_ROW )
                createNewButton(i, String.valueOf(arrayCharQuest[i-1]), false);

            // TH1: hàng đầu tiên (mới vô)
            else if(i <= MAX_CHAR_ONE_ROW)
                createNewButton(i, String.valueOf(arrayCharQuest[i-1]), true);
        }
    }

    private void createNewButton(int position, String character, boolean isFirstRow) {

        // mảng 12 phần tử, mà for chạy từ 1 => id = 12 trong mảng 12 phần tử => lỗi
        int id = position - 1;

        Log.e("e",position+"");

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                2);

        params.setMargins(10, 10, 10, 10);
        params.gravity = Gravity.CENTER_VERTICAL;

        arrayButton[id] = new Button(getContext());

        arrayButton[id].setTag(character);
        arrayButton[id].setId(id);
        arrayButton[id].setOnClickListener(this);
        arrayButton[id].setLayoutParams(params);

        arrayButton[id].setGravity(Gravity.CENTER);
        arrayButton[id].setText(character);
        arrayButton[id].setTextSize(24f);
        arrayButton[id].setTextColor(ResourcesCompat.getColor(Objects.requireNonNull(getContext()).getResources(),R.color.colorWhite,null));
        arrayButton[id].setBackground(ResourcesCompat.getDrawable(getContext().getResources(),R.drawable.border_custom_button,null));

        if(isFirstRow)
            firstRow.addView(arrayButton[id]);
        else
            newRow.addView(arrayButton[id]);

        isFadeOut[id] = false;
    }

    private void createNewRow() {

        newRow = new LinearLayout(getContext());

        LinearLayout.LayoutParams paramNewRow = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        newRow.setLayoutParams(paramNewRow);
        newRow.setWeightSum(8);
        newRow.setOrientation(LinearLayout.HORIZONTAL);

        lltBtnContainer.addView(newRow);
    }

    private void createFirstRow() {

        firstRow = new LinearLayout(getContext());

        LinearLayout.LayoutParams paramFirstRow = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        firstRow.setLayoutParams(paramFirstRow);
        firstRow.setOrientation(LinearLayout.HORIZONTAL);
        firstRow.setWeightSum(8);

        lltBtnContainer.addView(firstRow);
    }

    private void createCharArray(String answer, int length) {

        for(int i = 0; i < length ; i++){
            arrayCharQuest[i] = answer.charAt(i);
        }

        generateMoreRandomChar(length);

        shuffleArray(arrayCharQuest);
    }

    private void generateMoreRandomChar(int length) {

        char[] chars = {'あ','か','さ','た','な','は','ま','や','ら','わ',
                'い','き','し','ち','に','ひ','み','り',
                'う','く','す','つ','ぬ','ふ','む','ゆ','る',
                'え','け','せ','て','ね','へ','め','れ',
                'お','こ','そ','と','の','ほ','も','よ','ろ','を','ん'};

        for(int i = length ; i < 12 ; i++){

            int randomNum = ThreadLocalRandom.current().nextInt(0, chars.length - 1);

            arrayCharQuest[i] = chars[randomNum];
        }
    }

    private void shuffleArray(char[] arr) {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = ThreadLocalRandom.current();
        for (int i = arr.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            char a = arr[index];
            arr[index] = arr[i];
            arr[i] = a;
        }
    }

    private void init(View view) {
        lltBtnContainer       = view.findViewById(R.id.lltBtnContainer);
        lltAnswerContainer    = view.findViewById(R.id.lltAnswerContainer);
        imgVocabularyQuest    = view.findViewById(R.id.imgVocabularyQuest);
        imgBtnTip             = view.findViewById(R.id.imgBtnTip);
        imgBtnPrevQuest       = view.findViewById(R.id.imgBtnPrevQuest);
        imgBtnNextQuest       = view.findViewById(R.id.imgBtnNextQuest);
        imgBtnRefresh         = view.findViewById(R.id.imgBtnRefresh);
        dialog                = new DialogGoToGrammarTest();
        progressBar_VocabularyTest = view.findViewById(R.id.progressBar_VocabularyTest);

        imgBtnTip.setOnClickListener(this);
        imgBtnNextQuest.setOnClickListener(this);
        imgBtnPrevQuest.setOnClickListener(this);
        imgBtnRefresh.setOnClickListener(this);
        dialog.setListener(this);
    }

    private void setImage(String imageName) {

        Picasso.with(getContext())
                .load(imageName)
                .placeholder(android.R.color.darker_gray)
                .config(Bitmap.Config.RGB_565)
                .into(imgVocabularyQuest, new Callback() {
                    @Override
                    public void onSuccess() {
                        imgVocabularyQuest.setVisibility(View.VISIBLE);
                        progressBar_VocabularyTest.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.imgBtnTip:
                showTooltip();
                break;
            case R.id.imgBtnNextQuest:
                onNextQuest();
                break;
            case R.id.imgBtnPrevQuest:
                onPrevQuest();
                break;
            case R.id.imgBtnRefresh:
                showTipOrClearText();
                break;
            // những button trong arrayButton
            default:
                setTextAndDismissButton(view.getTag().toString(), view.getId());
                break;
        }

    }

    private void showTipOrClearText() {

        int length = quest.getAnswer().length();

        StringBuilder sb = new StringBuilder();

        for(int i = 1 ; i <= length ; i++)
            if(arrayTextView[i] != null)
                sb.append(arrayTextView[i].getText().toString());

        if(sb.toString().equals(""))
            ViewTooltip
                    .on(this, imgBtnRefresh)
                    .autoHide(true, 900)
                    .clickToHide(true)
                    .align(CENTER)
                    .position(ViewTooltip.Position.LEFT)
                    .text("Use to clear the answer")
                    .textSize(2,20)
                    .textColor(ResourcesCompat.getColor(this.getResources(),R.color.colorWhite,null))
                    .color(ResourcesCompat.getColor(this.getResources(),R.color.colorPrimaryDark,null))
                    .corner(10)
                    .arrowWidth(15)
                    .arrowHeight(15)
                    .distanceWithView(0)
                    .animation(new ViewTooltip.FadeTooltipAnimation(500))
                    .show();
        else {
            clearText();
            resetButton();
        }
    }

    private void onPrevQuest() {

        if(idQuest == 0)
            ViewTooltip
                    .on(this, imgBtnPrevQuest)
                    .autoHide(true, 900)
                    .clickToHide(true)
                    .align(CENTER)
                    .position(ViewTooltip.Position.BOTTOM)
                    .text("There is no previoust quiz")
                    .textSize(2,20)
                    .textColor(ResourcesCompat.getColor(this.getResources(),R.color.colorWhite,null))
                    .color(ResourcesCompat.getColor(this.getResources(),R.color.colorPrimaryDark,null))
                    .corner(10)
                    .arrowWidth(15)
                    .arrowHeight(15)
                    .distanceWithView(0)
                    .animation(new ViewTooltip.FadeTooltipAnimation(500))
                    .show();
        else if(listener != null)
            listener.onPrevQuest();
    }

    private void showTooltip() {
        ViewTooltip
                .on(this, imgBtnTip)
                .autoHide(true, 2000)
                .clickToHide(true)
                .align(CENTER)
                .position(ViewTooltip.Position.BOTTOM)
                .text("Hint: "+quest.getHint())
                .textSize(2,24)
                .textColor(ResourcesCompat.getColor(this.getResources(),R.color.colorWhite,null))
                .color(ResourcesCompat.getColor(this.getResources(),R.color.colorPrimaryDark,null))
                .corner(10)
                .arrowWidth(15)
                .arrowHeight(15)
                .distanceWithView(0)
                .animation(new ViewTooltip.FadeTooltipAnimation(500))
                .show();
    }

    private void setTextAndDismissButton(String answer, int id) {
        // currentInputTextView bắt đầu từ 1
        if(currentInputTextView <= quest.getAnswer().length()){

            arrayTextView[currentInputTextView].setText(answer);

            Animation animation  = AnimationUtils.loadAnimation(getContext(),R.anim.anim_scale_in_fade_in);
            Animation animation1 = AnimationUtils.loadAnimation(getContext(),R.anim.anim_scale_out_fade_out);
            arrayButton[id].startAnimation(animation);
            arrayButton[id].setVisibility(View.INVISIBLE);
            isFadeOut[id] = true;

            arrayTextView[currentInputTextView].startAnimation(animation1);

            currentInputTextView++;
        }
    }

    @Override
    public void onOkDialog() {
        if(listener != null)
            listener.onNextQuest(idQuest,isRightAnswer);
    }

    @Override
    public void onCancleDialog() {
        dialog.dismiss();
    }
}
