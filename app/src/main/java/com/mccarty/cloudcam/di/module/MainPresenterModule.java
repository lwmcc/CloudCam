package com.mccarty.cloudcam.di.module;

import com.mccarty.cloudcam.model.MainModel;
import com.mccarty.cloudcam.persistence.local.CloudCamDatabase;
import com.mccarty.cloudcam.ui.main.MainContract;
import com.mccarty.cloudcam.ui.main.MainPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module(includes = {DatabaseModule.class, NetworkModule.class})
public class MainPresenterModule {

    @Provides
    static MainModel provideMainModel(CloudCamDatabase cloudDatabase) {
        return new MainModel(cloudDatabase);
    }

    @Provides
    static MainPresenterImpl provideMainPresenter(MainModel mainModel) {
        return new MainPresenterImpl(mainModel);
    }
}
