package com.loongwind.frp.client.ext

import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter


@BindingAdapter("titleGravity")
fun titleGravity(toolbar: Toolbar, gravity: Int){
    val field = toolbar.javaClass.getDeclaredField("mTitleTextView")
    field.isAccessible = true
    val title : TextView = field.get(toolbar) as TextView
    title.layoutParams = Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT,Toolbar.LayoutParams.WRAP_CONTENT, gravity)
}