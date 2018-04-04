package com.mccarty.cloudcam.utils;

import android.app.Application;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.util.Log;

import com.mccarty.cloudcam.di.module.AppModule;

import javax.inject.Inject;

import static android.content.Context.CAMERA_SERVICE;

/**
 * Created by Larry McCarty on 3/29/2018.
 */

public class CameraAPI {

    private static final String TAG = CameraAPI.class.getSimpleName();

    @Inject
    Context context;

    //@Inject
    //CameraManager manager;

    public void setUpCameraOutput() {
        Log.d(TAG,"ENTER setUpCAmeraOutput");

        CameraManager manager =
                (CameraManager) context.getSystemService(CAMERA_SERVICE);

      /*  try {
            for (String cameraId : manager.getCameraIdList()) {

                Log.d(TAG,"CAMERA IDS: " + cameraId);

                CameraCharacteristics characteristics
                        = manager.getCameraCharacteristics(cameraId);

                Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);


                Log.d(TAG, "FACING: " + facing);

                //int facing2 = characteristics.get(CameraCharacteristics.LENS_FACING_FRONT);


            }
        } catch (CameraAccessException e) {
            Log.e(TAG,"ACCESS ERROR: " + e.getMessage());
        } catch (NullPointerException e) {
            Log.e(TAG,"NULL POINTER ERROR: " + e.getMessage());
        }*/

    }
}