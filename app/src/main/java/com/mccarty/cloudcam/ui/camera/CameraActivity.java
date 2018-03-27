package com.mccarty.cloudcam.ui.camera;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mccarty.cloudcam.R;

import javax.inject.Inject;

public class CameraActivity extends AppCompatActivity {

    @Inject
    CameraFragment cameraFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
    }
}
