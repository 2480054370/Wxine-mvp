package com.example.wxine_mvp.infos;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.example.wxine_mvp.addeditinfo.AddEditInfoActivity;
import com.example.wxine_mvp.data.Info;
import com.example.wxine_mvp.data.source.InfosDataSource;
import com.example.wxine_mvp.data.source.InfosRepository;
import com.example.wxine_mvp.util.EspressoIdlingResource;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Windows7 on 2016/7/6.
 */

public class InfosPresenter implements InfosContract.Presenter {
    private final InfosRepository mInfosRepository;

    private final InfosContract.View mInfosView;

    private InfosFilterType mCurrentFiltering = InfosFilterType.ALL_INFOS;

    private boolean mFirstLoad = true;

    public InfosPresenter(@NonNull InfosRepository infosRepository, @NonNull InfosContract.View infosView) {
        mInfosRepository = checkNotNull(infosRepository, "tasksRepository cannot be null");
        mInfosView = checkNotNull(infosView, "tasksView cannot be null!");

        mInfosView.setPresenter(this);
    }

    @Override
    public void addNewInfo() {
        mInfosView.showAddInfo();
    }

    public void start() {
        loadInfos(false);
    }

    public void result(int requestCode, int resultCode) {
        // If a task was successfully added, show snackbar
        if (AddEditInfoActivity.REQUEST_ADD_TASK == requestCode && Activity.RESULT_OK == resultCode) {
            mInfosView.showSuccessfullySavedMessage();
        }
    }

    public void loadInfos(boolean forceUpdate) {
        // Simplification for sample: a network reload will be forced on first load.
        loadInfos(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    private void loadInfos(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            mInfosView.setLoadingIndicator(true);
        }
        if (forceUpdate) {
            mInfosRepository.refreshInfos();
        }

        // The network request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
        EspressoIdlingResource.increment(); // App is busy until further notice

        mInfosRepository.getInfos(new InfosDataSource.LoadInfosCallback() {

            public void onInfosLoaded(List<Info> infos) {
                ArrayList<Info> infosToShow = new ArrayList<Info>();

                // This callback may be called twice, once for the cache and once for loading
                // the data from the server API, so we check before decrementing, otherwise
                // it throws "Counter has been corrupted!" exception.
                if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                    EspressoIdlingResource.decrement(); // Set app as idle.
                }

                // We filter the tasks based on the requestType
                for (Info info : infos) {
                    switch (mCurrentFiltering) {
                        case ALL_INFOS:
                            infosToShow.add(info);
                            break;
                        case ACTIVE_INFOS:
                            if (info.isActive()) {
                                infosToShow.add(info);
                            }
                            break;
                        case COMPLETED_INFOS:
                            if (info.isCompleted()) {
                                infosToShow.add(info);
                            }
                            break;
                        default:
                            infosToShow.add(info);
                            break;
                    }
                }
                // The view may not be able to handle UI updates anymore
                if (!mInfosView.isActive()) {
                    return;
                }
                if (showLoadingUI) {
                    mInfosView.setLoadingIndicator(false);
                }

                processInfos(infosToShow);
            }


            public void onDataNotAvailable() {
                // The view may not be able to handle UI updates anymore
                if (!mInfosView.isActive()) {
                    return;
                }
                mInfosView.showLoadingTasksError();
            }
        });
    }

    private void processInfos(ArrayList<Info> infos){
        if(infos.isEmpty()){

        } else {
            mInfosView.showInfos(infos);

        }
    }

    public void setFiltering(InfosFilterType requestType) {
        mCurrentFiltering = requestType;
    }

    public InfosFilterType getFiltering() {
        return mCurrentFiltering;
    }
}
