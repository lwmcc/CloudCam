package com.mccarty.cloudcam.ui.camera;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.mccarty.cloudcam.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static dagger.internal.Preconditions.checkNotNull;

public class CameraFragment extends Fragment implements CameraContract.View {

    @BindView(R.id.takeImageButton)
    ImageButton takePicture;

    @BindView(R.id.switchCameraButton)
    ImageButton switchCamera;

    private CameraContract.Presenter presenter;

    private final static String TAG = CameraFragment.class.getSimpleName();

    private OnFragmentInteractionListener mListener;

    private Unbinder unbinder;

    public CameraFragment() {
        // Requires empty public constructor
    }

    public static CameraFragment newInstance() {
        return new CameraFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            // mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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

    @Override
    public void setPresenter(@NonNull CameraContract.Presenter camPresenter) {
        presenter = checkNotNull(camPresenter);
    }

    @Override
    public void switchCameraButtonPressed() {
        Log.d(TAG, "SWITCH CAM BUTTON PRESSED");
    }

    @Override
    public void takePictureButtonPressed() {
        Log.d(TAG, "CAM BUTTON PRESSED");
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @OnClick(R.id.takeImageButton)
    public void takeImage() {
        presenter.captureImage();
    }

    @OnClick(R.id.switchCameraButton)
    public void switchCamera() {
        presenter.switchCamera();
    }

    @Override
    public void hasInternetConnection() {
        // TODO:
        // Move check to manifest
    }

}
