package com.mccarty.cloudcam.di.module;

import android.app.Application;

import com.mccarty.cloudcam.utils.UIUtils;

import java.io.File;

import dagger.Module;
import dagger.Provides;

@Module
public class FileModule {
    @Provides
    static File provideFile(Application application) {
        return new File(application.getExternalFilesDir(null), UIUtils.prependToImage());
    }
}
