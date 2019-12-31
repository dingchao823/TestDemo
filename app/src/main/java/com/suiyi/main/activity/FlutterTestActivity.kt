package com.suiyi.main.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.base.constants.Path
import io.flutter.facade.Flutter
import io.flutter.view.FlutterMain

@Route(path = Path.FLUTTER_TEST_ACTIVITY)
class FlutterTestActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FlutterMain.startInitialization(this)

        val view = Flutter.createView(this, lifecycle, "/main")
        setContentView(view)
    }
}