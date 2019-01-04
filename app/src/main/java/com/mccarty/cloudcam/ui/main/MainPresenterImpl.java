package com.mccarty.cloudcam.ui.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
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

import static com.mccarty.cloudcam.persistence.PersistenceConstants.INSERT_ENTITY;

//@ActivityScope
public class MainPresenterImpl implements MainContract.MainPresenter {

    private MainContract.MainView view;
    private final MainModel model;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final Context context;

    @Inject
    public MainPresenterImpl(MainModel mainModel, Context context) {
        this.model = mainModel;
        this.context = context;
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
        return model.hasInternetAccess();
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
        view.loadImages(images);
    }

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getAllImages();
        }
    };

    public void registerReceiver() {
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver, new IntentFilter(INSERT_ENTITY));
    }

    public void unregisterReceiver() {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver);
    }

}