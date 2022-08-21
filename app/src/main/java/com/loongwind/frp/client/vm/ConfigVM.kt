package com.loongwind.frp.client.vm

import androidx.databinding.ObservableField
import com.loongwind.ardf.base.BaseViewModel
import com.loongwind.frp.client.model.IniConfig
import com.loongwind.frp.client.model.IniProperty
import com.loongwind.frp.client.model.IniSection
import com.loongwind.frp.client.repository.IniRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ConfigVM : BaseViewModel(), KoinComponent{

    val serviceName = ObservableField<String>()
    val serviceIp = ObservableField<String>()
    val servicePort = ObservableField<String>()
    val token = ObservableField<String>()

    private val iniRepository : IniRepository by inject()


    fun save(){
        val config = IniConfig(name = serviceName.get() ?: "")
        val iniSection = IniSection(name = "common")
        iniSection.configs.add(IniProperty(key = "server_addr", value = serviceIp.get() ?: ""))
        iniSection.configs.add(IniProperty(key = "server_port", value = servicePort.get() ?: ""))
        iniSection.configs.add(IniProperty(key = "token", value = token.get() ?: ""))
        iniSection.configs.add(IniProperty(key = "token", value = token.get() ?: ""))
        iniSection.configs.add(IniProperty(key = "log_file", value = "./frpc.log"))
        config.sections.add(iniSection)
        iniRepository.savConfig(config)
        back()
    }

}