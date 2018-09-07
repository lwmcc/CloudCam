package com.mccarty.cloudcam.ui.main;

import android.app.Activity;
import android.util.Log;

import com.mccarty.cloudcam.ui.base.BaseView;

public class MainPresenterImpl<V extends BaseView> implements MainPresenter {

    private final static String TAG = MainPresenterImpl.class.getSimpleName();

    private V view;

    public V getView() {
        return view;
    }

    public void setView(V view) {
        this.view = view;
    }

    @Override
    public boolean hasInternetConnection() {
        return false;
    }

    @Override
    public int getNumbereOfCameras() {
        return 0;
    }

    @Override
    public void captureImage() {
        Log.d(TAG,"CAPTURE IMAGE");
    }

    @Override
    public void takeView(Object view) {

    }

    @Override
    public void dropView() {

    }

    @Override
    public boolean hasInternetAccess() {
        return false;
    }

}
