package com.mccarty.cloudcam.persistence.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.mccarty.cloudcam.persistence.local.Image.Image;
import com.mccarty.cloudcam.persistence.local.Image.ImageDao;


@Database(entities = {Image.class}, version = 1)
public abstract class CloudCamDatabase extends RoomDatabase {
    public abstract ImageDao imageDao();
}
