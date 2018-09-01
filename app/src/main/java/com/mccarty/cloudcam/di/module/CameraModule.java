package com.mccarty.cloudcam.di.module;


import android.app.Application;
import android.content.Context;
import android.hardware.camera2.CameraManager;

import com.mccarty.cloudcam.di.component.CameraScope;
import com.mccarty.cloudcam.model.CameraModel;
import com.mccarty.cloudcam.model.api.CameraAPI;
import com.mccarty.cloudcam.ui.camera.CameraFragment;
import com.mccarty.cloudcam.ui.camera.CameraPresenterImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

//@Module
public class CameraModule {

   /* Application application;

    CameraModule(Application application) {
        this.application = application;
    }

    @Provides
    Context provideContext() {
        return application;
    }

    @Provides
    Application provideApplication() {
        return application;
    }

    @Provides
    CameraManager provideCameraManager(Context context) {
        return (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
    }

    @Provides
    CameraAPI provideCamera(CameraManager manager) {
        return new CameraAPI(manager);
    }
*/
    /*@Provides
    CameraModel provideCameraModel() {
        return new CameraModel();
    }

    @Provides
    CameraPresenterImpl provideCameraPresenterImpl(CameraModel model) {
        return new CameraPresenterImpl(model);
    }*/
}
