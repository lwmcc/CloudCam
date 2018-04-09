package com.mccarty.cloudcam.utils;

import android.content.Context;
import android.widget.Toast;

import javax.inject.Inject;

/**
 * Created by Larry McCarty on 4/8/2018.
 */

public class UIUtils {
    public static void showToast(final String message, Context context) {
        Toast.makeText(context,message, Toast.LENGTH_SHORT).show();
    }

}
