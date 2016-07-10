/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.wxine_mvp.data.source.remote;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.example.wxine_mvp.data.Info;
import com.example.wxine_mvp.data.source.InfosDataSource;
import com.example.wxine_mvp.data.source.local.InfosDbHelper;
import com.example.wxine_mvp.data.source.local.InfosPersistenceContract;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementation of the data source that adds a latency simulating network.
 */
public class TasksRemoteDataSource implements InfosDataSource {

    private InfosDbHelper mDbHelper;

    private static TasksRemoteDataSource INSTANCE;

    private static final int SERVICE_LATENCY_IN_MILLIS = 5000;



    public static TasksRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TasksRemoteDataSource();
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private TasksRemoteDataSource() {}

    /**
     * Note: {@link LoadInfosCallback#onDataNotAvailable()} is fired if the database doesn't exist
     * or the table is empty.
     */
    @Override
    public void getInfos(@NonNull LoadInfosCallback callback) {
        List<Info> Infos = new ArrayList<Info>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                InfosPersistenceContract.InfoEntry.COLUMN_NAME_ENTRY_ID,
                InfosPersistenceContract.InfoEntry.COLUMN_NAME_TITLE,
                InfosPersistenceContract.InfoEntry.COLUMN_NAME_DESCRIPTION,
                InfosPersistenceContract.InfoEntry.COLUMN_NAME_COMPLETED
        };

        Cursor c = db.query(
                InfosPersistenceContract.InfoEntry.TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String itemId = c.getString(c.getColumnIndexOrThrow(InfosPersistenceContract.InfoEntry.COLUMN_NAME_ENTRY_ID));
                String title = c.getString(c.getColumnIndexOrThrow(InfosPersistenceContract.InfoEntry.COLUMN_NAME_TITLE));
                String description =
                        c.getString(c.getColumnIndexOrThrow(InfosPersistenceContract.InfoEntry.COLUMN_NAME_DESCRIPTION));
                boolean completed =
                        c.getInt(c.getColumnIndexOrThrow(InfosPersistenceContract.InfoEntry.COLUMN_NAME_COMPLETED)) == 1;
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
                InfosPersistenceContract.InfoEntry.COLUMN_NAME_ENTRY_ID,
                InfosPersistenceContract.InfoEntry.COLUMN_NAME_TITLE,
                InfosPersistenceContract.InfoEntry.COLUMN_NAME_DESCRIPTION,
                InfosPersistenceContract.InfoEntry.COLUMN_NAME_COMPLETED
        };

        String selection = InfosPersistenceContract.InfoEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = { InfoId };

        Cursor c = db.query(
                InfosPersistenceContract.InfoEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        Info Info = null;

        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            String itemId = c.getString(c.getColumnIndexOrThrow(InfosPersistenceContract.InfoEntry.COLUMN_NAME_ENTRY_ID));
            String title = c.getString(c.getColumnIndexOrThrow(InfosPersistenceContract.InfoEntry.COLUMN_NAME_TITLE));
            String description =
                    c.getString(c.getColumnIndexOrThrow(InfosPersistenceContract.InfoEntry.COLUMN_NAME_DESCRIPTION));
            boolean completed =
                    c.getInt(c.getColumnIndexOrThrow(InfosPersistenceContract.InfoEntry.COLUMN_NAME_COMPLETED)) == 1;
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
        values.put(InfosPersistenceContract.InfoEntry.COLUMN_NAME_ENTRY_ID, Info.getId());
        values.put(InfosPersistenceContract.InfoEntry.COLUMN_NAME_TITLE, Info.getTitle());
        //    values.put(InfoEntry.COLUMN_NAME_DESCRIPTION, Info.getDescription());
        //    values.put(InfoEntry.COLUMN_NAME_COMPLETED, Info.isCompleted());

        db.insert(InfosPersistenceContract.InfoEntry.TABLE_NAME, null, values);

        db.close();
    }

    @Override
    public void completeInfo(@NonNull Info Info) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(InfosPersistenceContract.InfoEntry.COLUMN_NAME_COMPLETED, true);

        String selection = InfosPersistenceContract.InfoEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = { Info.getId() };

        db.update(InfosPersistenceContract.InfoEntry.TABLE_NAME, values, selection, selectionArgs);

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
        values.put(InfosPersistenceContract.InfoEntry.COLUMN_NAME_COMPLETED, false);

        String selection = InfosPersistenceContract.InfoEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = { Info.getId() };

        db.update(InfosPersistenceContract.InfoEntry.TABLE_NAME, values, selection, selectionArgs);

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

        String selection = InfosPersistenceContract.InfoEntry.COLUMN_NAME_COMPLETED + " LIKE ?";
        String[] selectionArgs = { "1" };

        db.delete(InfosPersistenceContract.InfoEntry.TABLE_NAME, selection, selectionArgs);

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

        db.delete(InfosPersistenceContract.InfoEntry.TABLE_NAME, null, null);

        db.close();
    }

    @Override
    public void deleteInfo(@NonNull String InfoId) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String selection = InfosPersistenceContract.InfoEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = { InfoId };

        db.delete(InfosPersistenceContract.InfoEntry.TABLE_NAME, selection, selectionArgs);

        db.close();
    }

    @Override
    public void login(String username, String password, OnLoginFinishedListener listener) {

    }

    @Override
    public void register(String username, String reusername, String password, OnRegisterFinishedListener listener) {

    }
}
