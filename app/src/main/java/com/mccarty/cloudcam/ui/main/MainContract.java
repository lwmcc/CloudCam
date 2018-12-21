package com.mccarty.cloudcam.ui.main;

import com.mccarty.cloudcam.persistence.local.Image.ImageEntity;
import com.mccarty.cloudcam.ui.base.BasePresenter;
import com.mccarty.cloudcam.ui.base.BaseView;

import java.util.List;

public interface MainContract {
    interface MainView extends BaseView<MainPresenter> {
        void loadImages(List<ImageEntity> images);
        void downloadRemoteImages();
        void imageClicked();
    }
    interface MainPresenter extends BasePresenter<MainView> {
        void getAllImages();
        void showImage();
        void setImagesToView(List<ImageEntity> images);
    }
}
