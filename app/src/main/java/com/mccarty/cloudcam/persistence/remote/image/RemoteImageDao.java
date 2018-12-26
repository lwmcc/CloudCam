package com.mccarty.cloudcam.persistence.remote.image;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.document.Expression;
import com.amazonaws.mobileconnectors.dynamodbv2.document.Search;
import com.amazonaws.mobileconnectors.dynamodbv2.document.Table;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Primitive;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.mccarty.cloudcam.utils.Constants.IMAGES_TABLE;

public class RemoteImageDao implements RemoteDao {

    private final Application application;

    public RemoteImageDao(Application application) {
        this.application = application;
    }

    @Override
    public void saveImage(Document document) {

        // TODO: injected object is nul make a helper method
        Observable<Void> observable = Observable.create(o -> {
            Table table = Table.loadTable(dbClient(), IMAGES_TABLE);
            table.putItem(document);
        });
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();
    }

    @Override
    public void getImages() {
        Observable<Void> observable = Observable.create(o -> {
            Table imagesTable = Table.loadTable(dbClient(), IMAGES_TABLE);

            imagesTable.query(new Primitive(credentials().getCachedIdentityId())).getAllResults();

            final Expression expression = new Expression();
            expression.setExpressionStatement("user_id = :id");
            expression.withExpressionAttibuteValues(":id", new Primitive("larry"));

            Search searchResult = imagesTable.scan(expression);

            List<Document> results = searchResult.getAllResults();
        });

        observable.subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe();
    }

    private AmazonDynamoDBClient dbClient() {
        return new AmazonDynamoDBClient(IdentityManager.getDefaultIdentityManager().getCredentialsProvider().getCredentials());
    }

    private CognitoCachingCredentialsProvider credentials() {
        return new CognitoCachingCredentialsProvider(this.application.getApplicationContext(),
                AWSMobileClient.getInstance().getConfiguration());
    }

}