package com.mccarty.cloudcam.ui.camera;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mccarty.cloudcam.R;
import com.mccarty.cloudcam.di.component.ActivityScope;
import com.mccarty.cloudcam.model.CameraModel;
import com.mccarty.cloudcam.model.api.CameraAPI;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.AndroidInjection;

@ActivityScope
public class CameraFragment extends Fragment implements CameraView {

    private final static String TAG = CameraFragment.class.getSimpleName();

    @Inject
    CameraPresenterImpl presenter;

    @BindView(R.id.takeImageButton)
    ImageButton takePicture;

    @BindView(R.id.switchCameraButton)
    ImageButton switchCamera;

    private Unbinder unbinder;

    public CameraFragment() {
        // Requires empty public constructor
    }

    public static CameraFragment newInstance() {
        return new CameraFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.takeView(this);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onAttach(Context context) {
        AndroidInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.takeImageButton)
    @Override
    public void cameraImageButtonClicked() {
        presenter.takePicture();
    }

    @OnClick(R.id.switchCameraButton)
    @Override
    public void switchCameraButtonClicked() {
        presenter.switchCamera();
    }

    @Override
    public void hasInternetConnection() {

    }

}
