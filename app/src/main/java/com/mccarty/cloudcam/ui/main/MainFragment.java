package com.mccarty.cloudcam.ui.main;

import android.app.Application;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.GridView;

import com.mccarty.cloudcam.R;
import com.mccarty.cloudcam.di.component.ActivityScope;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.DaggerFragment;

@ActivityScope
public class MainFragment extends DaggerFragment implements MainContract.MainView {

    @Inject
    MainPresenterImpl presenter;

    @BindView(R.id.image_grid)
    GridView gridView;

    private Unbinder unbinder;

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
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
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
        unbinder.unbind();
        presenter.dropView();
    }

    @Override
    public void loadImages() {
        // TODO:
    }

    @Override
    public void imageClicked() {
        presenter.showImage();
    }

    @Override
    public void checkInternetConnection(Application applicatin) {
        // TODO:
    }
}