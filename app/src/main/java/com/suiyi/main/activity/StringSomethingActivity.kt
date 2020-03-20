package com.suiyi.main.activity

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.base.constants.Path
import com.suiyi.main.R
import kotlinx.android.synthetic.main.activity_string_something.*


@Route(path = Path.STRING_SOME_THING_ACTIVITY)
class StringSomethingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_string_something)

        val spannableString = SpannableString("¥12.99")
        val sizeSpan01 = RelativeSizeSpan(0.6f)
        spannableString.setSpan(sizeSpan01, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        textView.text = spannableString

    }
}
