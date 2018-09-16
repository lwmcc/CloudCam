package com.mccarty.cloudcam.ui.camera;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.mccarty.cloudcam.R;
import com.mccarty.cloudcam.ui.base.BaseActivity;

import javax.inject.Inject;

import dagger.Lazy;
import dagger.android.AndroidInjection;

public class CameraActivity extends BaseActivity {

    @Inject
    Lazy<CameraFragment> fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        CameraFragment cameraFragment =
                (CameraFragment) getFragmentManager().findFragmentById(R.id.camera_fragment);

        if (cameraFragment == null) {
            cameraFragment = fragment.get();

            FragmentManager manager = getFragmentManager();
            FragmentTransaction trans = manager.beginTransaction();

            trans.add(R.id.camera_fragment, cameraFragment);
            trans.commit();
        }
    }

}