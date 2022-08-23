package com.loongwind.frp.client.vm

import androidx.databinding.ObservableField
import com.loongwind.ardf.base.BaseViewModel
import com.loongwind.frp.client.constant.*
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
        val iniSection = IniSection(name = COMMON)
        iniSection.configs.add(IniProperty(key = ATTR_SERVER_ADDR, value = serviceIp.get() ?: ""))
        iniSection.configs.add(IniProperty(key = ATTR_SERVER_PORT, value = servicePort.get() ?: ""))
        iniSection.configs.add(IniProperty(key = ATTR_TOKEN, value = token.get() ?: ""))
        iniSection.configs.add(IniProperty(key = ATTR_ADMIN_PORT, value = DEFAULT_ADMIN_PORT))
        iniSection.configs.add(IniProperty(key = ATTR_ADMIN_USER, value = DEFAULT_ADMIN_USER))
        iniSection.configs.add(IniProperty(key = ATTR_ADMIN_PWD, value = DEFAULT_ADMIN_PWD))
        iniSection.configs.add(IniProperty(key = ATTR_LOG_FILE, value = DEFAULT_LOG_FILE))
        config.sections.add(iniSection)
        iniRepository.savConfig(config)
        back()
    }

}