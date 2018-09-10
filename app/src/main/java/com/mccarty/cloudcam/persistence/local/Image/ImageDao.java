package com.mccarty.cloudcam.persistence.local.Image;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ImageDao {

    @Query("SELECT * FROM user")
    List<Image> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertImage(Image image);

    @Delete
    void delete(Image image);
}
