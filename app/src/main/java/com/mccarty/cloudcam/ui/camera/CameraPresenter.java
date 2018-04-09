package com.mccarty.cloudcam.ui.camera;

import android.app.Activity;
import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.mccarty.cloudcam.utils.CameraAPI;

import javax.inject.Inject;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Created by Larry McCarty on 3/30/2018.
 */

public class CameraPresenter implements CameraContract.Presenter {
    @Override
    public void lockFocus() {

    }

    private static final String TAG = CameraPresenter.class.getSimpleName();

    private final CameraContract.View cameraView;

    //private final Context context;

    //private final Activity activity;

    @Inject
    CameraAPI camera;

    @Inject
    public CameraPresenter(@NonNull CameraContract.View camView,
                           @NonNull CameraAPI cameraAPI)

                           //,@NonNull Activity act)
                           {
        cameraView = checkNotNull(camView, "CameraView Cannot Be Null");
        camera = checkNotNull(cameraAPI, "Camera API Cannot Be Null");
        //context = checkNotNull(con, "Context Cannot Be Null");
        //activity = checkNotNull(act, "Activity Cannot Be Null");
        cameraView.setPresenter(this);
    }

    @Override
    public void setUpCameraOutput() {
        //camera.setUpCameraOutputs();
    }

    @Override
    public void openCamera(int width, int height) {
        //camera.setUpCameraOutputs(width, height);
        //camera.openCamera(width, height, context);
    }

    @Override
    public void captureImage() {
        Log.d(TAG,"CAPTURE IMAGE");
    }

    @Override
    public void switchCamera() {
        Log.d(TAG,"SWITCH CAMERA");
    }

    @Override
    public void start() {
        Log.d(TAG,"CAMERA START");
        setupCamera();
    }

    private void setupCamera() {
        cameraView.checkTextureView();
    }


    /*@Override
    public void lockFocus() {
        cameraView.showToast("Camera Lock Focus");
       // camera.lockFocus();
    }*/
}
