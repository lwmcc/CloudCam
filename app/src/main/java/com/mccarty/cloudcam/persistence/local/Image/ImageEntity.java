package com.mccarty.cloudcam.persistence.local.Image;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Date;

@Entity
public final class ImageEntity implements Parcelable {

    @PrimaryKey
    @NonNull
    private String imageName;

    @NonNull
    @ColumnInfo(name = "image_path")
    private String imagePath;

    @Nullable
    @ColumnInfo(name = "date")
    private Date date;

    public ImageEntity() {

    }

    protected ImageEntity(Parcel in) {
        imageName = in.readString();
        imagePath = in.readString();
        date = new Date(in.readLong());
    }

    public static final Creator<ImageEntity> CREATOR = new Creator<ImageEntity>() {
        @Override
        public ImageEntity createFromParcel(Parcel in) {
            return new ImageEntity(in);
        }

        @Override
        public ImageEntity[] newArray(int size) {
            return new ImageEntity[size];
        }
    };

    @NonNull
    public String getImageName() {
        return imageName;
    }

    @NonNull
    public String getImagePath() {
        return imagePath;
    }

    public void setDate(@Nullable Date date) {
        this.date = date;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.imageName);
        parcel.writeString(this.imagePath);
        parcel.writeLong(this.date.getTime());
    }
}