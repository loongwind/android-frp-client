package com.loongwind.frp.client.vm

import androidx.databinding.Observable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.Observer
import com.loongwind.ardf.base.BaseViewModel
import com.loongwind.ardf.base.event.EVENT_ITEM_CLICK
import com.loongwind.frp.client.constant.*
import com.loongwind.frp.client.model.IniConfig
import com.loongwind.frp.client.model.IniSection
import com.loongwind.frp.client.repository.IniRepository
import io.objectbox.annotation.Id
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeVM : BaseViewModel(), KoinComponent {
    val sectionList = ObservableArrayList<IniSection>()
    val config = ObservableField<IniConfig>()

    val isConnect = ObservableBoolean()

    private val iniRepository by inject<IniRepository>()

    init {
        isConnect.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if(isConnect.get()){
                    if(config.get() == null){
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

    fun loadConfig(id: Long){
        config.set(iniRepository.getConfigById(id))
        sectionList.clear()
        sectionList.addAll(config.get()?.sections?.filter { it.name != COMMON } ?: arrayListOf())
    }


    fun onSelectConfig(){
        postEvent(EVENT_SELECT)
    }

    fun switchConnect(){
//        postEvent(EVENT_START_SERVICE)
    }
}