package com.loongwind.frp.client.ext

import android.text.method.ScrollingMovementMethod
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.daimajia.swipe.SimpleSwipeListener
import com.daimajia.swipe.SwipeLayout


@BindingAdapter("isScroll")
fun setScroll(view:TextView, isScroll: Boolean){

    view.isVerticalScrollBarEnabled = true
    view.movementMethod = ScrollingMovementMethod.getInstance();
}


@BindingAdapter("onOpened")
fun onOpened(swipeLayout: SwipeLayout, onOpened : ()->Unit){
    swipeLayout.addSwipeListener(object : SimpleSwipeListener() {
        override fun onOpen(layout: SwipeLayout?) {
            onOpened()
        }
    })
}

@BindingAdapter("isClose")
fun close(swipeLayout: SwipeLayout, close:Boolean){
    if(!close && swipeLayout.openStatus == SwipeLayout.Status.Open){
        swipeLayout.close()
    }
}