package com.example.base.bean

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelBean : ViewModel() {

    var current: MutableLiveData<String>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
            }
            return field
        }

}