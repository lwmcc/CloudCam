package com.mccarty.cloudcam.di.module;

import dagger.Module;
import dagger.Provides;
import com.mccarty.cloudcam.utils.ErrorMessageString;

/**
 * Created by Larry McCarty on 3/20/2018.
 */

@Module
public class MainActivityModule {

    @Provides
    ErrorMessageString provideErrorMessageString() {

        return new ErrorMessageString();
    }
}