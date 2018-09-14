package com.mccarty.cloudcam.di.component;

import android.app.Application;

import com.mccarty.cloudcam.CloudCamApp;
import com.mccarty.cloudcam.di.module.DatabaseModule;
import com.mccarty.cloudcam.utils.ImageSaver;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;

@Singleton
//@Component(modules = DatabaseModule.class)
public interface CloudCamDatabaseComponent extends AndroidInjector<CloudCamApp> {

   /* @Component.Builder
    interface Builder {
        @BindsInstance
        CloudCamDatabaseComponent.Builder application(Application application);

        CloudCamDatabaseComponent build();
    }*/
}
