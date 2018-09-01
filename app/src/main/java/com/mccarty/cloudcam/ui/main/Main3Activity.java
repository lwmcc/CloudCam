package com.mccarty.cloudcam.ui.main;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mccarty.cloudcam.R;
import com.mccarty.cloudcam.ui.base.BaseActivity;
import com.mccarty.cloudcam.ui.camera.CameraActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class Main3Activity extends BaseActivity {

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        MainFragment frag = new MainFragment();
        fragmentTransaction.add(R.id.main_fragment, frag);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.fab)
    public void navigateToCameraFragmen() {
        startActivity(new Intent(Main3Activity.this, CameraActivity.class));
    }
}
