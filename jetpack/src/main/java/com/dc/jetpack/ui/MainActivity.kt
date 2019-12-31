package com.dc.jetpack.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.facade.annotation.Route
import com.dc.jetpack.R
import com.dc.jetpack.viewmodel.MainViewModel
import com.example.base.constants.Path

@Route(path = Path.MVVM_ACTIVITY)
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        setContentView(R.layout.jetpack_activity_main)
    }
}
