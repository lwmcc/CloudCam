package com.mccarty.cloudcam.di.module;

import android.app.Activity;

import com.mccarty.cloudcam.di.component.MainActivitySubcomponent;
import com.mccarty.cloudcam.ui.camera.CameraActivity;
import com.mccarty.cloudcam.ui.main.Main3Activity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * Created by Larry McCarty on 3/20/2018.
 */

@Module
public abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = {MainFragmentModule.class})
    abstract Main3Activity bindMainActivity();

    @ContributesAndroidInjector(modules = {CameraActivityModule.class,
            NetworkUtilsModule.class, CameraModule.class, ConfigurationTransformModule.class})
    abstract CameraActivity bindCameraActivity();

    /**
     * Created by Larry McCarty on 3/26/2018.
     */

    @Module(subcomponents = MainActivitySubcomponent.class)
    abstract static class MainActivityModule {
        @Binds
        @IntoMap
        @ActivityKey(Main3Activity.class)
        abstract AndroidInjector.Factory<? extends Activity>
        bindMainActivityInjectorFactory(MainActivitySubcomponent.Builder builder);
    }
}
