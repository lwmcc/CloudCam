package com.mccarty.cloudcam.di.module;

import android.app.Activity;

import com.mccarty.cloudcam.di.component.MainActivitySubcomponent;
import com.mccarty.cloudcam.ui.camera.CameraFragment;
import com.mccarty.cloudcam.ui.main.Main3Activity;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * Created by Larry McCarty on 3/26/2018.
 */

//@Module(subcomponents = MainActivitySubcomponent.class)
public abstract class MainActivityModule {

   /* @Binds
    @IntoMap
    @ActivityKey(Main3Activity.class)
    abstract AndroidInjector.Factory<? extends Activity>
    bindMain3ActivityInjectorFactory(MainActivitySubcomponent.Builder builder);*/
}
