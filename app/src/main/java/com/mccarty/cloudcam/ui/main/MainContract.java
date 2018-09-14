package com.mccarty.cloudcam.ui.main;

import com.mccarty.cloudcam.ui.base.BasePresenter;
import com.mccarty.cloudcam.ui.base.BaseView;

public interface MainContract {
    interface View extends BaseView<Presenter> {
        void loadImages();
    }
    interface Presenter extends BasePresenter<View> {
        void getAllImages();
    }
}
