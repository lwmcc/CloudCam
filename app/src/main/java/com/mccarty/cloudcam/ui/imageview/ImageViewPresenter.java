package com.mccarty.cloudcam.ui.imageview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.mccarty.cloudcam.model.MainModel;
import com.mccarty.cloudcam.persistence.local.Image.ImageEntity;
import com.mccarty.cloudcam.ui.main.MainContract;

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
public class ImageViewPresenter {

    private MainContract.MainView view;

    @Inject
    public ImageViewPresenter() {

    }







}