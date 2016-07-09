package com.example.wxine_mvp.addeditinfo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.wxine_mvp.data.Info;
import com.example.wxine_mvp.data.source.InfosDataSource;
import com.example.wxine_mvp.data.source.local.InfosLocalDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Windows7 on 2016/7/7.
 */

public class AddEditInfoPresenter implements AddEditInfoContract.Presenter,
        InfosLocalDataSource.GetInfoCallback {
    @NonNull
    private final InfosDataSource mInfosRepository;

    @NonNull
    private final AddEditInfoContract.View mAddInfoView;

    @Nullable
    private String mInfoId;



    public AddEditInfoPresenter(@Nullable String infoId, @NonNull InfosDataSource infosRepository,
                                @NonNull AddEditInfoContract.View addInfoView) {
        mInfoId = infoId;
        mInfosRepository = checkNotNull(infosRepository);
        mAddInfoView = checkNotNull(addInfoView);

        mAddInfoView.setPresenter(this);
    }

    @Override
    public void start() {
        if (!isNewInfo()) {
            populateInfo();
        }
    }



    public void saveInfo(String title, String content) {
        if (isNewInfo()) {
            createInfo(title, content);
        } else {
            updateTask(title, content);
        }
    }

    public void populateInfo() {
        if (isNewInfo()) {
            throw new RuntimeException("populateTask() was called but task is new.");
        }
        mInfosRepository.getInfo(mInfoId, this);
    }


    public void onInfoLoaded(Info info) {
        // The view may not be able to handle UI updates anymore
        if (mAddInfoView.isActive()) {
            mAddInfoView.setTitle(info.getTitle());
            mAddInfoView.setContent(info.getContent());
        }
    }

    public void onDataNotAvailable() {
        // The view may not be able to handle UI updates anymore
        if (mAddInfoView.isActive()) {
            mAddInfoView.showEmptyInfoError();
        }
    }

    private boolean isNewInfo() {
        return mInfoId == null;
    }

    private void createInfo(String title, String content) {
        Info newInfo = new Info(title, content);
        if (newInfo.isEmpty()) {
            mAddInfoView.showEmptyInfoError();
        } else {
            mInfosRepository.saveInfo(newInfo);
            mAddInfoView.showInfosList();
        }
    }

    private void updateTask(String title, String content) {
        if (isNewInfo()) {
            throw new RuntimeException("updateTask() was called but task is new.");
        }
        mInfosRepository.saveInfo(new Info(title, content));
        mAddInfoView.showInfosList(); // After an edit, go back to the list.
    }
}
