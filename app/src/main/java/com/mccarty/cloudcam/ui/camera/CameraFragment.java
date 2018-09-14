package com.mccarty.cloudcam.ui.camera;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.mccarty.cloudcam.R;
import com.mccarty.cloudcam.di.component.ActivityScope;
import com.mccarty.cloudcam.ui.dialogs.ConfirmationDialog;
import com.mccarty.cloudcam.utils.AutoFitTextureView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.AndroidInjection;

@ActivityScope
public class CameraFragment extends Fragment implements CameraView {

    @Inject
    CameraPresenterImpl presenter;

    @BindView(R.id.takeImageButton)
    ImageButton takePicture;

    @BindView(R.id.switchCameraButton)
    ImageButton switchCamera;

    @BindView(R.id.textureView)
    AutoFitTextureView textureView;

    private Unbinder unbinder;

    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private static final String FRAGMENT_DIALOG = "dialog";

    public CameraFragment() {
        // Requires empty public constructor
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
    public void onResume() {
        presenter.takeView(this);
        if (textureView.isAvailable()) {
            if (!hasCameraPermission()) {
                requestCameraPermission();
                return;
            }

            presenter.startThread();
            presenter.openCamera(textureView, getActivity().getWindowManager().getDefaultDisplay().getRotation());

        } else {
            textureView.setSurfaceTextureListener(surfaceTextureListener);
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        presenter.stopBackgroundThread();
        super.onPause();
    }

    @Override
    public void onAttach(Context context) {
        AndroidInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        presenter.dropView();
        super.onDestroyView();
    }

    @OnClick(R.id.takeImageButton)
    @Override
    public void cameraImageButtonClicked() {
        presenter.takePicture();
    }

    @OnClick(R.id.switchCameraButton)
    @Override
    public void switchCameraButtonClicked() {
        presenter.switchCamera(textureView, getActivity().getWindowManager().getDefaultDisplay().getRotation());
    }

    @Override
    public void setTransform(Matrix matrix) {
        textureView.setTransform(matrix);
    }

    @Override
    public void setAspectRatioLandscape(Size size) {
        textureView.setAspectRatio(
                size.getWidth(), size.getHeight());
    }

    @Override
    public void setAspectRatioPortrait(Size size) {
        textureView.setAspectRatio(
                size.getHeight(), size.getWidth());
    }

    @Override
    public void checkInternetConnection(Application application) {
        presenter.hasInternetAccess();
    }

    /**
     * {@link TextureView.SurfaceTextureListener} handles several lifecycle events on a
     * {@link TextureView}.
     */
    private final TextureView.SurfaceTextureListener surfaceTextureListener
            = new TextureView.SurfaceTextureListener() {

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture texture, int width, int height) {
            if (!hasCameraPermission()) {
                requestCameraPermission();
                return;
            }

            presenter.startThread();
            presenter.openCamera(textureView, getActivity().getWindowManager().getDefaultDisplay().getRotation());
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture texture, int width, int height) {
            presenter.configureTransform(width, height, getActivity().getWindowManager().getDefaultDisplay().getRotation());
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture texture) {
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture texture) {
        }

    };

    @Override
    public void requestCameraPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            new ConfirmationDialog().show(getChildFragmentManager(), FRAGMENT_DIALOG);
        } else {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }
    }

    @Override
    public boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

}
