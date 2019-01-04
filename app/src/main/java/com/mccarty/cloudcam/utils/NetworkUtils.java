package com.mccarty.cloudcam.utils;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {

  private final Application application;

  public NetworkUtils(Application application) {
    this.application = application;
  }

  private ConnectivityManager getConnectivityManager() {
    return (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
  }

  public boolean hasNetworkAccess() {
    NetworkInfo activeNetwork = getConnectivityManager().getActiveNetworkInfo();
    return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
  }

}
