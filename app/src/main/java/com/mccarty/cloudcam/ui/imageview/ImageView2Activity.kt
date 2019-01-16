package com.mccarty.cloudcam.ui.imageview

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable

import com.mccarty.cloudcam.R
import com.mccarty.cloudcam.persistence.local.Image.ImageEntity
import com.mccarty.cloudcam.ui.base.BaseActivity
import com.mccarty.cloudcam.utils.Constants.ENTITY_LIST
import com.mccarty.cloudcam.utils.Constants.POSITION
import dagger.Lazy
import dagger.android.AndroidInjection
import javax.inject.Inject

class ImageView2Activity : BaseActivity() {

    internal var fragment: Lazy<ImageViewFragment>? = null
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_view2)

        val intent = intent
        val images = intent.getParcelableArrayListExtra<ImageEntity>(ENTITY_LIST)
        val position = intent.getIntExtra(POSITION, 0)

        val bundle = Bundle()
        bundle.putInt(POSITION, position)
        bundle.putParcelableArrayList(ENTITY_LIST, images)

        var frag: ImageViewFragment? = fragmentManager.findFragmentById(R.id.image_view_fragment) as? ImageViewFragment

        if (frag == null) {
            frag = fragment?.get()
            val args = Bundle()
            args.putInt(POSITION, position)
            args.putParcelableArrayList(ENTITY_LIST, images)

            frag?.arguments = args

            val manager = fragmentManager
            manager.beginTransaction().add(R.id.image_view_fragment, frag).commit()
        }
    }

}