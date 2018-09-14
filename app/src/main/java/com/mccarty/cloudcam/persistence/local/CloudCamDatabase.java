package com.mccarty.cloudcam.persistence.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.mccarty.cloudcam.persistence.local.Image.ImageDao;
import com.mccarty.cloudcam.persistence.local.Image.ImageEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Database(entities = {ImageEntity.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class CloudCamDatabase extends RoomDatabase {

    public abstract ImageDao imageDao();
}
