package com.mccarty.cloudcam.persistence.remote.image;

import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;
import com.mccarty.cloudcam.persistence.local.Image.ImageEntity;

import java.util.List;

/**
 * Created by Larry McCarty on 11/3/2018.
 */

public interface RemoteDao {
    void saveImage(Document document);
    List<ImageEntity> getImages();
}
