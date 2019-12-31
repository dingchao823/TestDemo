package com.suiyi.main.activity

import android.app.Activity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.suiyi.main.R
import com.example.base.constants.Path
import com.suiyi.main.widget.MyInsetDrawable
import kotlinx.android.synthetic.main.activity_default_drawable.*

@Route(path = Path.DRAWABLE_ACTIVITY)
class DrawableActivity : Activity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_default_drawable)

        getDrawable(R.drawable.ic_default_picture)?.let {
            image_100_100.setImageDrawable(MyInsetDrawable(it))
        }
        getDrawable(R.drawable.ic_default_picture)?.let {
            image_167_255.setImageDrawable(MyInsetDrawable(it))
        }
        getDrawable(R.drawable.ic_default_picture)?.let {
            image_139_139.setImageDrawable(MyInsetDrawable(it))
        }
        getDrawable(R.drawable.ic_default_picture)?.let {
            image_95_95.setImageDrawable(MyInsetDrawable(it))
        }
    }
}