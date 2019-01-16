package com.mccarty.cloudcam.ui.imageview

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mccarty.cloudcam.R
import com.mccarty.cloudcam.di.component.ActivityScope
import com.mccarty.cloudcam.persistence.local.Image.ImageEntity
import com.mccarty.cloudcam.utils.Constants.ENTITY_LIST
import com.mccarty.cloudcam.utils.Constants.POSITION
import dagger.android.DaggerFragment
import java.util.ArrayList

@ActivityScope
class ImageViewFragment : DaggerFragment() {

    var images: List<ImageEntity>? = null
    var position: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            images = it.getParcelableArrayList(ENTITY_LIST)
            position = it.getInt(POSITION)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_image_view, container, false)
    }

   /* override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }*/

}