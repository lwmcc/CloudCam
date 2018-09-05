package com.mccarty.cloudcam.persistence.local;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.mccarty.cloudcam.R;

import javax.inject.Inject;

import static com.mccarty.cloudcam.persistence.PersistenceConstants.CAMERA_FIRST_RUN;
import static com.mccarty.cloudcam.persistence.PersistenceConstants.CAMERA_ID;
import static com.mccarty.cloudcam.persistence.PersistenceConstants.DEFAULT_CAMERA_ID;
import static com.mccarty.cloudcam.persistence.PersistenceConstants.NUMBER_OF_CAMERAS;
import static com.mccarty.cloudcam.persistence.PersistenceConstants.PREFERENCE_NAME;

public class AppPreferences implements BasePreferences {

    private SharedPreferences prefs;
    private Application application;

    @Inject
    public AppPreferences(Application application) {
        this.application = application;
        prefs = application.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public int getNumberofCameras() {
        return prefs.getInt(NUMBER_OF_CAMERAS, CAMERA_FIRST_RUN);
    }

    @Override
    public void setNumberOfCameras(int numOfCameras) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(NUMBER_OF_CAMERAS, numOfCameras);
        editor.apply();
    }

    @Override
    public String getCameraId() {
        return prefs.getString(CAMERA_ID, DEFAULT_CAMERA_ID);
    }

    @Override
    public void setCameraId(String cameraId) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(CAMERA_ID, cameraId);
        editor.apply();
    }
}
