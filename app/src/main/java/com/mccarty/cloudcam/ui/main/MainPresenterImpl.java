package com.mccarty.cloudcam.ui.main;

import android.app.Activity;
import android.util.Log;

import com.mccarty.cloudcam.di.component.ActivityScope;
import com.mccarty.cloudcam.ui.base.BaseView;

@ActivityScope
public class MainPresenterImpl<V extends BaseView> implements MainContract.Presenter {

    private final static String TAG = MainPresenterImpl.class.getSimpleName();

    private V view;

    public V getView() {
        return view;
    }

    public void setView(V view) {
        this.view = view;
    }

  /*  @Override
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

    }*/

    @Override
    public void takeView(MainContract.View view) {

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

    }
}
