package com.loongwind.frp.client.vm

import androidx.databinding.ObservableField
import com.loongwind.ardf.base.BaseViewModel
import com.loongwind.frp.client.constant.*
import com.loongwind.frp.client.model.IniProperty
import com.loongwind.frp.client.model.IniSection
import com.loongwind.frp.client.repository.IniRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FrpAddConfigVM : BaseViewModel(), KoinComponent {
    val name = ObservableField<String>()
    val type = ObservableField<String>("tcp")
    val localIp = ObservableField<String>()
    val localPort = ObservableField<String>()
    val remotePort = ObservableField<String>()

    var id:Long = 0


    private val iniRepository : IniRepository by inject()

    fun save(){
        saveTcpConfig()
    }


    private fun saveTcpConfig(){
        val iniSection = IniSection(name = name.get() ?: "")
        iniSection.configs.add(IniProperty(key = ATTR_TYPE, value = type.get() ?: ""))
        iniSection.configs.add(IniProperty(key = ATTR_LOCAL_IP, value = localIp.get() ?: ""))
        iniSection.configs.add(IniProperty(key = ATTR_LOCAL_PORT, value = localPort.get() ?: ""))
        iniSection.configs.add(IniProperty(key = ATTR_REMOTE_PORT, value = remotePort.get() ?: ""))

        iniRepository.getConfigById(id)?.let {
            it.sections.add(iniSection)
            iniRepository.saveConfig(it)
        }
        postEvent(EVENT_RESULT_SUCCESS)
    }
}