package com.mccarty.cloudcam.ui.main;

import com.mccarty.cloudcam.ui.base.BasePresenter;
import com.mccarty.cloudcam.ui.base.BaseView;

/**
 * Created by Larry McCarty on 3/26/2018.
 */

public interface MainContract {
    interface View extends BaseView<Presenter> {
        //void navigateToCamera();
    }

    interface Presenter extends BasePresenter {

    }
}
