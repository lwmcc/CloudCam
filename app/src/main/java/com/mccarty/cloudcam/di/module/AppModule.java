package com.mccarty.cloudcam.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.mccarty.cloudcam.persistence.local.CloudCamDatabase;
import com.mccarty.cloudcam.persistence.local.Image.ImageDao;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import static com.mccarty.cloudcam.di.module.ModuleConstants.CLOUDCAM_DB;

@Module
public abstract class AppModule {
    @Binds
    abstract Context bindContext(Application application);
}
