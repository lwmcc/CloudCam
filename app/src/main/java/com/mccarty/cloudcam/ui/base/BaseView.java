package com.mccarty.cloudcam.ui.base;

/**
 * Created by Larry McCarty on 3/26/2018.
 */

public interface BaseView<T extends BasePresenter> {
    void hasInternetConnection();

    void setPresenter(T presenter);
}
