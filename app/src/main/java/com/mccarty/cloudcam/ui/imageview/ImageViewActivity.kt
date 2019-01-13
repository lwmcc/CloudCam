package com.mccarty.cloudcam.ui.imageview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mccarty.cloudcam.R
import dagger.Lazy
import dagger.android.AndroidInjection
import javax.inject.Inject

class ImageViewActivity : AppCompatActivity() {

    @Inject
    var fragment: Lazy<ImageViewFragment>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_image_view)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var imageViewFragment: ImageViewFragment? = fragmentManager.findFragmentById(R.id.main_fragment) as ImageViewFragment

        if (imageViewFragment == null) {
            imageViewFragment = fragment?.get()

            val manager = fragmentManager
            val trans = manager.beginTransaction()
            trans.add(R.id.main_fragment, imageViewFragment)
            trans.commit()
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        // TODO: do something
    }

}