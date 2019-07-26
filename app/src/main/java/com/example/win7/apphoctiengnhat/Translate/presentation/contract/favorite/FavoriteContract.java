package com.example.win7.apphoctiengnhat.Translate.presentation.contract.favorite;

import com.example.win7.apphoctiengnhat.Translate.model.pojo.HistoryData;
import com.example.win7.apphoctiengnhat.Translate.presentation.contract.BaseContract;

import java.util.List;

/**
 * Created by NG on 20.04.17.
 */

public interface FavoriteContract extends BaseContract {

    interface View {
        void showEmptyView();

        void notifyData();
    }

    interface Presenter {
        List<HistoryData> getFavorite();

        void unMackeFavorite(int key, boolean isChecked);

        void notifyData();

        void unMakeAllFavorites();
    }
}
