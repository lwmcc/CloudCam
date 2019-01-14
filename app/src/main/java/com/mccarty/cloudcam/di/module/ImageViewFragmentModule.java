package com.mccarty.cloudcam.di.module;

import com.mccarty.cloudcam.ui.imageview.ImageViewFragment;
import com.mccarty.cloudcam.ui.main.MainFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class ImageViewFragmentModule {

    @Provides
    ImageViewFragment provideImageViewFragment() {
        return new ImageViewFragment();
    }
}