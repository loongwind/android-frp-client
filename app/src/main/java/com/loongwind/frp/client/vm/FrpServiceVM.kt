package com.loongwind.frp.client.vm

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import com.loongwind.ardf.base.BaseViewModel
import com.loongwind.frp.client.constant.EVENT_ADD_SERVICE
import com.loongwind.frp.client.constant.EVENT_CLICK_ITEM
import com.loongwind.frp.client.model.IniConfig
import com.loongwind.frp.client.repository.IniRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FrpServiceVM : BaseViewModel(), KoinComponent {

    val configList = ObservableArrayList<IniConfig>()
    private val iniRepository : IniRepository by inject()
    var clickItem:IniConfig? = null

    init {
        iniRepository.getAllConfig()?.let {
            configList.addAll(it)
        }

    }

    fun add(){
        postEvent(EVENT_ADD_SERVICE)
    }

    fun onItemClick(item:Any){
        if(item is IniConfig){
            clickItem = item
            postEvent(EVENT_CLICK_ITEM)
        }
    }

}