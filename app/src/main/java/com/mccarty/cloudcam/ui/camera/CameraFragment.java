package com.mccarty.cloudcam.ui.camera;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("******","HAS INTERNET ACCESS: " + presenter.hasInternetAccess());

        presenter.takeView(this);
        if (textureView.isAvailable()) {
            if (!hasCameraPermission()) {
                requestCameraPermission();
                return;
            }

            presenter.startThread();
            presenter.openCamera(textureView, getActivity());

        } else {
            textureView.setSurfaceTextureListener(surfaceTextureListener);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.stopBackgroundThread();
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
        presenter.dropView();
    }

    @OnClick(R.id.takeImageButton)
    @Override
    public void cameraImageButtonClicked() {
        presenter.takePicture();
    }

    @OnClick(R.id.switchCameraButton)
    @Override
    public void switchCameraButtonClicked() {
        presenter.switchCamera(textureView, getActivity());
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
            presenter.openCamera(textureView, getActivity());
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture texture, int width, int height) {
            //configureTransform(width, height);
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

    // TODO: move this

    /**
     * Shows OK/Cancel confirmation dialog about camera permission.
     */
    public static class ConfirmationDialog extends DialogFragment {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Fragment parent = getParentFragment();
            return new AlertDialog.Builder(getActivity())
                    .setMessage(R.string.camera_request)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            parent.requestPermissions(new String[]{Manifest.permission.CAMERA},
                                    REQUEST_CAMERA_PERMISSION);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Activity activity = parent.getActivity();
                                    if (activity != null) {
                                        activity.finish();
                                    }
                                }
                            })
                    .create();
        }
    }

    @Override
    public boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

}
