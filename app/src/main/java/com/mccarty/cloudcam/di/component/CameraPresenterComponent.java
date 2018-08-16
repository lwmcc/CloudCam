package com.mccarty.cloudcam.di.component;

//import com.mccarty.cloudcam.di.module.AppModule;
import com.mccarty.cloudcam.di.module.CameraModelModule;
import com.mccarty.cloudcam.di.module.MainPresenterModule;
import com.mccarty.cloudcam.ui.camera.CameraPresenterImpl;
import com.mccarty.cloudcam.ui.main.MainFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = MainPresenterModule.class)
public interface CameraPresenterComponent {
    void inject(MainFragment mainFragment);
}
