package com.mccarty.cloudcam.ui.camera;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mccarty.cloudcam.R;
import com.mccarty.cloudcam.utils.CameraAPI;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class CameraActivity extends AppCompatActivity implements CameraFragment.OnFragmentInteractionListener {

    private final static String TAG = CameraActivity.class.getSimpleName();

    @Inject
    CameraFragment cameraFragment;

    @Inject
    CameraAPI cameraAPI;

    private CameraPresenter cameraPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        FragmentManager manager = getFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        trans.add(R.id.camera_fragment, cameraFragment);
        trans.commit();

        cameraPresenter = new CameraPresenter(cameraAPI, cameraFragment);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
