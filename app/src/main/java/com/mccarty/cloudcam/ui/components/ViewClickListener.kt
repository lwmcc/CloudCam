package com.mccarty.cloudcam.ui.components

import android.view.View

/**
 * Created by Larry McCarty on 1/8/2019.
 */
@FunctionalInterface
public interface ViewClickListener {
    fun onClick(view: View, position: Int)
}