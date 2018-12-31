package com.mccarty.cloudcam.di.module;

import android.app.Application;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.mccarty.cloudcam.R;
import com.mccarty.cloudcam.persistence.remote.image.RemoteImageDao;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Larry McCarty on 10/28/2018.
 */

@Module
public class AWSModule {

    @Provides
    static BasicAWSCredentials provideBasicAWSCredentials(Application application) {
        return new BasicAWSCredentials(application.getString(R.string.AWS_ACCESS_KEY),
                application.getString(R.string.AWS_SECRET_KEY));
    }

    @Provides
    static AmazonS3Client provideAmazonS3Client(BasicAWSCredentials basicAWSCredentials) {
        return new AmazonS3Client(basicAWSCredentials);
    }

    @Provides
    static TransferUtility provideTransferUtility(Application application, AmazonS3Client s3) {
        return TransferUtility.builder().context(application).awsConfiguration(AWSMobileClient.getInstance().
                getConfiguration()).s3Client(s3).build();
    }

    @Provides
    static CognitoUserPool providedCognitoUserPool(Application application) {
        return new CognitoUserPool(application, AWSMobileClient.getInstance().getConfiguration());
    }

    @Provides
    static RemoteImageDao provideRemoteImageDao(Application application, TransferUtility transferUtility) {
        return new RemoteImageDao(application, transferUtility);
    }

    @Provides
    static AWSConfiguration awsConfiguration() {
        return AWSMobileClient.getInstance().getConfiguration();
    }

}