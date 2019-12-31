package com.suiyi.main.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.suiyi.main.R
import com.example.base.constants.Path

@Route(path = Path.CIRCLE_RECTANGLE_ACTIVITY)
class CircleRectangleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_circle_rectangle)


    }
}
