package com.example.win7.apphoctiengnhat.Translate.controller.data.db;

import com.example.win7.apphoctiengnhat.Translate.model.pojo.HistoryData;

import java.util.List;

/**
 * Created by NGusarov on 17/03/17.
 */

public interface Repository {

    void open();

    void close();

    void addHistory(HistoryData data);

    List<HistoryData> getAllHistories();

    List<HistoryData> getFavoriteHistories();

    void deleteHistoryData(int id);

    void deleteAllHistoryData();

    long getDataCount(String tableName);

    void makeHistoryFavorite(int id, boolean isFavorite);

    HistoryData getHistory(int id);

    int getLastHistoryId();
}
