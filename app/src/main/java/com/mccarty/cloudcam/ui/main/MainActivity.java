package com.mccarty.cloudcam.ui.main;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.mccarty.cloudcam.R;
import com.mccarty.cloudcam.ui.base.BaseActivity;
import com.mccarty.cloudcam.ui.camera.CameraActivity;
import com.mccarty.cloudcam.ui.login.LoginActivity;

import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.Lazy;
import dagger.android.AndroidInjection;

public class MainActivity extends BaseActivity {

    @Inject
    MainPresenterImpl presenter;

    @Inject
    Lazy<MainFragment> fragment;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MainFragment mainFragment = (MainFragment) getFragmentManager().findFragmentById(R.id.main_fragment);

        if (mainFragment == null) {
            mainFragment = fragment.get();

            FragmentManager manager = getFragmentManager();
            FragmentTransaction trans = manager.beginTransaction();
            trans.add(R.id.main_fragment, mainFragment);
            trans.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sign_in:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                return true;
            case R.id.action_sign_out:
                // TODO:
                return true;
                default:
            return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.fab)
    public void navigateToCameraFragmen() {
        startActivity(new Intent(MainActivity.this, CameraActivity.class));
    }
}