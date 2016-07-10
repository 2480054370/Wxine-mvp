package com.example.wxine_mvp.register;


import com.example.wxine_mvp.BasePresenter;
import com.example.wxine_mvp.BaseView;

/**
 * Created by admin on 2016/7/7.
 */
public interface RegContract {
    interface View extends BaseView<Presenter> {

        void setUserNull();

        void setUserError();

        void setPasswordError();

        void navigateToLogin();
    }

    interface Presenter extends BasePresenter {
        void validateCredentials(String username, String password, String repassword);
    }
}
