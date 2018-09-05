package com.mccarty.cloudcam.ui.base;

public interface BasePresenter<T> {
    void takeView(T view);
    void dropView();
    void finishActivity();
}
