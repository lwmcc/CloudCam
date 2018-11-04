package com.mccarty.cloudcam.utils;

import android.app.Application;
import android.media.Image;
import android.util.Log;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.s3.AmazonS3Client;
import com.mccarty.cloudcam.R;
import com.mccarty.cloudcam.di.module.AWSModule;
import com.mccarty.cloudcam.persistence.local.Image.ImageDao;
import com.mccarty.cloudcam.persistence.local.Image.ImageEntity;
import com.mccarty.cloudcam.persistence.remote.image.RemoteDao;
import com.mccarty.cloudcam.persistence.remote.image.RemoteImageDao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.mccarty.cloudcam.utils.Constants.AWS_IMAGE_PATH;
import static com.mccarty.cloudcam.utils.Constants.DATE_TIME;
import static com.mccarty.cloudcam.utils.Constants.ID;
import static com.mccarty.cloudcam.utils.Constants.IMAGE_NAME;
import static com.mccarty.cloudcam.utils.Constants.IMAGE_URI;
import static com.mccarty.cloudcam.utils.Constants.USER_ID;

public class ImageSaver {

    private final Image image;
    private final File file;
    private final ImageDao imageDao;
    private final NetworkUtils networkUtils;
    private final TransferUtility transferUtility;
    private final CognitoUserPool cognitoUserPool;

    public ImageSaver(Image image, File file, ImageDao imageDao, NetworkUtils networkUtils,
                      TransferUtility transferUtility, CognitoUserPool cognitoUserPool) {
        this.image = image;
        this.file = file;
        this.imageDao = imageDao;
        this.networkUtils = networkUtils;
        this.transferUtility = transferUtility;
        this.cognitoUserPool = cognitoUserPool;
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
                imageEntity.setImageName(file.getName());
                imageEntity.setImagePath(file.getPath());

                imageDao.insertImage(imageEntity);

                if (networkUtils.hasNetworkAccess()) {
                    uploadWithTransferUtility(file, file.getName(), file.getPath());
                }
            } catch (IOException e) {
                Log.e("", "ERROR IN OBSERVER: " + e.getMessage());
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

    public void uploadWithTransferUtility(File file, String imageName, String imagePath) {
        TransferObserver uploadObserver = transferUtility.upload(AWS_IMAGE_PATH + imageName, file);
        uploadObserver.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                if (state == TransferState.COMPLETED) {
                    Document document = new Document();
                    document.put(ID, UUID.randomUUID().toString());
                    document.put(USER_ID, cognitoUserPool.getCurrentUser().getUserId());
                    document.put(IMAGE_URI, imagePath);
                    document.put(IMAGE_NAME, imageName);
                    document.put(DATE_TIME, Instant.now().toString());

                    RemoteDao imageDao = new RemoteImageDao();
                    imageDao.saveImage(document);
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                Log.d("*****UPLOAD", "UPLOAD MESSAGE PROGRESS");
            }

            @Override
            public void onError(int id, Exception ex) {
                Log.e("*****UPLOAD", "UPLOAD MESSAGE ERROR" + ex.getMessage());
            }
        });

    }

}