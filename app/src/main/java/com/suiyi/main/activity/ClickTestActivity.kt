package com.suiyi.main.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.base.constants.Path
import com.suiyi.main.R
import kotlinx.android.synthetic.main.activity_click_test.*


/**
 * 在实践中发现，在 RV 中，如果在图片上进行滑动，
 * 当手指离开屏幕时，也会触发图片的点击事件
 */
@Route(path = Path.CLICK_TEST_ACTIVITY)
class ClickTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_click_test)

        text?.setOnClickListener{
            Toast.makeText(this, "点击了文字", Toast.LENGTH_SHORT).show()
        }

        image?.setOnClickListener{
            Toast.makeText(this, "点击了图片", Toast.LENGTH_SHORT).show()
        }
    }
}
