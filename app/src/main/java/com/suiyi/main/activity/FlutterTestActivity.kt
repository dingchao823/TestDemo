package com.suiyi.main.activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.suiyi.main.constants.Path
import io.flutter.app.FlutterActivity
import io.flutter.facade.Flutter
import io.flutter.view.FlutterMain
import io.flutter.view.FlutterNativeView
import io.flutter.view.FlutterView

@Route(path = Path.FLUTTER_TEST_ACTIVITY)
class FlutterTestActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FlutterMain.startInitialization(this)

        val view = Flutter.createView(this, lifecycle, "/main")
        setContentView(view)
    }
}