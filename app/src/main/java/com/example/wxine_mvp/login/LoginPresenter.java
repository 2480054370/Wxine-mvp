package com.example.wxine_mvp.login;

import android.support.annotation.NonNull;

import com.example.wxine_mvp.data.source.InfosDataSource;
import com.example.wxine_mvp.data.source.InfosRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by admin on 2016/7/8.
 */
public class LoginPresenter implements LoginContract.Presenter, InfosDataSource.OnLoginFinishedListener {
    private InfosRepository mTasksRepository;
    private LoginContract.View loginView;


    public LoginPresenter(@NonNull InfosRepository tasksRepository, @NonNull LoginContract.View tasksView) {
        mTasksRepository = checkNotNull(tasksRepository, "tasksRepository cannot be null");
        loginView = checkNotNull(tasksView, "tasksView cannot be null!");

        loginView.setPresenter(this);
    }


    @Override
    public void validateCredentials(String username, String password) {
        if (username.equals("") || password.equals("")) {
            loginView.setUserNull();
        } else {
            mTasksRepository.login(username, password, this);
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void onUserError() {
        loginView.setUserError();
    }

    @Override
    public void onSuccess() {
        loginView.navigateToHome();
    }
}
