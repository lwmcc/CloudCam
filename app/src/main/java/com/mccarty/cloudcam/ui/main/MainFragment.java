package com.mccarty.cloudcam.ui.main;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mccarty.cloudcam.R;
import com.mccarty.cloudcam.model.CameraModel;
import com.mccarty.cloudcam.ui.camera.CameraActivity;

import javax.inject.Inject;

public class MainFragment extends Fragment implements MainView {

    private static final String TAG = MainFragment.class.getSimpleName();

    @Inject
    MainPresenterImpl mainPresenter;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCameraButtonClicked();
            }
        });

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void startCameraButtonClicked() {
        startActivity(new Intent(getActivity(), CameraActivity.class));
    }

    @Override
    public void hasInternetConnection() {

    }
}
