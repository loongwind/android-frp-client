package com.loongwind.frp.client.ext

import android.app.Activity
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import com.loongwind.frp.client.R


@BindingAdapter("titleGravity")
fun titleGravity(toolbar: Toolbar, gravity: Int) {
    val field = toolbar.javaClass.getDeclaredField("mTitleTextView")
    field.isAccessible = true
    val title: TextView? = field.get(toolbar) as? TextView
    title?.layoutParams = Toolbar.LayoutParams(
        Toolbar.LayoutParams.WRAP_CONTENT,
        Toolbar.LayoutParams.WRAP_CONTENT,
        gravity
    )
}

@BindingAdapter("showBack")
fun showLeftBack(toolbar: Toolbar, showBack: Boolean) {
    if(showBack){
        toolbar.setNavigationIcon(R.drawable.ic_back)
    }
}