package com.mccarty.cloudcam.di.module;

import android.app.Fragment;

import com.mccarty.cloudcam.di.component.ActivityScope;
import com.mccarty.cloudcam.di.component.CameraComponent;
import com.mccarty.cloudcam.di.component.CameraScope;
import com.mccarty.cloudcam.di.component.FragmentScope;
import com.mccarty.cloudcam.ui.camera.CameraFragment;
import com.mccarty.cloudcam.ui.camera.CameraPresenterImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjector;
import dagger.android.ContributesAndroidInjector;
import dagger.android.FragmentKey;
import dagger.multibindings.IntoMap;

@Module
public class CameraFragmentModule {

    @Provides
    public CameraFragment provideCameraFragment() {
        return new CameraFragment();
    }
}
