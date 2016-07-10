package com.example.wxine_mvp.login;


import com.example.wxine_mvp.BasePresenter;
import com.example.wxine_mvp.BaseView;

/**
 * Created by Leeeeee on 2016/7/7.
 */
public interface LoginContract {
    interface View extends BaseView<Presenter> {

        void setUserNull();

        void setUserError();

        void navigateToHome();
    }

    interface Presenter extends BasePresenter {

        void validateCredentials(String username, String password);

    }
}
