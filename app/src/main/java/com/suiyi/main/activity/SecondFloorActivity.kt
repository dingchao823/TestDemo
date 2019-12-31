package com.suiyi.main.activity

import android.app.Activity
import android.graphics.Color
import android.os.Bundle

import android.util.Log
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener
import com.suiyi.main.R
import com.example.base.constants.Path
import com.suiyi.main.utils.DimenUtils
import kotlinx.android.synthetic.main.activity_second_floor.*


/**
 * 淘宝二楼实现，借助于 SmartRefreshLayout 第三方库
 *
 * @author 0004640
 */
@Route(path = Path.SECOND_FLOOR_ACTIVITY)
class SecondFloorActivity : Activity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_second_floor)

        initRefresh()

        refreshLayout.setEnableLoadMore(false)
        refreshLayout.setEnableLoadMoreWhenContentNotFull(false)
        refreshLayout.setEnableAutoLoadMore(false)

        refreshLayout.setOnMultiPurposeListener(object : SimpleMultiPurposeListener() {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                refreshLayout.finishLoadMore(2000)
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                Toast.makeText(applicationContext, "触发刷新事件", Toast.LENGTH_SHORT).show()
                refreshLayout.finishRefresh(2000)
            }

            override fun onHeaderMoving(header: RefreshHeader?, isDragging: Boolean, percent: Float, offset: Int, headerHeight: Int, maxDragHeight: Int) {
                home_title.alpha = 1 - percent

                Log.e("dc", "")
            }

            override fun onStateChanged(refreshLayout: RefreshLayout, oldState: RefreshState, newState: RefreshState) {
                if (oldState == RefreshState.TwoLevel) {
                    second_floor_content.animate().alpha(0.toFloat()).setDuration(1000)
                }
            }
        })

        header.setOnTwoLevelListener {
            Toast.makeText(this, "触发二楼事件", Toast.LENGTH_SHORT).show()
            second_floor_content.animate().alpha(1f).setDuration(2000)
            true//true 将会展开二楼状态 false 关闭刷新
        }

        refreshLayout.setOnRefreshListener { refreshLayout ->
            Toast.makeText(applicationContext, "触发刷新事件", Toast.LENGTH_SHORT).show()
            refreshLayout.finishRefresh(2000)
        }
    }

    private fun initRefresh() {
        // 设置下拉刷新的最大高度
        refreshLayout.setHeaderHeight(DimenUtils.pxToDpF(this, 300f))
        refreshLayout.setFooterHeight(DimenUtils.pxToDpF(this, 100f))

        refreshLayout.setPrimaryColors(Color.parseColor("#00BF19"), Color.parseColor("#000000"))
    }
}