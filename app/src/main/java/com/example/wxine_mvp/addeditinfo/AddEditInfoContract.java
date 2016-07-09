package com.example.wxine_mvp.addeditinfo;

import com.example.wxine_mvp.BasePresenter;
import com.example.wxine_mvp.BaseView;

/**
 * Created by Windows7 on 2016/7/7.
 */

public interface AddEditInfoContract {
    interface View extends BaseView<Presenter> {

        void showEmptyInfoError();

        void showInfosList();

        void setTitle(String title);

        void setContent(String description);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void saveInfo(String title, String description);

        void populateInfo();
    }
}
