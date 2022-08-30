package com.loongwind.frp.client.vm

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import com.loongwind.ardf.base.BaseViewModel
import com.loongwind.frp.client.constant.EVENT_ADD
import com.loongwind.frp.client.constant.EVENT_CLICK_ITEM
import com.loongwind.frp.client.constant.EVENT_DETAILS
import com.loongwind.frp.client.model.IniConfig
import com.loongwind.frp.client.model.IniSection
import com.loongwind.frp.client.repository.IniRepository
import io.objectbox.Box
import io.objectbox.android.ObjectBoxLiveData
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FrpServiceDetailsVM : BaseViewModel(), KoinComponent {
    val config = ObservableField<IniConfig>()
    val clientConfigs = ObservableArrayList<IniSection>()

    private val iniRepository : IniRepository by inject()

    var id:Long = 0

    var clickSection : IniSection? = null
    private var configLiveData : ObjectBoxLiveData<IniConfig>? = null

    val openedConfigItem = ObservableField<IniSection>()

    private val configObserver : (List<IniConfig>)->Unit = {
        val iniConfig = it.firstOrNull()
        config.set(iniConfig)
        iniConfig?.sections?.filter {
            it.name != "common"
        }?.let {
            clientConfigs.clear()
            clientConfigs.addAll(it)
        }
    }

    fun loadData(id:Long){
        this.id = id
        configLiveData = iniRepository.getConfigLiveDataById(id)
        configLiveData?.observeForever(configObserver)
    }


    fun add(){
        postEvent(EVENT_ADD)
    }

    fun onServiceEdit(){
        postEvent(EVENT_DETAILS)
    }

    fun onConfigEdit(item : Any){
        if(item is IniSection){
            clickSection = item
            postEvent(EVENT_CLICK_ITEM)
        }
    }

    fun onDeleteSection(iniSection: IniSection){
        iniRepository.deleteSection(iniSection)
        clientConfigs.remove(iniSection)
    }

    override fun onCleared() {
        configLiveData?.removeObserver(configObserver)
        super.onCleared()
    }


    fun onDragOpened(item : IniSection){
        openedConfigItem.set(item)
    }

    fun clearDragOpened(){
        openedConfigItem.set(null)
    }



}