package com.mccarty.cloudcam.ui.main;

import android.app.Activity;
import android.util.Log;

import com.mccarty.cloudcam.di.component.ActivityScope;
import com.mccarty.cloudcam.model.MainModel;
import com.mccarty.cloudcam.ui.base.BaseView;

import javax.inject.Inject;

//@ActivityScope
public class MainPresenterImpl implements MainContract.MainPresenter {


    private MainContract.MainView view;

    private final MainModel model;

    @Inject
    public MainPresenterImpl(MainModel mainModel) {
        this.model = mainModel;
    }

    @Override
    public void takeView(MainContract.MainView view) {
        this.view = view;
    }

    @Override
    public void dropView() {

    }

    @Override
    public boolean hasInternetAccess() {
        return false;
    }

    @Override
    public void getAllImages() {
        System.out.print("GET ALL IMAGES");
    }
}
