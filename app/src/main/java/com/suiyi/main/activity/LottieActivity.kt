package com.suiyi.main.activity

import android.app.Activity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.suiyi.main.R
import com.suiyi.main.constants.Path

@Route(path = Path.LOTTIE_ACTIVITY)
class LottieActivity : Activity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_lottie)
    }
}