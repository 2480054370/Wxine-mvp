package com.example.wxine_mvp.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Windows7 on 2016/7/6.
 */

public class InfosDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Tasks.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String BOOLEAN_TYPE = " INTEGER";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + InfosPersistenceContract.InfoEntry.TABLE_NAME + " (" +
                    InfosPersistenceContract.InfoEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    InfosPersistenceContract.InfoEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    InfosPersistenceContract.InfoEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    InfosPersistenceContract.InfoEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    InfosPersistenceContract.InfoEntry.COLUMN_NAME_COMPLETED + BOOLEAN_TYPE +
                    " )";

    public InfosDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }

}
