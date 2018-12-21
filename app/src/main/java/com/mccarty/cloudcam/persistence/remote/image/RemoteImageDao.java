package com.mccarty.cloudcam.persistence.remote.image;

import android.util.Log;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobileconnectors.dynamodbv2.document.Table;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.mccarty.cloudcam.utils.Constants.IMAGES_TABLE;

public class RemoteImageDao implements RemoteDao {
    @Override
    public void saveImage(Document document) {
        Observable<Void> observable = Observable.create(o -> {

            // TODO: inject this
            AmazonDynamoDBClient dbClient = new AmazonDynamoDBClient(IdentityManager.getDefaultIdentityManager().getCredentialsProvider().getCredentials());
            Table table = Table.loadTable(dbClient, IMAGES_TABLE);
            table.putItem(document);
        });
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();
    }

    @Override
    public void getImages() {
        Observable<Void> observable = Observable.create(o -> {

            // TODO: inject this
            AmazonDynamoDBClient dbClient = new AmazonDynamoDBClient(IdentityManager.getDefaultIdentityManager().
                    getCredentialsProvider().getCredentials());

            ScanRequest scanRequest = new ScanRequest().withTableName("images").
                    withFilterExpression("user_name =" + "larry").withProjectionExpression("image_name");
            ScanResult scanResult = dbClient.scan(scanRequest);

            for (Map<String, AttributeValue> items : scanResult.getItems()) {
                items.forEach((k, v) -> Log.d("", "ITEMSSSS: " + v.getS()));
            }
        });
        observable.subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe();
    }
}