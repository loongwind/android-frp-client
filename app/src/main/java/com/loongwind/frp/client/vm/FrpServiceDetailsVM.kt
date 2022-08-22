package com.loongwind.frp.client.vm

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import com.loongwind.ardf.base.BaseViewModel
import com.loongwind.frp.client.constant.EVENT_ADD
import com.loongwind.frp.client.model.IniConfig
import com.loongwind.frp.client.model.IniSection
import com.loongwind.frp.client.repository.IniRepository
import io.objectbox.Box
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FrpServiceDetailsVM : BaseViewModel(), KoinComponent {
    val config = ObservableField<IniConfig>()
    val clientConfigs = ObservableArrayList<IniSection>()

    private val iniRepository : IniRepository by inject()

    var id:Long = 0


    fun loadData(id:Long){
        this.id = id
        val iniConfig = iniRepository.getConfigById(id)
        config.set(iniConfig)
        iniConfig?.sections?.filter {
            it.name != "common"
        }?.let {
            clientConfigs.clear()
            clientConfigs.addAll(it)
        }
    }


    fun add(){
        postEvent(EVENT_ADD)
    }




}