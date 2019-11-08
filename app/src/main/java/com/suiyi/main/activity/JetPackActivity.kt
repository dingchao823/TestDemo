package com.suiyi.main.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.suiyi.main.R
import com.suiyi.main.adapter.SimpleTextAdapter
import com.example.base.bean.ViewModelBean
import com.suiyi.main.constants.Path
import kotlinx.android.synthetic.main.activity_recycler_diff.*

/**
 * 使用 JetPack 组件实现一些东西
 *
 * @author dingchao823
 */
@Route(path = Path.JET_PACK_ACTIVITY)
class JetPackActivity : AppCompatActivity(){

    private val nameObservable = Observer<String> {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jetpack)

        val mModel = ViewModelProviders.of(this).get(ViewModelBean::class.java)
        mModel.current?.observe(this@JetPackActivity, nameObservable)


        val layoutManager = VirtualLayoutManager(this)
        recycler_view_with_diff.layoutManager = layoutManager
        val adapter = DelegateAdapter(layoutManager)
        recycler_view_with_diff.adapter = adapter
        val simple = SimpleTextAdapter()
        adapter.addAdapter(simple)
        simple.notifyDataSetChanged()

    }
}