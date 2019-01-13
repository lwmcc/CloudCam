package com.mccarty.cloudcam.ui.imageview

import android.view.View

/**
 * Created by Larry McCarty on 1/9/2019.
 */
@FunctionalInterface
interface MainOnClick {
    fun onClick(view: View, position: Int)
}