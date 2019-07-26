package com.example.win7.apphoctiengnhat.Translate.presentation.implementation.translate;

import android.text.TextUtils;
import android.util.Log;

import com.example.win7.apphoctiengnhat.CurrentFragmentUseTranslate.CurrentFragmentUseTranslatePresentationImpl;
import com.example.win7.apphoctiengnhat.Translate.controller.data.service.history.HistoryDataService;
import com.example.win7.apphoctiengnhat.Translate.controller.data.spreference.SPreferenceManager;
import com.example.win7.apphoctiengnhat.Translate.controller.network.YandexTranslateApi;
import com.example.win7.apphoctiengnhat.Translate.controller.network.data.response.LanguageListResponse;
import com.example.win7.apphoctiengnhat.Translate.controller.network.data.response.TranslateResponse;
import com.example.win7.apphoctiengnhat.Translate.model.pojo.LanguagePair;
import com.example.win7.apphoctiengnhat.Translate.model.pojo.LanguageTranscript;
import com.example.win7.apphoctiengnhat.Translate.presentation.contract.translate.TranslateContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NGusarov on 17/03/17.
 */

public class TranslatePresenterImpl implements TranslateContract.Presenter {

    public static final String TAG = TranslatePresenterImpl.class.getSimpleName();

    private TranslateContract.View mView;

    private YandexTranslateApi mYandexTranslateApi;
    private HistoryDataService mHistoryDataService;
    private SPreferenceManager mSPreferenceManager;

    private List<String> mSupportedLanguages = new ArrayList<>();
    private List<LanguageTranscript> mLanguageTranscripts = new ArrayList<>();
    private List<LanguagePair> mLanguagePairs = new ArrayList<>();
    private LanguageTranscript mFrom;
    private LanguageTranscript mTo;

    //sửa danh sách ngôn ngữ để dịch tại đây
    private static String LANG = "en";

//    @Inject
    public TranslatePresenterImpl(YandexTranslateApi api,
                                  HistoryDataService historyDataService,
                                  SPreferenceManager preferenceManager,
                                  TranslateContract.View view) {
        this.mView = view;
        this.mYandexTranslateApi = api;
        this.mHistoryDataService = historyDataService;
        this.mSPreferenceManager = preferenceManager;
        loadSupportLanguages();
    }

    private void loadSupportLanguages() {
        Call<LanguageListResponse> call = mYandexTranslateApi.loadSupportedLangList(LANG);
        call.enqueue(new Callback<LanguageListResponse>() {
            @Override
            public void onResponse(final Call<LanguageListResponse> call, final Response<LanguageListResponse> response) {
                setSupportLangToView(response.body().getMapLangs(), response.body().getListDirs());
            }

            @Override
            public void onFailure(final Call<LanguageListResponse> call, final Throwable t) {
                Log.d(TAG, "error");
                t.printStackTrace();
            }
        });
    }

    private void setSupportLangToView(Map<String, String> supportedLangs, List<String> supportedLangDirs) {

//        for (String lang: supportedLangDirs) {
//            mLanguagePairs.add(new LanguagePair(lang));
//            Log.e("LANG LANG LANG : ",lang);
//        }
//
//        for (Map.Entry<String, String> entry: supportedLangs.entrySet()) {
//            mLanguageTranscripts.add(new LanguageTranscript(entry.getValue(), entry.getKey()));
//            mSupportedLanguages.add(entry.getValue());
//            Log.e("LANG LANG LANG mLanguageTranscripts : ",entry.getValue()+ " " + entry.getKey());
//            Log.e("LANG LANG LANG mSupportedLanguages : ",entry.getValue());
//        }

        mLanguagePairs.add(new LanguagePair("ja","en"));
        mLanguagePairs.add(new LanguagePair("ja","vi"));
        mLanguagePairs.add(new LanguagePair("en","ja"));
        mLanguagePairs.add(new LanguagePair("en","vi"));
        mLanguagePairs.add(new LanguagePair("vi","ja"));
        mLanguagePairs.add(new LanguagePair("vi","en"));

        mLanguageTranscripts.add(new LanguageTranscript("Japanese","ja"));
        mSupportedLanguages.add("Japanese");
        mLanguageTranscripts.add(new LanguageTranscript("English","en"));
        mSupportedLanguages.add("English");
        mLanguageTranscripts.add(new LanguageTranscript("Vietnamese","vi"));
        mSupportedLanguages.add("Vietnamese");

        mView.setLanguages();

        setDefaultLanguages(mSPreferenceManager.getCurrentLanguages());
        mView.invalidateSpinnerView();
    }

    private void setDefaultLanguages(final LanguagePair currentLanguages) {

        mFrom = new LanguageTranscript(getTranscript(currentLanguages.getLanguageFrom()), currentLanguages.getLanguageFrom());
        mTo = new LanguageTranscript(getTranscript(currentLanguages.getLanguageTo()), currentLanguages.getLanguageTo());
        mView.setFromSpinnerSelection((mSupportedLanguages.indexOf(mFrom.getName())));
        mView.setToSpinnerPosition(mSupportedLanguages.indexOf(mTo.getName()));

    }

    private String getTranscript(String language) {
        for (LanguageTranscript transcript: mLanguageTranscripts) {
            if (transcript.getTranscript().equals(language)) {
                return transcript.getName();
            }
        }

        return "error";
    }

    @Override
    public void getTranslate(String message) {

        String LanguagePair = "";

        switch (CurrentFragmentUseTranslatePresentationImpl.CURRENT_LANGUAGE_PAIR){
            case 0:
                break;
            case 1:
                LanguagePair = "ja-vi";
                break;
            case 2:
                LanguagePair = "en-ja";
                break;
            case 3:
                LanguagePair = getCurrentLanguagePair().getLangPairStringValue();
                break;
            case 4:
                LanguagePair = "en-vi";
                break;
            case 5:
                LanguagePair = "ja-en";
                break;
        }

        if (TextUtils.isEmpty(message) || LanguagePair.equals("")) {
            return;
        }
        //Log.d(TAG, "GET TRANSLATE FOR MESSAGE: " + message);
        //Log.d(TAG, "GET LANG: " + getCurrentLanguagePair().getLangPairStringValue());

        mView.showProgressBar();

        Call<TranslateResponse> call = mYandexTranslateApi.loadTranslateLang(message, LanguagePair);
        call.enqueue(new Callback<TranslateResponse>() {
            @Override
            public void onResponse(final Call<TranslateResponse> call, final Response<TranslateResponse> response) {
                if (response.isSuccessful()) {
                    mView.showTranslateResult(response.body().getResponseText());
                    mView.dismissProgressBar();
                    mHistoryDataService.addHistoryData(message, response.body().getResponseText(), getCurrentLanguagePair());
                    saveCurrentLangs();
                } else {
                    mView.dismissProgressBar();
                    mView.showError(response.message());
                }
            }

            @Override
            public void onFailure(final Call<TranslateResponse> call, final Throwable t) {
                mView.dismissProgressBar();
                mView.showError(t.getMessage());
            }
        });
    }

    private void saveCurrentLangs() {
        mSPreferenceManager.saveCurrentLanguages(getCurrentLanguagePair());
    }

    public List<String> getSupportedLang() {
        return mSupportedLanguages;
    }

    private LanguagePair getCurrentLanguagePair() {
        Log.d(TAG, "get current lang pair:  " + mFrom.toString() + ", to: " + mTo.toString());
        return new LanguagePair(mFrom.getTranscript(), mTo.getTranscript());
    }

    public void fromSelectItem(final int position) {
        mFrom = mLanguageTranscripts.get(position);
        Log.d(TAG, "new MFROM: " + mFrom.toString() + "position: " + position);
    }

    public void toSelectItem(final int position) {
        mTo = mLanguageTranscripts.get(position);
        Log.d(TAG, "new MTO: " + mTo.toString() + "position: " + position);
    }

    public void swapLangs() {
        swapTranscript();
        swapView();
    }

    private void swapTranscript() {
        Log.d(TAG, "swap transcript");
        LanguageTranscript tmp = mFrom;
        mFrom = mTo;
        mTo = tmp;
        saveCurrentLangs();
    }

    private void swapView() {
        int tmpPosition = mView.getFromSpinnerPosition();
        mView.setFromSpinnerSelection(mView.getToSpinnerPosition());
        mView.setToSpinnerPosition(tmpPosition);
        mView.swapText();
    }

    public void forceTranslate() {
        Log.d(TAG, "FORCE TRANSLATE. CURRENT LANGS: " + getCurrentLanguagePair().getLangPairStringValue());
        getTranslate(mView.getOriginal());
        saveCurrentLangs();
    }

    public void makeLastFavorite() {
        int lastHistoryId = mHistoryDataService.getLastHistoryId();
        Log.d(TAG, "last id : " + lastHistoryId);
        boolean isFavor = mHistoryDataService.getHistory(lastHistoryId).isFavorite();
        Log.d(TAG, "FAVORITE CURRENT? : " + isFavor);
        mHistoryDataService.makeFavorite(lastHistoryId, !isFavor);
    }
}
