package com.mccarty.cloudcam.di.module;

import android.app.Application;

import java.io.File;

import dagger.Module;
import dagger.Provides;

import static com.mccarty.cloudcam.apis.APIConstants.IMG_CLOUD_CAM;

@Module
class FileModule {
    @Provides
    static File provideFile(Application application) {
        return new File(application.getExternalFilesDir(null), IMG_CLOUD_CAM);
    }
}
