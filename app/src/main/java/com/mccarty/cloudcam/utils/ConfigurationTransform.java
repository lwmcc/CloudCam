package com.mccarty.cloudcam.utils;

import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.Size;
import android.view.Surface;

import javax.inject.Inject;

/**
 * Created by Larry McCarty on 4/8/2018.
 */

public class ConfigurationTransform {

    private final int width;
    private final int height;
    private final Activity activity;
    private final Size size;

    @Inject
    public ConfigurationTransform(int width, int height, Activity activity, Size size) {
        this.width = width;
        this.height = height;
        this.activity = activity;
        this.size = size;
    }

    //public Matrix configureTransform(int width, int height, Activity activity, Size size) {
    public Matrix configureTransform() {
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        Matrix matrix = new Matrix();
        RectF viewRect = new RectF(0, 0, width, height);
        RectF bufferRect = new RectF(0, 0, size.getHeight(), size.getWidth());
        float centerX = viewRect.centerX();
        float centerY = viewRect.centerY();
        if (Surface.ROTATION_90 == rotation || Surface.ROTATION_270 == rotation) {
            bufferRect.offset(centerX - bufferRect.centerX(), centerY - bufferRect.centerY());
            matrix.setRectToRect(viewRect, bufferRect, Matrix.ScaleToFit.FILL);
            float scale = Math.max(
                    (float) height / size.getHeight(),
                    (float) width / size.getWidth());
            matrix.postScale(scale, scale, centerX, centerY);
            matrix.postRotate(90 * (rotation - 2), centerX, centerY);
        } else if (Surface.ROTATION_180 == rotation) {
            matrix.postRotate(180, centerX, centerY);
        }
        return matrix;
    }
}
