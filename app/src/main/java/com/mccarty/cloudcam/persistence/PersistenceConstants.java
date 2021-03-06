package com.mccarty.cloudcam.persistence;

import com.mccarty.cloudcam.R;

public class PersistenceConstants {

    private PersistenceConstants() {}

    public static final String PREFERENCE_NAME = R.string.app_name + "_preferences";

    public static final String NUMBER_OF_CAMERAS = "number_of_cameras";

    public static final String CAMERA_ID = "camera_id";

    public static final int CAMERA_FIRST_RUN = -1;

    public static final String DEFAULT_CAMERA_ID = "0";

    public static final String CLOUD_CAM_BUCKET = "cloud-cam-media";

    public static final String INSERT_ENTITY = "insert_entity";

    public static final String IMAGE_DOWNLOAD_SAVE = "image_download_save";
}
