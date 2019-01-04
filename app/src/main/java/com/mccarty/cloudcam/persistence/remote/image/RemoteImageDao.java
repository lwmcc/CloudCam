package com.mccarty.cloudcam.persistence.remote.image;

import android.app.Application;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.document.Expression;
import com.amazonaws.mobileconnectors.dynamodbv2.document.Table;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Primitive;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.google.gson.Gson;
import com.mccarty.cloudcam.persistence.AWSJSONDocument;
import com.mccarty.cloudcam.persistence.local.Image.ImageDao;
import com.mccarty.cloudcam.persistence.local.Image.ImageEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

import static com.mccarty.cloudcam.persistence.PersistenceConstants.CLOUD_CAM_BUCKET;
import static com.mccarty.cloudcam.persistence.PersistenceConstants.IMAGE_DOWNLOAD_SAVE;
import static com.mccarty.cloudcam.persistence.PersistenceConstants.INSERT_ENTITY;
import static com.mccarty.cloudcam.utils.Constants.DATE_TIME;
import static com.mccarty.cloudcam.utils.Constants.IMAGES_TABLE;
import static com.mccarty.cloudcam.utils.Constants.IMAGE_NAME;
import static com.mccarty.cloudcam.utils.Constants.IMAGE_URI;

public class RemoteImageDao implements RemoteDao {

    private final Application application;
    private final TransferUtility transferUtility;
    private final ImageDao imageDao;

    public RemoteImageDao(Application application, TransferUtility transferUtility, ImageDao imageDao) {
        this.application = application;
        this.transferUtility = transferUtility;
        this.imageDao = imageDao;
    }

    @Override
    public void saveImage(Document document) {
        Completable.create(e -> {
            Table table = Table.loadTable(dbClient(), IMAGES_TABLE);
            table.putItem(document);
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    @Override
    public void getImages() {
        Table imagesTable = Table.loadTable(dbClient(), IMAGES_TABLE);

        imagesTable.query(new Primitive(credentials().getCachedIdentityId())).getAllResults();

        final Expression expression = new Expression();
        expression.setExpressionStatement("user_id = :id");
        expression.withExpressionAttibuteValues(":id", new Primitive("larry"));

        downloadFromS3(convertDocsToEntities(imagesTable.scan(expression).getAllResults()));
    }

    private AmazonDynamoDBClient dbClient() {
        return new AmazonDynamoDBClient(IdentityManager.getDefaultIdentityManager().getCredentialsProvider().getCredentials());
    }

    private CognitoCachingCredentialsProvider credentials() {
        return new CognitoCachingCredentialsProvider(this.application.getApplicationContext(),
                AWSMobileClient.getInstance().getConfiguration());
    }

    private List<ImageEntity> convertDocsToEntities(List<Document> images) {

        if (images.isEmpty()) {
            return Collections.emptyList();
        }

        List<ImageEntity> entities = new ArrayList<>();

        images.forEach(img -> {
            ImageEntity entity = new ImageEntity();

            try {
                JSONObject jsonObj = new JSONObject(img.toString());

                String imageName = jsonObj.get(IMAGE_NAME).toString();
                String imagePath = jsonObj.get(IMAGE_URI).toString();

                String dateTime = jsonObj.get(DATE_TIME).toString();

                AWSJSONDocument docImageName = new Gson().fromJson(imageName, AWSJSONDocument.class);
                AWSJSONDocument docImageURi = new Gson().fromJson(imagePath, AWSJSONDocument.class);
                AWSJSONDocument docDateTime = new Gson().fromJson(dateTime, AWSJSONDocument.class);

                Optional<String> optImageName = Optional.ofNullable(docImageName.value);
                Optional<String> optImageUri = Optional.ofNullable(docImageURi.value);
                Optional<String> optDateTime = Optional.ofNullable(docDateTime.value);

                if (optImageName.isPresent() && optImageUri.isPresent()) {
                    entity.setImageName(optImageName.get());
                    entity.setImagePath(optImageUri.get());

                    optDateTime.ifPresent(s -> entity.setDate(new Date(Long.parseLong(s))));

                    entities.add(entity);
                }
            } catch (JSONException | NumberFormatException e) {
                // TODO: do something
                System.out.println("***** ERROR: " + e.getMessage());
            }

        });

        return entities;
    }

    private void downloadFromS3(final List<ImageEntity> s3Images) {
        if (s3Images.isEmpty()) {
            return;
        }

        IntStream.range(0, s3Images.size()).forEach(index -> {
            String imageName = s3Images.get(index).getImageName();
            File file = new File(application.getExternalFilesDir(null), imageName);

            TransferObserver observer = transferUtility.download(CLOUD_CAM_BUCKET, imageName, file);
            observer.setTransferListener(new TransferListener() {
                @Override
                public void onStateChanged(int id, TransferState state) {
                    if (state == TransferState.COMPLETED) {
                        ImageEntity entity = new ImageEntity();
                        entity.setImageName(imageName);
                        entity.setImagePath(s3Images.get(index).getImagePath());
                        entity.setDate(s3Images.get(index).getDate());

                        Completable.create(e -> {
                            imageDao.insertImage(entity);
                            int size = s3Images.size() - 1;
                            if (index == size) {
                                LocalBroadcastManager manager = LocalBroadcastManager.getInstance(application);
                                Intent intent = new Intent(INSERT_ENTITY);
                                manager.sendBroadcast(intent);
                            }
                        }).subscribeOn(Schedulers.io()).subscribe();
                    }
                }

                @Override
                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                    // TODO: do something
                    //System.out.println("***** KEY START DOWNLOAD PROG: " + id);
                }

                @Override
                public void onError(int id, Exception ex) {
                    // TODO: do something
                    System.out.println("***** AN ERROR OCCURRED: " + ex);
                }
            });
        });

    }

}