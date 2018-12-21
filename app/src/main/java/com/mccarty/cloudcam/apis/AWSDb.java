package com.mccarty.cloudcam.apis;

import android.app.Application;
import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;

/**
 * Created by Larry McCarty on 10/28/2018.
 */

public class AWSDb {

    Application application;
    String userPoolId;
    Regions regions;

    public void AWSDb(Application application, String userPoolId, Regions regions) {
        this.application = application;
        this.userPoolId = userPoolId;
        this.regions = regions;
    }

}
