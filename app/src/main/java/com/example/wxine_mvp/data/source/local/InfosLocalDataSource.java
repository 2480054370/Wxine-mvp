package com.example.wxine_mvp.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.example.wxine_mvp.data.Info;
import com.example.wxine_mvp.data.source.InfosDataSource;
import com.example.wxine_mvp.data.source.local.InfosPersistenceContract.InfoEntry;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Windows7 on 2016/7/6.
 */

public class InfosLocalDataSource implements InfosDataSource {
    private static InfosLocalDataSource INSTANCE;

    private InfosDbHelper mDbHelper;

    // Prevent direct instantiation.
    private InfosLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
        mDbHelper = new InfosDbHelper(context);
    }

    public static InfosLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new InfosLocalDataSource(context);
        }
        return INSTANCE;
    }

    /**
     * Note: {@link LoadInfosCallback#onDataNotAvailable()} is fired if the database doesn't exist
     * or the table is empty.
     */
    @Override
    public void getInfos(@NonNull LoadInfosCallback callback) {
        List<Info> Infos = new ArrayList<Info>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                InfoEntry.COLUMN_NAME_ENTRY_ID,
                InfoEntry.COLUMN_NAME_TITLE,
                InfoEntry.COLUMN_NAME_DESCRIPTION,
                InfoEntry.COLUMN_NAME_COMPLETED
        };

        Cursor c = db.query(
                InfoEntry.TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String itemId = c.getString(c.getColumnIndexOrThrow(InfoEntry.COLUMN_NAME_ENTRY_ID));
                String title = c.getString(c.getColumnIndexOrThrow(InfoEntry.COLUMN_NAME_TITLE));
                String description =
                        c.getString(c.getColumnIndexOrThrow(InfoEntry.COLUMN_NAME_DESCRIPTION));
                boolean completed =
                        c.getInt(c.getColumnIndexOrThrow(InfoEntry.COLUMN_NAME_COMPLETED)) == 1;
                //Info Info = new Info(title, description, itemId, completed);
                //Infos.add(Info);
            }
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (Infos.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable();
        } else {
            callback.onInfosLoaded(Infos);
        }

    }

    /**
     * Note: {@link GetInfoCallback#onDataNotAvailable()} is fired if the {@link Info} isn't
     * found.
     */
    @Override
    public void getInfo(@NonNull String InfoId, @NonNull GetInfoCallback callback) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                InfoEntry.COLUMN_NAME_ENTRY_ID,
                InfoEntry.COLUMN_NAME_TITLE,
                InfoEntry.COLUMN_NAME_DESCRIPTION,
                InfoEntry.COLUMN_NAME_COMPLETED
        };

        String selection = InfoEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = { InfoId };

        Cursor c = db.query(
                InfoEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        Info Info = null;

        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            String itemId = c.getString(c.getColumnIndexOrThrow(InfoEntry.COLUMN_NAME_ENTRY_ID));
            String title = c.getString(c.getColumnIndexOrThrow(InfoEntry.COLUMN_NAME_TITLE));
            String description =
                    c.getString(c.getColumnIndexOrThrow(InfoEntry.COLUMN_NAME_DESCRIPTION));
            boolean completed =
                    c.getInt(c.getColumnIndexOrThrow(InfoEntry.COLUMN_NAME_COMPLETED)) == 1;
         //   Info = new Info(title, description, itemId, completed);
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (Info != null) {
            callback.onInfoLoaded(Info);
        } else {
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void saveInfo(@NonNull Info Info) {
        checkNotNull(Info);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(InfoEntry.COLUMN_NAME_ENTRY_ID, Info.getId());
        values.put(InfoEntry.COLUMN_NAME_TITLE, Info.getTitle());
    //    values.put(InfoEntry.COLUMN_NAME_DESCRIPTION, Info.getDescription());
    //    values.put(InfoEntry.COLUMN_NAME_COMPLETED, Info.isCompleted());

        db.insert(InfoEntry.TABLE_NAME, null, values);

        db.close();
    }

    @Override
    public void completeInfo(@NonNull Info Info) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(InfoEntry.COLUMN_NAME_COMPLETED, true);

        String selection = InfoEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = { Info.getId() };

        db.update(InfoEntry.TABLE_NAME, values, selection, selectionArgs);

        db.close();
    }

    @Override
    public void completeInfo(@NonNull String InfoId) {
        // Not required for the local data source because the {@link InfosRepository} handles
        // converting from a {@code InfoId} to a {@link Info} using its cached data.
    }

    @Override
    public void activateInfo(@NonNull Info Info) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(InfoEntry.COLUMN_NAME_COMPLETED, false);

        String selection = InfoEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = { Info.getId() };

        db.update(InfoEntry.TABLE_NAME, values, selection, selectionArgs);

        db.close();
    }

    @Override
    public void activateInfo(@NonNull String InfoId) {
        // Not required for the local data source because the {@link InfosRepository} handles
        // converting from a {@code InfoId} to a {@link Info} using its cached data.
    }

    @Override
    public void clearCompletedInfos() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String selection = InfoEntry.COLUMN_NAME_COMPLETED + " LIKE ?";
        String[] selectionArgs = { "1" };

        db.delete(InfoEntry.TABLE_NAME, selection, selectionArgs);

        db.close();
    }

    @Override
    public void refreshInfos() {
        // Not required because the {@link InfosRepository} handles the logic of refreshing the
        // Infos from all the available data sources.
    }

    @Override
    public void deleteAllInfos() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        db.delete(InfoEntry.TABLE_NAME, null, null);

        db.close();
    }

    @Override
    public void deleteInfo(@NonNull String InfoId) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String selection = InfoEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = { InfoId };

        db.delete(InfoEntry.TABLE_NAME, selection, selectionArgs);

        db.close();
    }
}
