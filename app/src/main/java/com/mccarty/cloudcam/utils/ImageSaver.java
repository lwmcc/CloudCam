package com.mccarty.cloudcam.utils;

import android.media.Image;

import com.mccarty.cloudcam.persistence.local.Image.ImageDao;
import com.mccarty.cloudcam.persistence.local.Image.ImageEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Date;

public class ImageSaver implements Runnable {

    private final Image image;
    private final File file;
    private final ImageDao imageDao;

    public ImageSaver(Image image, File file, ImageDao imageDao) {
        this.image = image;
        this.file = file;
        this.imageDao = imageDao;
    }

    @Override
    public void run() {
        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        FileOutputStream output = null;

        try {
            output = new FileOutputStream(file);
            output.write(bytes);

            ImageEntity imageEntity = new ImageEntity();
            imageEntity.setDate(new Date());
            imageEntity.setImageName(file.getName());
            imageEntity.setImagePath(file.getPath());

            imageDao.insertImage(imageEntity);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            image.close();
            if (null != output) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
