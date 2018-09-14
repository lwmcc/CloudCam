package com.mccarty.cloudcam.persistence.local.Image;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Date;

@Entity
public final class ImageEntity {

    @PrimaryKey
    @NonNull
    private String imageName;

    @NonNull
    @ColumnInfo(name = "image_path")
    private String imagePath;

    @Nullable
    @ColumnInfo(name = "date")
    private Date date;

    @NonNull
    public String getImageName() {
        return imageName;
    }

    @NonNull
    public String getImagePath() {
        return imagePath;
    }

    @Nullable
    public Date getDate() {
        return date;
    }

    public void setImageName(@NonNull String imageName) {
        this.imageName = imageName;
    }

    public void setImagePath(@NonNull String imagePath) {
        this.imagePath = imagePath;
    }

    public void setDate(@Nullable Date date) {
        this.date = date;
    }

}
