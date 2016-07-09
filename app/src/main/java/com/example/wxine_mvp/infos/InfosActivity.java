package com.example.wxine_mvp.infos;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.wxine_mvp.MyApplication;
import com.example.wxine_mvp.util.ActivityUtils;
import com.example.wxine_mvp.R;
import com.example.wxine_mvp.Injection;
public class InfosActivity extends AppCompatActivity{
    private static final String CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY";
    private LinearLayout nav;
    private ImageView open;
    DrawerLayout drawer;
    private MyApplication app;
    private InfosPresenter mInfosPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (MyApplication) getApplication();
        setContentView(R.layout.activity_infos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        nav = (LinearLayout) findViewById(R.id.nav_background);
        open = (ImageView) findViewById(R.id.open);
        if (navigationView !=null){
            setupDrawerContent(navigationView);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        InfosFragment infosFragment =
                (InfosFragment) getSupportFragmentManager().findFragmentById(R.id.content);
        if (infosFragment == null){
            infosFragment = InfosFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),infosFragment,R.id.content);
        }
        mInfosPresenter = new InfosPresenter(
                Injection.provideInfosRepository(getApplicationContext()),infosFragment);

        if (savedInstanceState != null){
            InfosFilterType currentFiltering =
                    (InfosFilterType) savedInstanceState.getSerializable(CURRENT_FILTERING_KEY);
            mInfosPresenter.setFiltering(currentFiltering);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(CURRENT_FILTERING_KEY,mInfosPresenter.getFiltering());
        super.onSaveInstanceState(outState);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.nav_profile:
                                // Do nothing, we're already on that screen
                                break;
                            case R.id.nav_friends:
//                                Intent intent =
//                                        new Intent(TasksActivity.this, StatisticsActivity.class);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                startActivity(intent);
                                break;
                            default:
                                break;
                        }
                        // Close the navigation drawer when an item is selected.
                        menuItem.setChecked(true);
                        drawer.closeDrawers();
                        return true;
                    }
                });
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        /*int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.nav_camara) {
            fragment = new InfosFragment();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);*/
        return true;
    }
}
