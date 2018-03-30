package com.mccarty.cloudcam.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import javax.inject.Inject;

/**
 * Created by Larry McCarty on 3/28/2018.
 */

public class NetworkUtils {
    private ConnectivityManager getConnectivityManager(Context context) {
        ConnectivityManager manager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager;
    }

    public boolean hasNetworkAccess(Context context) {
        NetworkInfo activeNetwork = getConnectivityManager(context).getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}
