package com.mccarty.cloudcam.ui.imageview

import android.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.mccarty.cloudcam.R
import com.mccarty.cloudcam.ui.base.BaseActivity
import com.mccarty.cloudcam.ui.main.MainFragment
import dagger.Lazy
import dagger.android.AndroidInjection
import javax.inject.Inject

class ImageView2Activity : BaseActivity() {

    //@Inject
    //var fragment: Lazy<ImageViewFragment>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_view2)

        // var frag: ImageViewFragment? = fragmentManager.findFragmentById(R.id.main_fragment) as ImageViewFragment

        //if (frag == null) {
        //frag = com.mccarty.cloudcam.ui.imageview.ImageViewFragment?.get()

        //val manager = fragmentManager
        //manager.beginTransaction().add(R.id.main_fragment, frag).commit()
        //}
    }
}
