package com.mccarty.cloudcam.di.module;

import android.app.Application;
import android.app.Fragment;
import android.content.Context;
import android.hardware.Camera;

import com.mccarty.cloudcam.di.component.CameraComponent;
import com.mccarty.cloudcam.di.component.CameraFragmentComponent;
import com.mccarty.cloudcam.persistence.local.AppPreferences;
import com.mccarty.cloudcam.ui.camera.CameraActivity;
import com.mccarty.cloudcam.ui.camera.CameraFragment;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjector;
import dagger.android.ContributesAndroidInjector;
import dagger.android.FragmentKey;
import dagger.multibindings.IntoMap;

@Module
public abstract class AppModule {
    @Binds
    abstract Context bindContext(Application application);
}
