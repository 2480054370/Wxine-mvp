package com.example.wxine_mvp.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.wxine_mvp.Injection;
import com.example.wxine_mvp.R;
import com.example.wxine_mvp.data.source.InfosRepository;
import com.example.wxine_mvp.data.source.local.InfosLocalDataSource;
import com.example.wxine_mvp.data.source.remote.TasksRemoteDataSource;
import com.example.wxine_mvp.login.LoginActivity;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by admin on 2016/7/7.
 */
public class RegActivity extends AppCompatActivity implements RegContract.View{

    private EditText username, password, repassword;
    private Button register_button;
    private TextView login_text;
    private ProgressBar register_progress;

    private RegContract.Presenter mPresenter;//P

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mPresenter = new RegPresenter(Injection.provideInfosRepository(getApplicationContext()),RegActivity.this);
        username = (EditText) findViewById(R.id.register_name);
        password = (EditText) findViewById(R.id.register_password);
        repassword = (EditText) findViewById(R.id.confirm_password);
        register_button = (Button) findViewById(R.id.register_button);
        login_text = (TextView) findViewById(R.id.login_text);
        register_progress = (ProgressBar) findViewById(R.id.register_progress);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register_progress.setVisibility(View.VISIBLE);
                mPresenter.validateCredentials(username.getText().toString(), password.getText().toString(), repassword.getText().toString());
            }
        });
        login_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegActivity.this, LoginActivity.class);
                startActivity(intent);
                RegActivity.this.finish();
            }
        });

    }

    @Override
    public void setPasswordError() {
        register_progress.setVisibility(View.INVISIBLE);
        Toast.makeText(RegActivity.this,"两次密码输入不一致，请重新输入", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setUserError() {
        register_progress.setVisibility(View.INVISIBLE);
        Toast.makeText(RegActivity.this,"用户名已存在，请重新输入",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setUserNull() {
        register_progress.setVisibility(View.INVISIBLE);
        Toast.makeText(RegActivity.this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToLogin() {
        register_progress.setVisibility(View.INVISIBLE);
        Toast.makeText(RegActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegActivity.this, LoginActivity.class);
        startActivity(intent);
        RegActivity.this.finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull RegContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

}
