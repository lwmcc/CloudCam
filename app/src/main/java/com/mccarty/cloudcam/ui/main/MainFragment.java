package com.mccarty.cloudcam.ui.main;

import android.app.Application;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mccarty.cloudcam.R;
import com.mccarty.cloudcam.di.component.ActivityScope;

import javax.inject.Inject;

@ActivityScope
public class MainFragment extends Fragment implements MainContract.View {

    @Inject
    MainContract.Presenter presenter;

    public MainFragment() {
        // Requires empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main3, container, false);
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    @Override
    public void loadImages() {

    }

    @Override
    public void checkInternetConnection(Application applicatin) {

    }
}