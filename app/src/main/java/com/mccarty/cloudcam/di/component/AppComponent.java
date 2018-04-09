package com.mccarty.cloudcam.di.component;

import android.app.Application;

import com.mccarty.cloudcam.CloudCamApp;
import com.mccarty.cloudcam.di.module.ActivityBuilder;
import com.mccarty.cloudcam.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by Larry McCarty on 3/20/2018.
 */

@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class,
        ActivityBuilder.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(CloudCamApp app);
}
