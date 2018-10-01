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

    public static float getThumbnailHeight(Resources resources) {
        int div = 3;
        float percent = 0.93f;
        float density = 160f;
        float dp;
        if (resources.getConfiguration().orientation == 1) {
            dp = (resources.getDisplayMetrics().widthPixels / (resources.getDisplayMetrics().densityDpi / density) / div) * percent;
        } else {
            dp = (resources.getDisplayMetrics().heightPixels / (resources.getDisplayMetrics().densityDpi / density) / div)  * percent;
        }

        return dp;
    }

}
