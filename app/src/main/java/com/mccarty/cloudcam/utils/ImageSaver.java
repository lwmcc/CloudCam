package com.mccarty.cloudcam.utils;

import android.app.Application;
import android.media.Image;
import android.util.Log;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.mccarty.cloudcam.R;
import com.mccarty.cloudcam.persistence.local.Image.ImageDao;
import com.mccarty.cloudcam.persistence.local.Image.ImageEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ImageSaver {

    private final Application application;
    private final Image image;
    private final File file;
    private final ImageDao imageDao;
    private final NetworkUtils networkUtils;

    public ImageSaver(Application application, Image image, File file, ImageDao imageDao, NetworkUtils networkUtils) {
        this.application = application;
        this.image = image;
        this.file = file;
        this.imageDao = imageDao;
        this.networkUtils = networkUtils;
        AWSMobileClient.getInstance().initialize(application);
    }

    public void saveImage() {


        Observable<Void> observable = Observable.create(o -> {
            ByteBuffer buffer = image.getPlanes()[0].getBuffer();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            FileOutputStream output = null;

            try {
                output = new FileOutputStream(file);
                output.write(bytes);

                ImageEntity imageEntity = new ImageEntity();
                imageEntity.setDate(new Date());
                imageEntity.setImageName(file.getName() + UIUtils.appendToImage());
                imageEntity.setImagePath(file.getPath());

                imageDao.insertImage(imageEntity);

                uploadWithTransferUtility(file);


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
        });

        observable.subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe();
    }

    public void uploadWithTransferUtility(File file) {
        AmazonS3Client s3 = new AmazonS3Client(new BasicAWSCredentials(application.getString(R.string.AWS_ACCESS_KEY),
                application.getString(R.string.AWS_SECRET_KEY)));
        TransferUtility transferUtility =
                TransferUtility.builder().context(application).awsConfiguration(AWSMobileClient.getInstance().
                        getConfiguration()).s3Client(s3).build();

        TransferObserver uploadObserver = transferUtility.upload("device-inventory-media/media", file);

        uploadObserver.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                if (state == TransferState.COMPLETED) {
                    Log.d("*****UPLOAD","COMPLETED::::");
                }

                Log.d("*****UPLOAD","UPLOAD MESSAGE OK");
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                Log.d("*****UPLOAD","UPLOAD MESSAGE PROGRESS");
            }

            @Override
            public void onError(int id, Exception ex) {
                Log.e("*****UPLOAD","UPLOAD MESSAGE ERROR" + ex.getMessage());
            }
        });
    }

}