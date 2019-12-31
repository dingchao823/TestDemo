package com.suiyi.main.activity

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.suiyi.main.R
import com.suiyi.main.adapter.SimpleRecyclerAdapter
import com.example.base.constants.Path
import kotlinx.android.synthetic.main.activity_scroller.*

@Route(path = Path.SCROLLER_ACTIVITY)
class ScrollerActivity : Activity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_scroller)

        recycler_scroller.layoutManager = LinearLayoutManager(this)
        recycler_scroller.adapter = SimpleRecyclerAdapter(this)
    }
}