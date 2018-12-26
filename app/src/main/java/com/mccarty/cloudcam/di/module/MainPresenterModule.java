package com.mccarty.cloudcam.di.module;

import com.mccarty.cloudcam.model.MainModel;
import com.mccarty.cloudcam.persistence.local.CloudCamDatabase;
import com.mccarty.cloudcam.persistence.remote.image.RemoteImageDao;
import com.mccarty.cloudcam.ui.main.MainContract;
import com.mccarty.cloudcam.ui.main.MainPresenterImpl;
import com.mccarty.cloudcam.utils.NetworkUtils;

import dagger.Module;
import dagger.Provides;

@Module(includes = {DatabaseModule.class, NetworkModule.class, AWSModule.class})
public class MainPresenterModule {

    @Provides
    static MainModel provideMainModel(CloudCamDatabase cloudDatabase, NetworkUtils networkUtils,
                                      RemoteImageDao remoteImageDao) {
        return new MainModel(cloudDatabase, networkUtils, remoteImageDao);
    }

    @Provides
    static MainPresenterImpl provideMainPresenter(MainModel mainModel) {
        return new MainPresenterImpl(mainModel);
    }
}
