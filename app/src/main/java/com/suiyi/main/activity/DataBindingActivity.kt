package com.suiyi.main.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil.setContentView
import androidx.databinding.Observable
import com.alibaba.android.arouter.facade.annotation.Route
import com.suiyi.main.BR
import com.suiyi.main.R
import com.suiyi.main.bean.User
import com.example.base.constants.Path
import com.suiyi.main.databinding.ActivityDataBindingBinding

/**
 * DataBindingActivity 试验
 */
@Route(path = Path.DATABINDING_ACTIVITY)
class DataBindingActivity : AppCompatActivity() {

    var dataBinding : ActivityDataBindingBinding? = null
    var user : User? = null
    var userHandler = UserHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = setContentView(this, R.layout.activity_data_binding)
        user = User().also {
            it.name = "我改变了"
            it.password = "123"
        }
        dataBinding?.userInfo = user
        dataBinding?.userHandler = userHandler

        user?.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback(){

            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                when(propertyId){
                    BR.userInfo -> {

                    }
                }
            }

        })
    }

    inner class UserHandler(){

        fun changeName(){
            user?.name = "点击事件"
        }

        fun changePwd(){
            user?.password = "888888"
        }

        fun changeAll(){
            user?.name = "改变全部"
            user?.password = "666666"
        }

    }

}
