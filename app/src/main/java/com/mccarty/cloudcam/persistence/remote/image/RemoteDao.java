package com.mccarty.cloudcam.persistence.remote.image;

import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;

/**
 * Created by Larry McCarty on 11/3/2018.
 */

public interface RemoteDao {
    void saveImage(Document document);
}
