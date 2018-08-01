package com.mccarty.cloudcam.di.component;

//import com.mccarty.cloudcam.di.module.AppModule;
import com.mccarty.cloudcam.di.module.MainPresenterModule;
import com.mccarty.cloudcam.ui.main.MainFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Larry McCarty on 7/25/2018.
 */

@Singleton
@Component(modules = MainPresenterModule.class)
public interface CameraPresenterComponent {
    void inject(MainFragment mainFragment);
}
