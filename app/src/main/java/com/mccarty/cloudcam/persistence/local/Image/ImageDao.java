package com.mccarty.cloudcam.persistence.local.Image;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface ImageDao {

    @Query("SELECT * FROM ImageEntity")
    List<ImageEntity> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertImage(ImageEntity imageEntity);

    @Delete
    void delete(ImageEntity image);
}
