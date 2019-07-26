package com.example.win7.apphoctiengnhat.App.Fragment.KizunaAi;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.win7.apphoctiengnhat.App.Fragment.KizunaAi.Adapter.KizunaAiChatAdapter;
import com.example.win7.apphoctiengnhat.App.Fragment.KizunaAi.Model.ChatModel;
import com.example.win7.apphoctiengnhat.App.Fragment.KizunaAi.MyClientAccessToken.MyClientAccessToken;
import com.example.win7.apphoctiengnhat.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by ntkhai1996 on 26-Dec-17.
 */

public class Fragment_KizunaAi extends Fragment implements AIListener , ScreenShotable{

    private FloatingActionButton btnSpeak_FragmentKizunaAI;
    private ProgressDialog mDialog;
    private ArrayList<ChatModel> lstChat;
    private KizunaAiChatAdapter adapter;
    private AIService aiService;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_kizuna_ai, container, false);
        setHasOptionsMenu(true);

        checkRecordRadioPermisson();

        setAIConfiguration();

        initView(rootView);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_fragment_kizuna_ai, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear_history_fragment_kizuna_ai:
                lstChat.clear();
                adapter.notifyDataSetChanged();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void checkRecordRadioPermisson() {

        int permission = ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()),
                Manifest.permission.RECORD_AUDIO);

        if (permission != PackageManager.PERMISSION_GRANTED) {

            makeRequest();
        }

    }

    private void makeRequest() {
        ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),
                new String[]{Manifest.permission.RECORD_AUDIO},
                101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 101: {

                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                return;
            }
        }
    }

    private void setAIConfiguration(){

        final AIConfiguration config = new AIConfiguration(MyClientAccessToken.MY_CLIENT_ACCESS_TOKEN,
                AIConfiguration.SupportedLanguages.Japanese,
                AIConfiguration.RecognitionEngine.System);
        aiService = AIService.getService(Objects.requireNonNull(getContext()), config);
        aiService.setListener(this);
    }

    private void initView(View rootView) {

        RecyclerView rcvKazunaAi = rootView.findViewById(R.id.rcvKazunaAi);
        rcvKazunaAi.setHasFixedSize(true);
        rcvKazunaAi.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));
        lstChat = new ArrayList<>();
        adapter = new KizunaAiChatAdapter(getContext(),lstChat);
        rcvKazunaAi.setAdapter(adapter);


        btnSpeak_FragmentKizunaAI = rootView.findViewById(R.id.btnSpeak_FragmentKizunaAI);
        btnSpeakOnClick();

        mDialog = new ProgressDialog(getContext());
        mDialog.setMessage("I'm listenning <3");
    }

    private void btnSpeakOnClick() {
        btnSpeak_FragmentKizunaAI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAnimation();
            }
        });
    }

    private void setAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_scale_out_scale_in);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                mDialog.show();
                aiService.startListening();
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
        btnSpeak_FragmentKizunaAI.startAnimation(animation);
    }

    @Override
    public void onResult(AIResponse result) {

        Result respone = result.getResult();

        lstChat.add(new ChatModel(respone.getResolvedQuery(), respone.getFulfillment().getSpeech(),true));

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onError(AIError error) { }

    @Override
    public void onAudioLevel(float level) { }

    @Override
    public void onListeningStarted() { }

    @Override
    public void onListeningCanceled() {
        mDialog.dismiss();
    }

    @Override
    public void onListeningFinished() {
        mDialog.dismiss();
    }

    @Override
    public void takeScreenShot() { }

    @Override
    public Bitmap getBitmap() {
        return null;
    }
}
