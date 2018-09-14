package com.mccarty.cloudcam.di.module;

import android.app.Application;
import com.mccarty.cloudcam.persistence.local.AppPreferences;
import dagger.Module;
import dagger.Provides;

@Module
public class PreferencesModule {

    @Provides
    AppPreferences provideAppPreferences(Application application) {
        return new AppPreferences(application);
    }
}
