package com.loongwind.frp.client.vm

import androidx.databinding.ObservableField
import com.loongwind.ardf.base.BaseViewModel
import com.loongwind.frp.client.constant.*
import com.loongwind.frp.client.ext.get
import com.loongwind.frp.client.ext.update
import com.loongwind.frp.client.model.IniProperty
import com.loongwind.frp.client.model.IniSection
import com.loongwind.frp.client.repository.IniRepository
import io.objectbox.annotation.Id
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FrpAddConfigVM : BaseViewModel(), KoinComponent {
    val name = ObservableField<String>()
    val type = ObservableField<String>("tcp")
    val localIp = ObservableField<String>()
    val localPort = ObservableField<String>()
    val remotePort = ObservableField<String>()

    var serviceConfigId:Long = 0
    var sectionId : Long = 0

    private var section:IniSection? = null


    private val iniRepository : IniRepository by inject()

    fun save(){
        if(section == null){
            saveTcpConfig()
        }else{
            updateSection()
        }
    }

    fun loadSection(){
        if(sectionId > 0){
            section = iniRepository.getSectionById(sectionId)
            name.set(section?.name)
            type.set(section?.get(ATTR_TYPE))
            localIp.set(section?.get(ATTR_LOCAL_IP))
            localPort.set(section?.get(ATTR_LOCAL_PORT))
            remotePort.set(section?.get(ATTR_REMOTE_PORT))
        }
    }

    private fun updateSection(){
        section?.name = name.get() ?: ""
        section?.update(ATTR_TYPE, type.get() ?: "")
        section?.update(ATTR_LOCAL_IP, localIp.get() ?: "")
        section?.update(ATTR_LOCAL_PORT, localPort.get() ?: "")
        section?.update(ATTR_REMOTE_PORT, remotePort.get() ?: "")
        section?.let {
            iniRepository.saveSection(it)
        }
        postEvent(EVENT_RESULT_SUCCESS)
    }

    private fun saveTcpConfig(){
        val iniSection = IniSection(name = name.get() ?: "")
        iniSection.configs.add(IniProperty(key = ATTR_TYPE, value = type.get() ?: ""))
        iniSection.configs.add(IniProperty(key = ATTR_LOCAL_IP, value = localIp.get() ?: ""))
        iniSection.configs.add(IniProperty(key = ATTR_LOCAL_PORT, value = localPort.get() ?: ""))
        iniSection.configs.add(IniProperty(key = ATTR_REMOTE_PORT, value = remotePort.get() ?: ""))

        iniRepository.getConfigById(serviceConfigId)?.let {
            it.sections.add(iniSection)
            iniRepository.saveConfig(it)
        }
        back()
    }
}