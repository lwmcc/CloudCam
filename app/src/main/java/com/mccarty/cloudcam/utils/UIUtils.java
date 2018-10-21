package com.mccarty.cloudcam.utils;

import android.content.res.Resources;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.mccarty.cloudcam.di.module.ModuleConstants.JPEG_EXTENSION;

public class UIUtils {

    public static String appendToImage() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");
        return now.format(formatter) + JPEG_EXTENSION;
    }

    public static int getThumbnailHeightPx(Resources resources) {
        float dp;
        if (resources.getConfiguration().orientation == 1) {
            dp = (resources.getDisplayMetrics().widthPixels / resources.getDisplayMetrics().density) / 2.9f;
        } else {
            dp = resources.getDisplayMetrics().heightPixels / resources.getDisplayMetrics().density;
        }
        return Math.round(dp * (resources.getDisplayMetrics().densityDpi / 160f));
    }

}
