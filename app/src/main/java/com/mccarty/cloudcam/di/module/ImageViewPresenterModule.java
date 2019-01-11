package com.mccarty.cloudcam.di.module;

import android.app.Application;

import com.mccarty.cloudcam.model.MainModel;
import com.mccarty.cloudcam.persistence.local.CloudCamDatabase;
import com.mccarty.cloudcam.persistence.remote.image.RemoteImageDao;
import com.mccarty.cloudcam.ui.imageview.ImageViewPresenter;
import com.mccarty.cloudcam.ui.main.MainPresenterImpl;
import com.mccarty.cloudcam.utils.NetworkUtils;

import dagger.Module;
import dagger.Provides;

@Module
class ImageViewPresenterModule {

    @Provides
    static ImageViewPresenter provideImageViewPresenter() {
        return new ImageViewPresenter();
    }
}
