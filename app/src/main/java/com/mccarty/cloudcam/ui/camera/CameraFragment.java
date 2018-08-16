package com.mccarty.cloudcam.ui.camera;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.mccarty.cloudcam.R;
import com.mccarty.cloudcam.model.CameraModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static dagger.internal.Preconditions.checkNotNull;

public class CameraFragment extends Fragment implements CameraView {


    private CameraPresenterImpl presenter;

    @Inject
    CameraModel model;
    /*
    @Inject
    Context context;

    @Inject
    CameraManager cameraManager;*/

    @BindView(R.id.takeImageButton)
    ImageButton takePicture;

    @BindView(R.id.switchCameraButton)
    ImageButton switchCamera;

    //@BindView(R.id.textureView)
    //AutoFitTextureView textureView;

    /*private CameraCaptureSession captureSession;

    private CameraDevice cameraDevice;

    private int numberOfCameras;
    private int sensorOrientation;
    private static final int STATE_PREVIEW = 0;
    private static final int STATE_WAITING_LOCK = 1;
    private static final int STATE_PICTURE_TAKEN = 4;
    private static final int STATE_WAITING_PRECAPTURE = 2;
    private static final int STATE_WAITING_NON_PRECAPTURE = 3;
    private int state = STATE_PREVIEW;

    private static final int MAX_PREVIEW_WIDTH = 1920;
    private static final int MAX_PREVIEW_HEIGHT = 1080;

    private static final String IMAGE_NAME = "pic.jpg";

    private String cameraId;

    private Size previewSize;

    private Semaphore cameraOpenCloseLock = new Semaphore(1);

    private boolean flashSupported;

    private CaptureRequest previewRequest;

    private CaptureRequest.Builder previewRequestBuilder;

    //private CameraContract.Presenter presenter;
*/
    private final static String TAG = CameraFragment.class.getSimpleName();

    //private OnFragmentInteractionListener mListener;

    private Unbinder unbinder;

    //private ImageReader imageReader;

   // private Handler backgroundHandler;

   // private HandlerThread backgroundThread;

   // private File file;

    /**
     * Conversion from screen rotation to JPEG orientation.
     */


    public CameraFragment() {
        // Requires empty public constructor
    }

    public static CameraFragment newInstance() {
        return new CameraFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CameraPresenterImpl(this, model);
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

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onAttach(Context context) {
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
        // TODO: switch camera
    }

    @Override
    public void hasInternetConnection() {

    }

}
