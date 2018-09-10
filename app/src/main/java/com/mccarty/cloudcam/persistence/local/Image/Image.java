package com.mccarty.cloudcam.persistence.local.Image;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.time.LocalDateTime;

@Entity
public final class Image {

    @PrimaryKey
    @NonNull
    private String imageName;

    @NonNull
    @ColumnInfo(name = "image_path")
    private String imagePath;

    @Nullable
    @ColumnInfo(name = "date_time")
    private LocalDateTime date;

    @Nullable
    @ColumnInfo(name = "camera_settings")
    private String cameraSettings;

}
