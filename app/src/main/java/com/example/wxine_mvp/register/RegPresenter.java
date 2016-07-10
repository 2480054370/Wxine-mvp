package com.example.wxine_mvp.register;

import android.support.annotation.NonNull;

import com.example.wxine_mvp.data.source.InfosDataSource;
import com.example.wxine_mvp.data.source.InfosRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by admin on 2016/7/7.
 */
public class RegPresenter implements RegContract.Presenter,InfosDataSource.OnRegisterFinishedListener {
    private InfosRepository mTasksRepository;
    private RegContract.View mTasksView;


    public RegPresenter(@NonNull InfosRepository tasksRepository, @NonNull RegContract.View tasksView) {
        mTasksRepository = checkNotNull(tasksRepository, "tasksRepository cannot be null");
        mTasksView = checkNotNull(tasksView, "tasksView cannot be null!");

        mTasksView.setPresenter(this);
    }


    @Override
    public void start() {

    }

    @Override
    public void validateCredentials(String username, String password, String repassword) {
        if(!password.equals(repassword)){
            mTasksView.setPasswordError();
        }else if(username.equals("") || password.equals("") || repassword.equals("")){
            mTasksView.setUserNull();
        }else{
            mTasksRepository.register(username,password,repassword,this);
        }
    }

    @Override
    public void onSuccess() {
        mTasksView.navigateToLogin();
    }

    @Override
    public void onUserError() {
        mTasksView.setUserError();
    }
}
