package com.example.wxine_mvp.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.example.wxine_mvp.data.Info;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.example.wxine_mvp.data.User;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Windows7 on 2016/7/6.
 */

public class InfosRepository implements InfosDataSource {
    private static InfosRepository INSTANCE = null;

    private final InfosDataSource mInfosRemoteDataSource;

    private final InfosDataSource mInfosLocalDataSource;

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    Map<String, Info> mCachedInfos;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    boolean mCacheIsDirty = false;

    // Prevent direct instantiation.
    private InfosRepository(@NonNull InfosDataSource InfosRemoteDataSource,
                            @NonNull InfosDataSource InfosLocalDataSource) {
        mInfosRemoteDataSource = checkNotNull(InfosRemoteDataSource);
        mInfosLocalDataSource = checkNotNull(InfosLocalDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param InfosRemoteDataSource the backend data source
     * @param InfosLocalDataSource  the device storage data source
     * @return the {@link InfosRepository} instance
     */
    public static InfosRepository getInstance(InfosDataSource InfosRemoteDataSource,
                                              InfosDataSource InfosLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new InfosRepository(InfosRemoteDataSource, InfosLocalDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(InfosDataSource, InfosDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * Gets Infos from cache, local data source (SQLite) or remote data source, whichever is
     * available first.
     * <p>
     * Note: {@link com.example.wxine_mvp.data.source.InfosDataSource.LoadInfosCallback#onDataNotAvailable()} is fired if all data sources fail to
     * get the data.
     */

    public void getInfos(@NonNull final InfosDataSource.LoadInfosCallback callback) {
        checkNotNull(callback);

        // Respond immediately with cache if available and not dirty
        if (mCachedInfos != null && !mCacheIsDirty) {
            callback.onInfosLoaded(new ArrayList<>(mCachedInfos.values()));
            return;
        }

        if (mCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            getInfosFromRemoteDataSource(callback);
        } else {
            // Query the local storage if available. If not, query the network.
            mInfosLocalDataSource.getInfos(new InfosDataSource.LoadInfosCallback() {
                @Override
                public void onInfosLoaded(List<Info> Infos) {
                    refreshCache(Infos);
                    callback.onInfosLoaded(new ArrayList<>(mCachedInfos.values()));
                }

                @Override
                public void onDataNotAvailable() {
                    getInfosFromRemoteDataSource(callback);
                }
            });
        }
    }


    public void saveInfo(@NonNull Info Info) {
        checkNotNull(Info);
        mInfosRemoteDataSource.saveInfo(Info);
        mInfosLocalDataSource.saveInfo(Info);

        // Do in memory cache update to keep the app UI up to date
        if (mCachedInfos == null) {
            mCachedInfos = new LinkedHashMap<>();
        }
        mCachedInfos.put(Info.getId(), Info);
    }


    public void completeInfo(@NonNull Info Info) {
        checkNotNull(Info);
        mInfosRemoteDataSource.completeInfo(Info);
        mInfosLocalDataSource.completeInfo(Info);

        //Info completedInfo = new Info(Info.getTitle(), Info.getDescription(), Info.getId(), true);

        // Do in memory cache update to keep the app UI up to date
        if (mCachedInfos == null) {
            mCachedInfos = new LinkedHashMap<>();
        }
    //    mCachedInfos.put(Info.getId(), completedInfo);
    }


    public void completeInfo(@NonNull String InfoId) {
        checkNotNull(InfoId);
        completeInfo(getInfoWithId(InfoId));
    }


    public void activateInfo(@NonNull Info Info) {
        checkNotNull(Info);
        mInfosRemoteDataSource.activateInfo(Info);
        mInfosLocalDataSource.activateInfo(Info);

     //   Info activeInfo = new Info(Info.getTitle(), Info.getDescription(), Info.getId());

        // Do in memory cache update to keep the app UI up to date
        if (mCachedInfos == null) {
            mCachedInfos = new LinkedHashMap<>();
        }
 //       mCachedInfos.put(Info.getId(), activeInfo);
    }

    @Override
    public void activateInfo(@NonNull String InfoId) {
        checkNotNull(InfoId);
        activateInfo(getInfoWithId(InfoId));
    }

    @Override
    public void clearCompletedInfos() {
        mInfosRemoteDataSource.clearCompletedInfos();
        mInfosLocalDataSource.clearCompletedInfos();

        // Do in memory cache update to keep the app UI up to date
        if (mCachedInfos == null) {
            mCachedInfos = new LinkedHashMap<>();
        }
        Iterator<Map.Entry<String, Info>> it = mCachedInfos.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Info> entry = it.next();
            if (entry.getValue().isCompleted()) {
                it.remove();
            }
        }
    }

    /**
     * Gets Infos from local data source (sqlite) unless the table is new or empty. In that case it
     * uses the network data source. This is done to simplify the sample.
     * <p>
     * Note: {@link com.example.wxine_mvp.data.source.InfosDataSource.LoadInfosCallback#onDataNotAvailable()} is fired if both data sources fail to
     * get the data.
     */

    public void getInfo(@NonNull final String InfoId, @NonNull final InfosDataSource.GetInfoCallback callback) {
        checkNotNull(InfoId);
        checkNotNull(callback);

        Info cachedInfo = getInfoWithId(InfoId);

        // Respond immediately with cache if available
        if (cachedInfo != null) {
            callback.onInfoLoaded(cachedInfo);
            return;
        }

        // Load from server/persisted if needed.

        // Is the Info in the local data source? If not, query the network.
        mInfosLocalDataSource.getInfo(InfoId, new InfosDataSource.GetInfoCallback() {
            @Override
            public void onInfoLoaded(Info Info) {
                callback.onInfoLoaded(Info);
            }

            @Override
            public void onDataNotAvailable() {
                mInfosRemoteDataSource.getInfo(InfoId, new InfosDataSource.GetInfoCallback() {
                    @Override
                    public void onInfoLoaded(Info Info) {
                        callback.onInfoLoaded(Info);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });
    }


    public void refreshInfos() {
        mCacheIsDirty = true;
    }


    public void deleteAllInfos() {
        mInfosRemoteDataSource.deleteAllInfos();
        mInfosLocalDataSource.deleteAllInfos();

        if (mCachedInfos == null) {
            mCachedInfos = new LinkedHashMap<>();
        }
        mCachedInfos.clear();
    }


    public void deleteInfo(@NonNull String InfoId) {
        mInfosRemoteDataSource.deleteInfo(checkNotNull(InfoId));
        mInfosLocalDataSource.deleteInfo(checkNotNull(InfoId));

        mCachedInfos.remove(InfoId);
    }

    private void getInfosFromRemoteDataSource(@NonNull final InfosDataSource.LoadInfosCallback callback) {
        mInfosRemoteDataSource.getInfos(new InfosDataSource.LoadInfosCallback() {
            @Override
            public void onInfosLoaded(List<Info> Infos) {
                refreshCache(Infos);
                refreshLocalDataSource(Infos);
                callback.onInfosLoaded(new ArrayList<>(mCachedInfos.values()));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshCache(List<Info> Infos) {
        if (mCachedInfos == null) {
            mCachedInfos = new LinkedHashMap<>();
        }
        mCachedInfos.clear();
        for (Info Info : Infos) {
            mCachedInfos.put(Info.getId(), Info);
        }
        mCacheIsDirty = false;
    }

    private void refreshLocalDataSource(List<Info> Infos) {
        mInfosLocalDataSource.deleteAllInfos();
        for (Info Info : Infos) {
            mInfosLocalDataSource.saveInfo(Info);
        }
    }

    @Nullable
    private Info getInfoWithId(@NonNull String id) {
        checkNotNull(id);
        if (mCachedInfos == null || mCachedInfos.isEmpty()) {
            return null;
        } else {
            return mCachedInfos.get(id);
        }
    }
}
