package com.mccarty.cloudcam.ui.main;

import android.app.Application;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import com.mccarty.cloudcam.R;
import com.mccarty.cloudcam.di.component.ActivityScope;

import javax.inject.Inject;

import dagger.android.DaggerFragment;

@ActivityScope
public class MainFragment extends DaggerFragment implements MainContract.MainView {

    @Inject
    MainPresenterImpl presenter;

    @Inject
    public MainFragment() {
        // Requires empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: do something
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onResume() {
        presenter.takeView(this);
        presenter.getAllImages();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dropView();
    }

    @Override
    public void loadImages() {
        // TODO:
    }

    @Override
    public void checkInternetConnection(Application applicatin) {
        // TODO:
    }
}