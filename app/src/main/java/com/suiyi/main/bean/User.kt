package com.suiyi.main.bean

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import com.suiyi.main.BR

class User : BaseObservable() {

    @Bindable
    var name : String? = null
    set(value) {
        field = value
        notifyChange()
    }

    @Bindable
    var password : String? = null
    set(value) {
        field = value
    }



}