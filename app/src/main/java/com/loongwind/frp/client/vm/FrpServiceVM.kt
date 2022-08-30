package com.loongwind.frp.client.vm

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import com.loongwind.ardf.base.BaseViewModel
import com.loongwind.frp.client.constant.EVENT_ADD_SERVICE
import com.loongwind.frp.client.constant.EVENT_CLICK_ITEM
import com.loongwind.frp.client.constant.EVENT_RESULT
import com.loongwind.frp.client.model.IniConfig
import com.loongwind.frp.client.repository.IniRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FrpServiceVM : BaseViewModel(), KoinComponent {

    val configList = ObservableArrayList<IniConfig>()
    private val iniRepository : IniRepository by inject()

    val selectedConfig = ObservableField<IniConfig>()
    var isSelectMode = false
    var clickItem:IniConfig? = null
    private val dataChangeObserver : (List<IniConfig>)->Unit = { list ->
        configList.clear()
        configList.addAll(list)
    }
    init {
        iniRepository.getAllConfigLiveData().observeForever(dataChangeObserver)
    }

    fun add(){
        postEvent(EVENT_ADD_SERVICE)
    }

    fun onItemClick(item:Any){
        if(item is IniConfig){
            if(isSelectMode){
                selectedConfig.set(item)
                postEvent(EVENT_RESULT)
            }else{
                clickItem = item
                postEvent(EVENT_CLICK_ITEM)
            }
        }
    }

    override fun onCleared() {
        iniRepository.getAllConfigLiveData().removeObserver(dataChangeObserver)
        super.onCleared()
    }

    fun onDelete(item : IniConfig){
        configList.remove(item)
        iniRepository.deleteConfig(item)
    }


}