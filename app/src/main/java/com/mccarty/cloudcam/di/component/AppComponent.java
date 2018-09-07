package com.mccarty.cloudcam.di.component;

import android.app.Application;

import com.mccarty.cloudcam.CloudCamApp;
import com.mccarty.cloudcam.di.module.ActivityBuilder;
import com.mccarty.cloudcam.di.module.AppModule;
import com.mccarty.cloudcam.di.module.CameraPresenterModule;
import com.mccarty.cloudcam.di.module.PreferencesModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class,
        ActivityBuilder.class, CameraPresenterModule.class, PreferencesModule.class})
public interface AppComponent extends AndroidInjector<CloudCamApp> {
    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
