package com.loongwind.frp.client.vm

import androidx.databinding.Observable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.loongwind.ardf.base.BaseViewModel
import com.loongwind.ardf.base.event.EVENT_ITEM_CLICK
import com.loongwind.frp.client.constant.EVENT_CLICK_ITEM
import com.loongwind.frp.client.constant.EVENT_START_SERVICE
import com.loongwind.frp.client.constant.EVENT_STOP_SERVICE
import com.loongwind.frp.client.model.IniConfig
import com.loongwind.frp.client.repository.IniRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeServiceVM : BaseViewModel(), KoinComponent {
    val configList = ObservableArrayList<IniConfig>()

    val selectedItem = ObservableField<IniConfig>()

    val isConnect = ObservableBoolean()

    var clickItem : IniConfig? = null

    private val iniRepository by inject<IniRepository>()

    init {
        iniRepository.getAllConfig()?.let {
            configList.addAll(it)
        }
        isConnect.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if(isConnect.get()){
                    if(selectedItem.get() == null){
                        isConnect.set(false)
                        postHintText("请选择Frpc服务")
                    }else{
                        postEvent(EVENT_START_SERVICE)
                    }
                }else{
                    postEvent(EVENT_STOP_SERVICE)
                }
            }

        })
    }

    fun onItemClick(item:Any){
        if(item is IniConfig){
            clickItem = item
            postEvent(EVENT_CLICK_ITEM)
        }

    }

    fun onItemCheck(item:Any){
        if(item is IniConfig){
            if(selectedItem.get() == item){
                selectedItem.set(null)
            }else{
                selectedItem.set(item)
            }
        }
    }

    fun switchConnect(){
//        postEvent(EVENT_START_SERVICE)
    }
}