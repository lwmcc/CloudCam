package com.mccarty.cloudcam.persistence;

import javax.inject.Singleton;

@Singleton
public class AppDataManager implements BaseDataManager{
    @Override
    public int getNumberofCameras() {
        return 0;
    }

    @Override
    public void setNumberOfCameras(int numOfCameras) {

    }

    @Override
    public String getCameraId() {
        return "0";
    }

    @Override
    public void setCameraId(String cameraId) {

    }
}
