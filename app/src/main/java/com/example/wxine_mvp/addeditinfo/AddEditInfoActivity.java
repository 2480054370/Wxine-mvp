package com.example.wxine_mvp.addeditinfo;

import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.wxine_mvp.Injection;
import com.example.wxine_mvp.R;
import com.example.wxine_mvp.util.ActivityUtils;

public class AddEditInfoActivity extends AppCompatActivity {
    public static final int REQUEST_ADD_TASK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addinfo_act);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        AddEditInfoFragment addEditInfoFragment =
                (AddEditInfoFragment) getSupportFragmentManager().findFragmentById(R.id.content);
        String infoId = null;
        if (addEditInfoFragment == null) {
            addEditInfoFragment = AddEditInfoFragment.newInstance();

            if (getIntent().hasExtra(AddEditInfoFragment.ARGUMENT_EDIT_INFO_ID)) {
                infoId = getIntent().getStringExtra(
                        AddEditInfoFragment.ARGUMENT_EDIT_INFO_ID);
                toolbar.setTitle("TO DO");
                Bundle bundle = new Bundle();
                bundle.putString(AddEditInfoFragment.ARGUMENT_EDIT_INFO_ID, infoId);
                addEditInfoFragment.setArguments(bundle);
            } else {
                toolbar.setTitle("TO DO");
            }

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    addEditInfoFragment,R.id.content);
        }
        new AddEditInfoPresenter(infoId,Injection.provideInfosRepository(getApplicationContext()),addEditInfoFragment);

    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }


}
