package com.suiyi.main.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.androidkun.xtablayout.XTabLayout
import com.suiyi.main.R
import com.suiyi.main.SHApplication
import com.example.base.constants.Path

/**
 * 主要测试 LayoutInflater 在 传入 activity Context 和 Application context 的区别
 *
 */
@Route(path = Path.CONTEXT_TEST_ACTIVITY)
class ContextTestActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = LayoutInflater.from(SHApplication.instance()).inflate(R.layout.activity_context_test, null, false)
        setContentView(view)

        val tabLayout : XTabLayout = findViewById(R.id.tab_layout)

        for(index in 1..10) {
            val tab = tabLayout.newTab();
            tab.text = "标题$index"
            tabLayout.addTab(tab)
        }
    }
}