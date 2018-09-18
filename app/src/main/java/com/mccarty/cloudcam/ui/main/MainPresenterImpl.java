package com.mccarty.cloudcam.ui.main;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.media.Image;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mccarty.cloudcam.di.component.ActivityScope;
import com.mccarty.cloudcam.model.MainModel;
import com.mccarty.cloudcam.persistence.local.Image.ImageEntity;
import com.mccarty.cloudcam.ui.base.BaseView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

//@ActivityScope
public class MainPresenterImpl implements MainContract.MainPresenter {

    private MainContract.MainView view;
    private final MainModel model;
    private Observer<List<ImageEntity>> observer;

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
        view = null;
    }

    @Override
    public boolean hasInternetAccess() {
        // TODO:
        return false;
    }

    @Override
    public void getAllImages() {
        observer = imageEntities -> {
            imageEntities.stream().forEach(img -> {
                Log.d("***** PRESENTER: ", "" + img.getImageName());
            });

            model.getAllImages().removeObserver(observer);
        };

        model.getAllImages().observeForever(observer);
    }

}

