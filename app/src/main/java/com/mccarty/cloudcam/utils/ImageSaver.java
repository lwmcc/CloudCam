package com.mccarty.cloudcam.utils;

import android.media.Image;
import android.util.Log;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.mccarty.cloudcam.persistence.local.Image.ImageDao;
import com.mccarty.cloudcam.persistence.local.Image.ImageEntity;
import com.mccarty.cloudcam.persistence.remote.image.RemoteImageDao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.UUID;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

import static com.mccarty.cloudcam.persistence.PersistenceConstants.CLOUD_CAM_BUCKET;
import static com.mccarty.cloudcam.utils.Constants.DATE_TIME;
import static com.mccarty.cloudcam.utils.Constants.ID;
import static com.mccarty.cloudcam.utils.Constants.IMAGE_NAME;
import static com.mccarty.cloudcam.utils.Constants.IMAGE_URI;
import static com.mccarty.cloudcam.utils.Constants.USER_ID;

public class ImageSaver {

    private final Image image;
    private final File file;
    private final ImageDao imageDao;
    private final RemoteImageDao remoteImageDao;
    private final NetworkUtils networkUtils;
    private final TransferUtility transferUtility;
    private final CognitoUserPool cognitoUserPool;

    public ImageSaver(Image image, File file, ImageDao imageDao, RemoteImageDao remoteImageDao,
                      NetworkUtils networkUtils, TransferUtility transferUtility, CognitoUserPool cognitoUserPool) {
        this.image = image;
        this.file = file;
        this.imageDao = imageDao;
        this.remoteImageDao = remoteImageDao;
        this.networkUtils = networkUtils;
        this.transferUtility = transferUtility;
        this.cognitoUserPool = cognitoUserPool;

        System.out.println("***** SIGNED IN CAM: " + IdentityManager.getDefaultIdentityManager().isUserSignedIn());
    }

    public void saveImage() {
        Date date = new Date();
        Completable.create(e -> {
            ByteBuffer buffer = image.getPlanes()[0].getBuffer();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            FileOutputStream output = null;

            try {
                output = new FileOutputStream(file);
                output.write(bytes);
                imageDao.insertImage(getImageEntity(date, file.getName(), file.getPath()));
            } catch (IOException ioe) {
                Log.e("", "ERROR IN OBSERVER: " + ioe.getMessage());
            } finally {
                image.close();
                if (null != output) {
                    try {
                        output.close();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }

            if (!e.isDisposed()) {
                e.onComplete();
            }
        }).andThen(uploadImage(date)).subscribeOn(Schedulers.io()).subscribe();
    }

    private Completable uploadImage(Date date) {
        return Completable.create(e -> {
            if (networkUtils.hasNetworkAccess() && IdentityManager.getDefaultIdentityManager().isUserSignedIn()) {
                uploadWithTransferUtility(file, file.getName(), file.getPath(), date);
            }

            if (!e.isDisposed()) {
                e.onComplete();
            }
        });
    }

    private Document getImageDocument(final String imageName, final String imagePath, final String id,
                                      final String userId, final Date date) {
        Document document = new Document();
        document.put(ID, id);
        document.put(USER_ID, userId);
        document.put(IMAGE_URI, imagePath);
        document.put(IMAGE_NAME, imageName);
        document.put(DATE_TIME, date.getTime());

        return document;
    }

    private ImageEntity getImageEntity(final Date date, final String name, final String path) {
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setDate(date);
        imageEntity.setImageName(name);
        imageEntity.setImagePath(path);
        return imageEntity;
    }

    private void uploadWithTransferUtility(File file, String imageName, String imagePath, Date date) {
        TransferObserver uploadObserver = transferUtility.upload(CLOUD_CAM_BUCKET, imageName, file);
        uploadObserver.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                if (state == TransferState.COMPLETED) {
                    remoteImageDao.saveImage(getImageDocument(imageName, imagePath, UUID.randomUUID().toString(),
                            cognitoUserPool.getCurrentUser().getUserId(), date));
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