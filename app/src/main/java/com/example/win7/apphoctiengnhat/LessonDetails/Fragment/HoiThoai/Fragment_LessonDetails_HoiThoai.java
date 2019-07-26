package com.example.win7.apphoctiengnhat.LessonDetails.Fragment.HoiThoai;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.win7.apphoctiengnhat.LessonDetails.Fragment.HoiThoai.Model.Character;
import com.example.win7.apphoctiengnhat.LessonDetails.Fragment.HoiThoai.Model.ContentConversation;
import com.example.win7.apphoctiengnhat.LessonDetails.Fragment.HoiThoai.Model.Typewriter;
import com.example.win7.apphoctiengnhat.R;
import com.vatsal.imagezoomer.ImageZoomButton;
import com.vatsal.imagezoomer.ZoomAnimation;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ntkhai1996 on 19-Dec-17.
 */

public class Fragment_LessonDetails_HoiThoai extends Fragment {

    ImageButton btnLessonDetails_HoiThoai_Conversation_Next;
    ImageButton btnLessonDetails_HoiThoai_Conversation_Hisotry;
    ImageButton btnLessonDetails_HoiThoai_Conversation_Reset;
    ImageButton btnLessonDetails_HoiThoai_Conversation_Sound;
    Typewriter typeWriter_LessonDetails_HoiThoai_Conversation;
    TextView txtFirstPerson;
    TextView txtSecondPerson;
    ImageView imageViewFirstPersonAvatar;
    ImageView imageViewSecondPersonAvatar;

    MediaPlayer mediaPlayer;

    List<ContentConversation> contentConversationList;
    int indexConversation = 0;

    StringBuffer stringBuffer_History_Conversation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lesson_details_hoi_thoai, container, false);

        stringBuffer_History_Conversation = new StringBuffer();

        showContentConversation();
        initView(rootView);

        return rootView;
    }

    private void showContentConversation() {

        contentConversationList = new ArrayList<>();

        Character Neptune = new Character("山田",R.drawable.nep1);
        Character Noire = new Character("王",R.drawable.noire1);

        // cắt mp3 thành nhiều đoạn thì hay hơn là tính giây như này
        contentConversationList.add(new ContentConversation(Neptune,"王さんは　スポーツを　しますか。",1,2700));
        contentConversationList.add(new ContentConversation(Noire,"はい、します。",2,1900));
        contentConversationList.add(new ContentConversation(Neptune,"そうですか。どんなスポーツをしますか。",1,3500));
        contentConversationList.add(new ContentConversation(Noire,"テニスやサッカをします。",2,2500));
        contentConversationList.add(new ContentConversation(Neptune,"ああ、いいですね。",1,2000));

    }

    private void initView(View rootView) {

        mediaPlayer = MediaPlayer.create(getContext(),R.raw.aa);

        txtFirstPerson = rootView.findViewById(R.id.txtFirstPerson);
        txtSecondPerson = rootView.findViewById(R.id.txtSecondPerson);
        imageViewFirstPersonAvatar = rootView.findViewById(R.id.imageViewFirstPersonAvatar);
        imageViewSecondPersonAvatar = rootView.findViewById(R.id.imageViewSecondPersonAvatar);

        btnLessonDetails_HoiThoai_Conversation_Sound = rootView.findViewById(R.id.btnLessonDetails_HoiThoai_Conversation_Sound);
        btnLessonDetails_HoiThoai_Conversation_Reset = rootView.findViewById(R.id.btnLessonDetails_HoiThoai_Conversation_Reset);
        btnLessonDetails_HoiThoai_Conversation_Hisotry = rootView.findViewById(R.id.btnLessonDetails_HoiThoai_Conversation_Hisotry);
        btnLessonDetails_HoiThoai_Conversation_Next = rootView.findViewById(R.id.btnLessonDetails_HoiThoai_Conversation_Next);
        btnConversationNext();
        btnConversationHistory();
        btnConversationReset();
        btnConversationSound();

        typeWriter_LessonDetails_HoiThoai_Conversation = rootView.findViewById(R.id.typeWriter_LessonDetails_HoiThoai_Conversation);
        typeWriter_LessonDetails_HoiThoai_Conversation.setCharacterDelay(50);
    }

    private void btnConversationSound() {

        btnLessonDetails_HoiThoai_Conversation_Sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

    private void btnConversationReset() {


        btnLessonDetails_HoiThoai_Conversation_Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mediaPlayer.release();
                mediaPlayer = MediaPlayer.create(getContext(),R.raw.aa);

                indexConversation = 0;
                stringBuffer_History_Conversation.setLength(0);

                imageViewSecondPersonAvatar.setImageResource(0);
                imageViewFirstPersonAvatar.setImageResource(0);
                txtFirstPerson.setBackgroundResource(0);
                txtSecondPerson.setBackgroundResource(0);
                txtFirstPerson.setText("");
                txtSecondPerson.setText("");

                typeWriter_LessonDetails_HoiThoai_Conversation.setText("");
            }
        });

    }

    public AlertDialog.Builder buildDialogHistory(Context context , StringBuffer sb) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        String titleText = "History";


        // Initialize a new foreground color span instance
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.RED);
        // Initialize a new spannable string builder instance
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);
        // Apply the text color span
        ssBuilder.setSpan(
                foregroundColorSpan,
                0,
                titleText.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );


        builder.setTitle(ssBuilder);
        if(sb.length() != 0)
            builder.setMessage(sb);
        else
            builder.setMessage("The story is empty !!!");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //finish();
            }
        });

        return builder;
    }

    private void btnConversationHistory() {

        btnLessonDetails_HoiThoai_Conversation_Hisotry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buildDialogHistory(getContext(), stringBuffer_History_Conversation).show();

            }
        });

    }

    private void btnConversationNext() {

        btnLessonDetails_HoiThoai_Conversation_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // ko cho bấm khi media đang chạy , né lỗi
                btnLessonDetails_HoiThoai_Conversation_Next.setEnabled(false);
                btnLessonDetails_HoiThoai_Conversation_Reset.setEnabled(false);

                if(indexConversation == contentConversationList.size())
                    return;

                // gán = 1 va 2 do biết rõ 2 người , chứ nhiều người thì đây sai rồi
                // sô thứ tự là lẽ thì khung tên trò chuyện ở bên trái
                if(contentConversationList.get(indexConversation).getSoThuTu() % 2 != 0)
                {
                    // Tắt hiển thị thông tin người thứ 2
                    imageViewSecondPersonAvatar.setImageResource(0);

                    txtSecondPerson.setBackgroundResource(0);
                    txtSecondPerson.setText("");

                    // hiển thị các thông tin của người thứ 1
                    txtFirstPerson.setBackgroundColor(Color.parseColor("#80000000"));
                    txtFirstPerson.setText(contentConversationList.get(indexConversation).getCharacter().getName());
                    typeWriter_LessonDetails_HoiThoai_Conversation.animateText(contentConversationList.get(indexConversation).getContent());
                    imageViewFirstPersonAvatar.setImageResource(contentConversationList.get(indexConversation).getCharacter().getAvatar());


                    // Add nội dung vào history
                    stringBuffer_History_Conversation.append(contentConversationList.get(indexConversation).getCharacter().getName()+ ":   " + contentConversationList.get(indexConversation).getContent());
                    if( indexConversation < contentConversationList.size() - 1 )
                    {
                        stringBuffer_History_Conversation.append(System.lineSeparator());
                        stringBuffer_History_Conversation.append(System.lineSeparator());
                    }

                    // phát voice nhân vật
                    mediaPlayer.start();
                    CountDownTimer countDownTimer = new CountDownTimer(contentConversationList.get(indexConversation).getDuration(), 1000) {
                        public void onTick(long millisUntilFinished) {
                        }

                        public void onFinish() {

                            if(indexConversation < contentConversationList.size() - 1){
                                indexConversation++;
                                mediaPlayer.pause();
                            }
                            else{
                                mediaPlayer.stop();
                            }

                            // cho bấm trở lại khi media đã xong
                            btnLessonDetails_HoiThoai_Conversation_Next.setEnabled(true);
                            btnLessonDetails_HoiThoai_Conversation_Reset.setEnabled(true);
                        }
                    };countDownTimer.start();


                }
                // sô thứ tự là lẽ thì khung tên trò chuyện ở bên phải
                else if(contentConversationList.get(indexConversation).getSoThuTu() % 2 == 0)
                {
                    // Tắt hiển thị thông tin người thứ 1
                    imageViewFirstPersonAvatar.setImageResource(0);

                    txtFirstPerson.setBackgroundResource(0);
                    txtFirstPerson.setText("");

                    // hiển thị các thông tin của người thứ 2
                    txtSecondPerson.setBackgroundColor(Color.parseColor("#80000000"));
                    txtSecondPerson.setText(contentConversationList.get(indexConversation).getCharacter().getName());
                    typeWriter_LessonDetails_HoiThoai_Conversation.animateText(contentConversationList.get(indexConversation).getContent());
                    imageViewSecondPersonAvatar.setImageResource(contentConversationList.get(indexConversation).getCharacter().getAvatar());

                    // Add nội dung vào history
                    stringBuffer_History_Conversation.append(contentConversationList.get(indexConversation).getCharacter().getName()+ ":   " + contentConversationList.get(indexConversation).getContent());
                    if( indexConversation < contentConversationList.size() - 1 )
                    {
                        stringBuffer_History_Conversation.append(System.lineSeparator());
                        stringBuffer_History_Conversation.append(System.lineSeparator());
                    }


                    // phát voice nhân vật
                    mediaPlayer.start();
                    CountDownTimer countDownTimer = new CountDownTimer(contentConversationList.get(indexConversation).getDuration(), 1000) {
                        public void onTick(long millisUntilFinished) {

                        }

                        public void onFinish() {
                            if(indexConversation < contentConversationList.size() - 1){
                                indexConversation++;
                                mediaPlayer.pause();
                            }
                            else{
                                mediaPlayer.stop();
                            }

                            // cho bấm trở lại khi media đã xong
                            btnLessonDetails_HoiThoai_Conversation_Next.setEnabled(true);
                            btnLessonDetails_HoiThoai_Conversation_Reset.setEnabled(true);
                        }
                    };countDownTimer.start();


                }
            }
        });

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("会話");
    }

}
