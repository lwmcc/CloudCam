package com.mccarty.cloudcam.persistence.api;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;

import com.mccarty.cloudcam.persistence.local.AppPreferences;
import com.mccarty.cloudcam.utils.AutoFitTextureView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import javax.inject.Inject;

import static com.mccarty.cloudcam.persistence.PersistenceConstants.CAMERA_FIRST_RUN;
import static com.mccarty.cloudcam.persistence.PersistenceConstants.DEFAULT_CAMERA_ID;

public class CameraAPI {

    private static final String TAG = CameraAPI.class.getSimpleName();

    private final CameraManager cameraManager;

    private final File file;

    private final AppPreferences prefs;

    private CameraCaptureSession captureSession;

    private CaptureRequest previewRequest;

    private Size previewSize;

    private ImageReader imageReader;

    private boolean flashSupported;

    private CameraDevice cameraDevice;

    private static final int STATE_PREVIEW = 0;

    private static final int STATE_WAITING_LOCK = 1;

    private static final int STATE_WAITING_PRECAPTURE = 2;

    private static final int STATE_WAITING_NON_PRECAPTURE = 3;

    private static final int STATE_PICTURE_TAKEN = 4;

    private static final int MAX_PREVIEW_WIDTH = 1920;

    private static final int MAX_PREVIEW_HEIGHT = 1080;

    private int mState = STATE_PREVIEW;

    private int sensorOrientation;

    private int rotation;

    private int state = STATE_PREVIEW;

    private int width;

    private int height;

    private Size mPreviewSize;

    private AutoFitTextureView textureView;

    private CaptureRequest mPreviewRequest;

    private Semaphore cameraOpenCloseLock = new Semaphore(1);

    private boolean mFlashSupported;

    private CaptureRequest.Builder previewRequestBuilder;

    private HandlerThread backgroundThread;

    private Handler backgroundHandler;

    private Surface surface;

    @Inject
    public CameraAPI(CameraManager manager, File file, AppPreferences appPreferences) {
        this.cameraManager = manager;
        this.file = file;
        this.prefs = appPreferences;
    }

    /**
     * Conversion from screen rotation to JPEG orientation.
     */
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private static final String FRAGMENT_DIALOG = "dialog";

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    /**
     * Starts a background thread and its {@link Handler}.
     */
    public void startBackgroundThread() {
        backgroundThread = new HandlerThread("CameraBackground");
        backgroundThread.start();
        backgroundHandler = new Handler(backgroundThread.getLooper());
    }

    /**
     * Stops the background thread and its {@link Handler}.
     */
    private void stopBackgroundThread() {
        backgroundThread.quitSafely();
        try {
            backgroundThread.join();
            backgroundThread = null;
            backgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private final ImageReader.OnImageAvailableListener onImageAvailableListener
            = new ImageReader.OnImageAvailableListener() {
        @Override
        public void onImageAvailable(ImageReader reader) {
            backgroundHandler.post(new ImageSaver(reader.acquireNextImage(), file));
        }
    };

    private static class ImageSaver implements Runnable {
        private final Image image;
        private final File file;

        ImageSaver(Image image, File file) {
            this.image = image;
            this.file = file;
        }

        @Override
        public void run() {
            ByteBuffer buffer = image.getPlanes()[0].getBuffer();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            FileOutputStream output = null;

            try {
                output = new FileOutputStream(file);
                output.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                image.close();
                if (null != output) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public void lockFocus() {
        try {
            previewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER,
                    CameraMetadata.CONTROL_AF_TRIGGER_START);
            state = STATE_WAITING_LOCK;
            captureSession.capture(previewRequestBuilder.build(), captureCallback,
                    backgroundHandler);
        } catch (CameraAccessException e) {
            Log.e(TAG, "CAMERA ERROR: " + e.getMessage());
        }
    }

    /**
     * A {@link CameraCaptureSession.CaptureCallback} that handles events related to JPEG capture.
     */
    private CameraCaptureSession.CaptureCallback captureCallback
            = new CameraCaptureSession.CaptureCallback() {

        private void process(CaptureResult result) {
            switch (state) {
                case STATE_PREVIEW: {
                    // We have nothing to do when the camera preview is working normally.
                    break;
                }
                case STATE_WAITING_LOCK: {
                    Integer afState = result.get(CaptureResult.CONTROL_AF_STATE);
                    if (afState == null) {
                        captureStillPicture(() -> getRotation());
                    } else if (CaptureResult.CONTROL_AF_STATE_FOCUSED_LOCKED == afState ||
                            CaptureResult.CONTROL_AF_STATE_NOT_FOCUSED_LOCKED == afState) {
                        // CONTROL_AE_STATE can be null on some devices
                        Integer aeState = result.get(CaptureResult.CONTROL_AE_STATE);
                        if (aeState == null ||
                                aeState == CaptureResult.CONTROL_AE_STATE_CONVERGED) {
                            state = STATE_PICTURE_TAKEN;
                            captureStillPicture(() -> getRotation());
                        } else {
                            runPrecaptureSequence();
                        }
                    }
                    break;
                }
                case STATE_WAITING_PRECAPTURE: {
                    // CONTROL_AE_STATE can be null on some devices
                    Integer aeState = result.get(CaptureResult.CONTROL_AE_STATE);
                    if (aeState == null ||
                            aeState == CaptureResult.CONTROL_AE_STATE_PRECAPTURE ||
                            aeState == CaptureRequest.CONTROL_AE_STATE_FLASH_REQUIRED) {
                        state = STATE_WAITING_NON_PRECAPTURE;
                    }
                    break;
                }
                case STATE_WAITING_NON_PRECAPTURE: {
                    // CONTROL_AE_STATE can be null on some devices
                    Integer aeState = result.get(CaptureResult.CONTROL_AE_STATE);
                    if (aeState == null || aeState != CaptureResult.CONTROL_AE_STATE_PRECAPTURE) {
                        state = STATE_PICTURE_TAKEN;
                        captureStillPicture(() -> getRotation());
                    }
                    break;
                }
            }
        }


        @Override
        public void onCaptureProgressed(@NonNull CameraCaptureSession session,
                                        @NonNull CaptureRequest request,
                                        @NonNull CaptureResult partialResult) {
            process(partialResult);
        }

        @Override
        public void onCaptureCompleted(@NonNull CameraCaptureSession session,
                                       @NonNull CaptureRequest request,
                                       @NonNull TotalCaptureResult result) {
            process(result);
        }

    };

    private void captureStillPicture(Supplier<Integer> rotation) {

        try {
            // TODO: do something
            /*final Activity activity = getActivity();
            if (null == activity || null == cameraDevice) {
                return;
            }*/
            // This is the CaptureRequest.Builder that we use to take a picture.
            final CaptureRequest.Builder captureBuilder =
                    cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            captureBuilder.addTarget(imageReader.getSurface());

            // Use the same AE and AF modes as the preview.
            captureBuilder.set(CaptureRequest.CONTROL_AF_MODE,
                    CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            setAutoFlash(captureBuilder);

            // Orientation
            captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, getOrientation(() -> rotation.get()));

            CameraCaptureSession.CaptureCallback CaptureCallback
                    = new CameraCaptureSession.CaptureCallback() {

                @Override
                public void onCaptureCompleted(@NonNull CameraCaptureSession session,
                                               @NonNull CaptureRequest request,
                                               @NonNull TotalCaptureResult result) {
                    // TODO: do something
                    //Toast.makeText(getActivity(), "Saved: " + file, Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "*****" + file.toString());
                    unlockFocus();
                }
            };

            captureSession.stopRepeating();
            captureSession.abortCaptures();
            captureSession.capture(captureBuilder.build(), CaptureCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private int getOrientation(Supplier<Integer> rotation) {
        // Sensor orientation is 90 for most devices, or 270 for some devices (eg. Nexus 5X)
        // We have to take that into account and rotate JPEG properly.
        // For devices with orientation of 90, we simply return our mapping from ORIENTATIONS.
        // For devices with orientation of 270, we need to rotate the JPEG 180 degrees.
        return (ORIENTATIONS.get(rotation.get()) + sensorOrientation + 270) % 360;
    }

    private void runPrecaptureSequence() {
        try {
            // This is how to tell the camera to trigger.
            previewRequestBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER,
                    CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER_START);
            // Tell #mCaptureCallback to wait for the precapture sequence to be set.
            state = STATE_WAITING_PRECAPTURE;
            captureSession.capture(previewRequestBuilder.build(), captureCallback,
                    backgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void setAutoFlash(CaptureRequest.Builder requestBuilder) {
        if (flashSupported) {
            requestBuilder.set(CaptureRequest.CONTROL_AE_MODE,
                    CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
        }
    }

    @SuppressWarnings("SuspiciousNameCombination")
    private Size setupCamera(final int width, final int height) {
        try {
            //String[] ids = cameraManager.getCameraIdList();

            if (prefs.getNumberofCameras() == CAMERA_FIRST_RUN) {
                prefs.setCameraId(DEFAULT_CAMERA_ID);
                prefs.setNumberOfCameras(cameraManager.getCameraIdList().length);
            }
            //for (String cameraId : ids) {
            CameraCharacteristics characteristics
                    = cameraManager.getCameraCharacteristics(prefs.getCameraId());


            // TODO: change this
            // We don't use a front facing camera in this sample.
            //Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);

                /*if (facing != null && facing == CameraCharacteristics.LENS_FACING_FRONT) {
                    continue;
                }*/

            StreamConfigurationMap map = characteristics.get(
                    CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
               /* if (map == null) {
                    //continue;
                    return;
                }*/

            Size largest = Collections.max(
                    Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)),
                    new CompareSizesByArea());
            imageReader = ImageReader.newInstance(largest.getWidth(), largest.getHeight(),
                    ImageFormat.JPEG, /*maxImages*/ 2);
            imageReader.setOnImageAvailableListener(
                    onImageAvailableListener, backgroundHandler);

            sensorOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
            boolean swappedDimensions = false;
            switch (getRotation()) {
                case Surface.ROTATION_0:
                case Surface.ROTATION_180:
                    if (sensorOrientation == 90 || sensorOrientation == 270) {
                        swappedDimensions = true;
                    }
                    break;
                case Surface.ROTATION_90:
                case Surface.ROTATION_270:
                    if (sensorOrientation == 0 || sensorOrientation == 180) {
                        swappedDimensions = true;
                    }
                    break;
                default:
                    Log.e(TAG, "Display rotation is invalid: " + getRotation());
            }

            Point displaySize = new Point();

            int rotatedPreviewWidth = width;
            int rotatedPreviewHeight = height;
            int maxPreviewWidth = displaySize.x;
            int maxPreviewHeight = displaySize.y;

            if (swappedDimensions) {
                rotatedPreviewWidth = height;
                rotatedPreviewHeight = width;
                maxPreviewWidth = displaySize.y;
                maxPreviewHeight = displaySize.x;
            }

            if (maxPreviewWidth > MAX_PREVIEW_WIDTH) {
                maxPreviewWidth = MAX_PREVIEW_WIDTH;
            }

            if (maxPreviewHeight > MAX_PREVIEW_HEIGHT) {
                maxPreviewHeight = MAX_PREVIEW_HEIGHT;
            }

            previewSize = chooseOptimalSize(map.getOutputSizes(SurfaceTexture.class),
                    rotatedPreviewWidth, rotatedPreviewHeight, maxPreviewWidth,
                    maxPreviewHeight, largest);

            Boolean available = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
            flashSupported = available == null ? false : available;
            //}
        } catch (CameraAccessException e) {
            Log.e(TAG, "CameraAccessException: " + e.getMessage());
        } catch (NullPointerException e) {
            Log.e(TAG, "NullPointerException: " + e.getMessage());
        }

        return previewSize;
    }


    static class CompareSizesByArea implements Comparator<Size> {
        @Override
        public int compare(Size lhs, Size rhs) {
            // We cast here to ensure the multiplications won't overflow
            return Long.signum((long) lhs.getWidth() * lhs.getHeight() -
                    (long) rhs.getWidth() * rhs.getHeight());
        }
    }

    private void unlockFocus() {
        try {
            // Reset the auto-focus trigger
            previewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER,
                    CameraMetadata.CONTROL_AF_TRIGGER_CANCEL);
            setAutoFlash(previewRequestBuilder);
            captureSession.capture(previewRequestBuilder.build(), captureCallback,
                    backgroundHandler);
            // After this, the camera will go back to the normal state of preview.
            state = STATE_PREVIEW;
            captureSession.setRepeatingRequest(previewRequest, captureCallback,
                    backgroundHandler);
        } catch (CameraAccessException e) {
            Log.e(TAG, "FOCUS LOCK CAMERA ERROR: " + e.getMessage());
        }
    }

    private void createCameraPreviewSession(Surface surface) {
        try {

            // We set up a CaptureRequest.Builder with the output Surface.
            previewRequestBuilder
                    = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            previewRequestBuilder.addTarget(surface);

            // Here, we create a CameraCaptureSession for camera preview.
            cameraDevice.createCaptureSession(Arrays.asList(surface, imageReader.getSurface()),
                    new CameraCaptureSession.StateCallback() {

                        @Override
                        public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                            // The camera is already closed
                            if (null == cameraDevice) {
                                return;
                            }

                            // When the session is ready, we start displaying the preview.
                            captureSession = cameraCaptureSession;
                            try {
                                // Auto focus should be continuous for camera preview.
                                previewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE,
                                        CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                                // Flash is automatically enabled when necessary.
                                setAutoFlash(previewRequestBuilder);

                                // Finally, we start displaying the camera preview.
                                previewRequest = previewRequestBuilder.build();
                                captureSession.setRepeatingRequest(previewRequest,
                                        captureCallback, backgroundHandler);
                            } catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onConfigureFailed(
                                @NonNull CameraCaptureSession cameraCaptureSession) {
                            //Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                            // TODO: move to view
                        }
                    }, null
            );
        } catch (CameraAccessException e) {
            Log.e(TAG, "CAMERA ERROR: " + e.getMessage());
        }
    }

    private static Size chooseOptimalSize(Size[] choices, int textureViewWidth,
                                          int textureViewHeight, int maxWidth, int maxHeight, Size aspectRatio) {

        // Collect the supported resolutions that are at least as big as the preview Surface
        List<Size> bigEnough = new ArrayList<>();
        // Collect the supported resolutions that are smaller than the preview Surface
        List<Size> notBigEnough = new ArrayList<>();
        int w = aspectRatio.getWidth();
        int h = aspectRatio.getHeight();
        for (Size option : choices) {
            if (option.getWidth() <= maxWidth && option.getHeight() <= maxHeight &&
                    option.getHeight() == option.getWidth() * h / w) {
                if (option.getWidth() >= textureViewWidth &&
                        option.getHeight() >= textureViewHeight) {
                    bigEnough.add(option);
                } else {
                    notBigEnough.add(option);
                }
            }
        }

        if (bigEnough.size() > 0) {
            return Collections.min(bigEnough, new CompareSizesByArea());
        } else if (notBigEnough.size() > 0) {
            return Collections.max(notBigEnough, new CompareSizesByArea());
        } else {
            Log.e(TAG, "Couldn't find any suitable preview size");
            return choices[0];
        }
    }

    public Matrix openCamera(int width, int height) {
        this.height = height;
        this.width = width;

        Size s = setupCamera(width, height);
        Matrix matrix = configureTransform(width, height, s);
        try {
            if (!cameraOpenCloseLock.tryAcquire(2500, TimeUnit.MILLISECONDS)) {
                throw new RuntimeException("Time out waiting to lock camera opening.");
            }

            // TODO:
            Log.d(TAG,"OPEN CAMERA: " + prefs.getCameraId());

            cameraManager.openCamera(prefs.getCameraId(), stateCallback, backgroundHandler);
        } catch (CameraAccessException e) {
            Log.e(TAG, "OPEN CAMERA ERROR: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while trying to lock camera opening.", e);
        } catch (SecurityException e) {
            // This shouldn't happen because this is check in the camera fragment
            //e.printStackTrace();
            Log.e(TAG, "CAMERA SECURITY ERROR: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "**CAMERA EXCEPTION: " + e.getMessage());
        }

        return matrix;
    }

    private Matrix configureTransform(int width, int height, Size size) {

        // TODO: do something here?
        /*if (null == mTextureView || null == mPreviewSize || null == activity) {
            return;
        }*/

        Matrix matrix = new Matrix();
        RectF viewRect = new RectF(0, 0, width, height);
        RectF bufferRect = new RectF(0, 0, size.getHeight(), size.getWidth());
        float centerX = viewRect.centerX();
        float centerY = viewRect.centerY();
        if (Surface.ROTATION_90 == getRotation() || Surface.ROTATION_270 == getRotation()) {
            bufferRect.offset(centerX - bufferRect.centerX(), centerY - bufferRect.centerY());
            matrix.setRectToRect(viewRect, bufferRect, Matrix.ScaleToFit.FILL);
            float scale = Math.max(
                    (float) height / size.getHeight(),
                    (float) width / size.getWidth());
            matrix.postScale(scale, scale, centerX, centerY);
            matrix.postRotate(90 * (getRotation() - 2), centerX, centerY);
        } else if (Surface.ROTATION_180 == getRotation()) {
            matrix.postRotate(180, centerX, centerY);
        }
        return matrix;
    }

    /**
     * {@link CameraDevice.StateCallback} is called when {@link CameraDevice} changes its state.
     */
    private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {

        @Override
        public void onOpened(@NonNull CameraDevice device) {
            // This method is called when the camera is opened.  We start camera preview here.
            cameraOpenCloseLock.release();
            cameraDevice = device;
            createCameraPreviewSession(getSurface());
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice device) {
            cameraOpenCloseLock.release();
            device.close();
            cameraDevice = null;
        }

        @Override
        public void onError(@NonNull CameraDevice device, int error) {
            cameraOpenCloseLock.release();
            device.close();
            cameraDevice = null;

            // TODO: finish activity on error
            /*Activity activity = getActivity();
            if (null != activity) {
                activity.finish();
            }*/
        }

    };

    // TODO: move to view

    /**
     * Shows an error message dialog.
     */
    public static class ErrorDialog extends DialogFragment {

        private static final String ARG_MESSAGE = "message";

        public static ErrorDialog newInstance(String message) {
            ErrorDialog dialog = new ErrorDialog();
            Bundle args = new Bundle();
            args.putString(ARG_MESSAGE, message);
            dialog.setArguments(args);
            return dialog;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Activity activity = getActivity();
            return new AlertDialog.Builder(activity)
                    .setMessage(getArguments().getString(ARG_MESSAGE))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            activity.finish();
                        }
                    })
                    .create();
        }

    }

    public int getRotation() {
        return this.rotation;
    }

    public void setSurface(AutoFitTextureView textureView) {
        this.surface = new Surface(textureView.getSurfaceTexture());
    }

    public Surface getSurface() {
        return new Surface(this.textureView.getSurfaceTexture());
    }

    /**
     * Must be set before camera is opened
     *
     * @param textureView
     * @param rotation
     */
    public void setCamera(AutoFitTextureView textureView, int rotation) {
        this.textureView = textureView;
        this.rotation = rotation;
    }

    public void switchCamera(int width, int height) {
        changeCameraId();
        closeCamera();
        openCamera(width, height);
    }

    private void changeCameraId() {
        int cameraId = Integer.valueOf(prefs.getCameraId());
        if (cameraId == (prefs.getNumberofCameras() - 1)) {
            prefs.setCameraId("0");
        } else {
            cameraId += 1;
            prefs.setCameraId(String.valueOf(cameraId));
        }
    }

    /**
     * Closes the current {@link CameraDevice}.
     */
    public void closeCamera() {

        Log.d(TAG,"CLOSE CAMERA");
        try {
            cameraOpenCloseLock.acquire();
            if (null != captureSession) {
                captureSession.close();
                captureSession = null;
            }
            if (null != cameraDevice) {
                cameraDevice.close();
                cameraDevice = null;
            }
            if (null != imageReader) {
                imageReader.close();
                imageReader = null;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while trying to lock camera closing.", e);
        } finally {
            cameraOpenCloseLock.release();
        }
    }

}