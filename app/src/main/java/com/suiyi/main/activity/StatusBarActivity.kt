package com.suiyi.main.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.base.constants.Path
import com.suiyi.main.R
import com.suiyi.main.utils.StatusBarUtil

@Route(path = Path.STATUS_BAR_ACTIVITY)
class StatusBarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status_bar)

        StatusBarUtil.setLightMode(this)
    }
}
