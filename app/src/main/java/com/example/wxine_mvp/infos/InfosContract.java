package com.example.wxine_mvp.infos;


import com.example.wxine_mvp.BasePresenter;
import com.example.wxine_mvp.BaseView;
import com.example.wxine_mvp.data.Info;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Windows7 on 2016/7/6.
 */

public interface InfosContract {
    interface View extends BaseView<Presenter> {
        void showAddInfo();
        void showInfos(ArrayList<Info> infos);
        void showSuccessfullySavedMessage();
        void setLoadingIndicator(boolean active);
        boolean isActive();
        void showLoadingTasksError();
    }

    interface Presenter extends BasePresenter {

        void addNewInfo();
        void result(int requestCode, int resultCode);
    }
}
