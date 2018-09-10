package com.mccarty.cloudcam.di.module;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.support.annotation.NonNull;

import com.mccarty.cloudcam.persistence.local.CloudCamDatabase;
import com.mccarty.cloudcam.persistence.local.Image.Image;
import com.mccarty.cloudcam.persistence.local.Image.ImageDao;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Provides
    CloudCamDatabase provideCloudcamDatabase() {
        return new CloudCamDatabase() {
            @Override
            public ImageDao imageDao() {
                return null;
            }

            @NonNull
            @Override
            protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
                return null;
            }

            @NonNull
            @Override
            protected InvalidationTracker createInvalidationTracker() {
                return null;
            }

            @Override
            public void clearAllTables() {

            }
        };
    }

}
