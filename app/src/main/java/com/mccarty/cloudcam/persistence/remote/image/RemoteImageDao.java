package com.mccarty.cloudcam.persistence.remote.image;

import android.util.Log;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobileconnectors.dynamodbv2.document.Table;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.mccarty.cloudcam.utils.Constants.IMAGES_TABLE;

/**
 * Created by Larry McCarty on 11/3/2018.
 */

public class RemoteImageDao implements RemoteDao {
    @Override
    public void saveImage(Document document) {
        Observable<Void> observable = Observable.create(o -> {
            AmazonDynamoDBClient dbClient = new AmazonDynamoDBClient(IdentityManager.getDefaultIdentityManager().getCredentialsProvider().getCredentials());
            Table table = Table.loadTable(dbClient, IMAGES_TABLE);
            table.putItem(document);
        });
       observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();
    }
}
