package com.mccarty.cloudcam.persistence.local;

public interface BasePreferences {

    int getNumberofCameras();

    void setNumberOfCameras(int numOfCameras);

    String getCameraId();

    void setCameraId(String CameraId);
}
