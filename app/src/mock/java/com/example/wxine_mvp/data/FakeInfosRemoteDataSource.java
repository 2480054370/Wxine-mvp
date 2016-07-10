package com.example.wxine_mvp.data;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.example.wxine_mvp.data.source.InfosDataSource;
import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Windows7 on 2016/7/6.
 */

public class FakeInfosRemoteDataSource  implements InfosDataSource {
    private static FakeInfosRemoteDataSource INSTANCE;

    private static final Map<String, Info> InfoS_SERVICE_DATA = new LinkedHashMap<>();

    // Prevent direct instantiation.
    private FakeInfosRemoteDataSource() {}

    public static FakeInfosRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FakeInfosRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getInfos(@NonNull LoadInfosCallback callback) {
        callback.onInfosLoaded(Lists.newArrayList(InfoS_SERVICE_DATA.values()));
    }

    @Override
    public void getInfo(@NonNull String InfoId, @NonNull GetInfoCallback callback) {
        Info Info = InfoS_SERVICE_DATA.get(InfoId);
        callback.onInfoLoaded(Info);
    }

    @Override
    public void saveInfo(@NonNull Info Info) {
        InfoS_SERVICE_DATA.put(Info.getId(), Info);
    }

    @Override
    public void completeInfo(@NonNull Info Info) {
        Info completedInfo = new Info(Info.getTitle());
        InfoS_SERVICE_DATA.put(Info.getId(), completedInfo);
    }

    @Override
    public void completeInfo(@NonNull String InfoId) {
        // Not required for the remote data source.
    }

    @Override
    public void activateInfo(@NonNull Info Info) {
        Info activeInfo = new Info(Info.getTitle());
        InfoS_SERVICE_DATA.put(Info.getId(), activeInfo);
    }

    @Override
    public void activateInfo(@NonNull String InfoId) {
        // Not required for the remote data source.
    }

    @Override
    public void clearCompletedInfos() {
        Iterator<Map.Entry<String, Info>> it = InfoS_SERVICE_DATA.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Info> entry = it.next();
            if (entry.getValue().isCompleted()) {
                it.remove();
            }
        }
    }

    public void refreshInfos() {
        // Not required because the {@link InfosRepository} handles the logic of refreshing the
        // Infos from all the available data sources.
    }

    @Override
    public void deleteInfo(@NonNull String InfoId) {
        InfoS_SERVICE_DATA.remove(InfoId);
    }

    @Override
    public void deleteAllInfos() {
        InfoS_SERVICE_DATA.clear();
    }

    @VisibleForTesting
    public void addInfos(Info... Infos) {
        for (Info Info : Infos) {
            InfoS_SERVICE_DATA.put(Info.getId(), Info);
        }
    }

    @Override
    public void login(String username, String password, OnLoginFinishedListener listener) {

    }

    @Override
    public void register(String username, String reusername, String password, OnRegisterFinishedListener listener) {

    }
}
