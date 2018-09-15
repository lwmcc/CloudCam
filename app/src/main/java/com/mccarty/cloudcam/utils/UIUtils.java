package com.mccarty.cloudcam.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.mccarty.cloudcam.di.module.ModuleConstants.JPEG_EXTENSION;

public class UIUtils {
    public static String appendToImage() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");
        return now.format(formatter) + JPEG_EXTENSION;
    }

}
