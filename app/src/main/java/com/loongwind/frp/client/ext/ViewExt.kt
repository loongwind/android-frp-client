package com.loongwind.frp.client.ext

import android.text.method.ScrollingMovementMethod
import android.widget.TextView
import androidx.databinding.BindingAdapter


@BindingAdapter("isScroll")
fun setScroll(view:TextView, isScroll: Boolean){

    view.isVerticalScrollBarEnabled = true
    view.movementMethod = ScrollingMovementMethod.getInstance();
}