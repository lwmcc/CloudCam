package com.mccarty.cloudcam.ui.main;

import android.util.Log;

import com.mccarty.cloudcam.model.MainModel;
import com.mccarty.cloudcam.persistence.local.Image.ImageEntity;

import java.util.List;

import javax.inject.Inject;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//@ActivityScope
public class MainPresenterImpl implements MainContract.MainPresenter {

    private MainContract.MainView view;
    private final MainModel model;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

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
        return true;
    }

    @Override
    public void getAllImages() {
        Single.create((SingleOnSubscribe<List<ImageEntity>>) e -> e.onSuccess(model.getAllImages()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<ImageEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<ImageEntity> imageEntities) {
                        setImagesToView(imageEntities);
                        compositeDisposable.clear();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO: log this
                    }
                });
    }

    @Override
    public void showImage() {
        Log.d("TAG", "***** SHOW IMAGE ON CLICK");
    }

    @Override
    public void setImagesToView(List<ImageEntity> images) {
        if (!images.isEmpty()) {
            view.loadImages(images);
        } else {
            model.scanForRemoteImages();
            // TODO: add remote images to db
            // load images from db

        }
    }
}