package com.mccarty.cloudcam.ui.camera;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mccarty.cloudcam.R;
import com.mccarty.cloudcam.ui.base.BaseActivity;
import com.mccarty.cloudcam.utils.CameraAPI;
import com.mccarty.cloudcam.utils.ConfigurationTransform;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class CameraActivity extends BaseActivity {

    private final static String TAG = CameraActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        FragmentManager manager = getFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();

        CameraFragment frag = new CameraFragment();

        trans.add(R.id.camera_fragment, frag);
        trans.commit();
    }

}
