package com.suiyi.main.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.base.constants.Path
import com.suiyi.main.R
import com.tencent.tinker.lib.tinker.TinkerInstaller
import kotlinx.android.synthetic.main.activity_tinker_test.*

@Route(path = Path.TINKER_TEST_ACTIVITY)
class TinkerTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tinker_test)

        testOne?.setOnClickListener{

        }

        testTwo?.setOnClickListener{
            TinkerInstaller.onReceiveUpgradePatch(applicationContext,
                    Environment.getExternalStorageDirectory().absolutePath + "/patch_signed_7zip.apk")
        }
    }
}
