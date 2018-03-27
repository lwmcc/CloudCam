package com.mccarty.cloudcam;

/**
 * Created by Larry McCarty on 3/26/2018.
 */

public interface BasePresenter<T> {
    void takeView(T view);

    void dropView();
}
