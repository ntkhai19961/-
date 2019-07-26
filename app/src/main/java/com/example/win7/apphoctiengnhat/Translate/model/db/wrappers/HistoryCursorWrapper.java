package com.example.win7.apphoctiengnhat.Translate.model.db.wrappers;

import android.database.Cursor;

import com.example.win7.apphoctiengnhat.Translate.model.pojo.HistoryData;
import com.example.win7.apphoctiengnhat.Translate.model.pojo.LanguagePair;

import static com.example.win7.apphoctiengnhat.Translate.model.db.table.HistoryShema.*;

/**
 * Created by NG on 11.04.17.
 */

public class HistoryCursorWrapper extends BaseCursorWrapper<HistoryData> {

    public HistoryCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public HistoryData getData() {
        int key = getInt(getColumnIndex(HistoryTable.Cols.ID));
        String pair = getString(getColumnIndex(HistoryTable.Cols.PAIR));
        String originalText = getString(getColumnIndex(HistoryTable.Cols.TEXT_FROM));
        String translateText = getString(getColumnIndex(HistoryTable.Cols.TEXT_TO));
        long time = getLong(getColumnIndex(HistoryTable.Cols.TIME));
        boolean favorite = getInt(getColumnIndex(HistoryTable.Cols.FAVORITE)) == 1;

        return new HistoryData(key, new LanguagePair(pair), originalText, translateText, time, favorite);
    }
}
