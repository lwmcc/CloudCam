package com.mccarty.cloudcam.di.component;

import com.mccarty.cloudcam.CloudCamApp;
import com.mccarty.cloudcam.di.module.AppModule;


import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

/**
 * Created by Larry McCarty on 3/20/2018.
 */

@Component(modules = {AndroidInjectionModule.class, AppModule.class})
public interface AppComponent extends AndroidInjector<CloudCamApp> {
}
