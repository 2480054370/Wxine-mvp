package com.example.wxine_mvp.login;

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
import com.example.wxine_mvp.infos.InfosActivity;
import com.example.wxine_mvp.register.RegActivity;
import com.example.wxine_mvp.register.RegContract;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by admin on 2016/7/8.
 */
public class LoginActivity extends AppCompatActivity implements LoginContract.View {


    private EditText username;
    private EditText password;
    private Button login_button;
    private TextView register_text;
    private ProgressBar login_progress;

    private LoginContract.Presenter mPresenter;//P

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new LoginPresenter(Injection.provideInfosRepository(getApplicationContext()),LoginActivity.this);

        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        login_button = (Button) findViewById(R.id.email_sign_in_button);
        register_text = (TextView) findViewById(R.id.register_text);
        login_progress = (ProgressBar) findViewById(R.id.login_progress);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_progress.setVisibility(View.VISIBLE);
                mPresenter.validateCredentials(username.getText().toString(), password.getText().toString());
            }
        });
        register_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });
    }

    @Override
    public void setUserNull() {
        login_progress.setVisibility(View.INVISIBLE);
        Toast.makeText(this,"账号或密码为空，请输入账号密码",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setUserError() {
        login_progress.setVisibility(View.INVISIBLE);
        Toast.makeText(this, "用户名或者密码错误，请重新输入", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToHome() {
        login_progress.setVisibility(View.INVISIBLE);
        Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this, InfosActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }


    @Override
    public void setPresenter(LoginContract.Presenter presenter) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    public void setmPresenter(LoginContract.Presenter mPresenter) {
        this.mPresenter = checkNotNull(mPresenter);
    }
}
