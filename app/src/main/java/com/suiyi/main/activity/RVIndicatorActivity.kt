package com.suiyi.main.activity

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.suiyi.main.R
import com.suiyi.main.adapter.NormalRecyclerAdapter
import com.example.base.constants.Path
import com.suiyi.main.utils.DimenUtils
import kotlinx.android.synthetic.main.activity_rv_indicator.*

@Route(path = Path.RECYCLER_INDICATOR)
class RVIndicatorActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_rv_indicator)

        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler_view.adapter = NormalRecyclerAdapter(this)

        home_indicator.attachToRecyclerView(recycler_view, DimenUtils.dipTopx(this, (20 * 110).toFloat()))
    }
}