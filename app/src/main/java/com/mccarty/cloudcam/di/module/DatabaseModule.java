package com.mccarty.cloudcam.di.module;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.mccarty.cloudcam.persistence.local.CloudCamDatabase;
import com.mccarty.cloudcam.persistence.local.Image.ImageDao;

import dagger.Module;
import dagger.Provides;

import static com.mccarty.cloudcam.di.module.ModuleConstants.CLOUDCAM_DB;

@Module
public class DatabaseModule {

    @Provides
    static CloudCamDatabase provideCloudCamDatabase(Context context) {
        return Room.databaseBuilder(context, CloudCamDatabase.class, CLOUDCAM_DB).build();
    }

    @Provides
    static ImageDao provideImageDao(CloudCamDatabase cloudCamDatabase) {
        return cloudCamDatabase.imageDao();
    }
}
