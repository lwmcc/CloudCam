package com.mccarty.cloudcam.di.module;

import android.app.Application;

import com.mccarty.cloudcam.model.MainModel;
import com.mccarty.cloudcam.persistence.local.CloudCamDatabase;
import com.mccarty.cloudcam.persistence.local.Image.ImageDao;
import com.mccarty.cloudcam.persistence.remote.image.RemoteImageDao;
import com.mccarty.cloudcam.ui.main.MainPresenterImpl;
import com.mccarty.cloudcam.utils.NetworkUtils;

import dagger.Module;
import dagger.Provides;

@Module(includes = {DatabaseModule.class, NetworkModule.class, AWSModule.class})
class MainPresenterModule {

    @Provides
    static MainModel provideMainModel(CloudCamDatabase cloudDatabase, NetworkUtils networkUtils,
                                      RemoteImageDao remoteImageDao, ImageDao imageDao) {
        return new MainModel(cloudDatabase, networkUtils, remoteImageDao, imageDao);
    }

    @Provides
    static MainPresenterImpl provideMainPresenter(MainModel mainModel, Application application) {
        return new MainPresenterImpl(mainModel, application);
    }
}
