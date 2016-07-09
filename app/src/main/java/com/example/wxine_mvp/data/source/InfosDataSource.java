package com.example.wxine_mvp.data.source;

import android.support.annotation.NonNull;

import com.example.wxine_mvp.data.Info;
import java.util.List;

/**
 * Created by Windows7 on 2016/7/6.
 */

public interface InfosDataSource {
    interface LoadInfosCallback {

        void onInfosLoaded(List<Info> Infos);

        void onDataNotAvailable();
    }

    interface GetInfoCallback {

        void onInfoLoaded(Info Info);

        void onDataNotAvailable();
    }

    void getInfos(@NonNull LoadInfosCallback callback);

    void getInfo(@NonNull String InfoId, @NonNull GetInfoCallback callback);

    void saveInfo(@NonNull Info Info);

    void completeInfo(@NonNull Info Info);

    void completeInfo(@NonNull String InfoId);

    void activateInfo(@NonNull Info Info);

    void activateInfo(@NonNull String InfoId);

    void clearCompletedInfos();

    void refreshInfos();

    void deleteAllInfos();

    void deleteInfo(@NonNull String InfoId);
}
