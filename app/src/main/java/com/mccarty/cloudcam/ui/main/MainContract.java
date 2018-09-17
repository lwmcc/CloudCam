package com.mccarty.cloudcam.ui.main;

import com.mccarty.cloudcam.ui.base.BasePresenter;
import com.mccarty.cloudcam.ui.base.BaseView;

public interface MainContract {
    interface MainView extends BaseView<MainPresenter> {
        void loadImages();
    }
    interface MainPresenter extends BasePresenter<MainView> {
        void getAllImages();
    }
}
