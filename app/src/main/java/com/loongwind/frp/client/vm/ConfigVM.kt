package com.loongwind.frp.client.vm

import androidx.databinding.ObservableField
import com.loongwind.ardf.base.BaseViewModel
import com.loongwind.frp.client.constant.*
import com.loongwind.frp.client.ext.get
import com.loongwind.frp.client.ext.getSection
import com.loongwind.frp.client.ext.update
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

    private var config : IniConfig? = null

    private val iniRepository : IniRepository by inject()

    var id : Long? = null

    fun loadData(){
        val configId = id ?: 0
        if(configId > 0){
            config = iniRepository.getConfigById(configId)
            config?.let {
                serviceName.set(it.name)
                serviceIp.set(it.get(COMMON, ATTR_SERVER_ADDR))
                servicePort.set(it.get(COMMON, ATTR_SERVER_PORT))
                token.set(it.get(COMMON, ATTR_TOKEN))
            }
        }
    }

    fun save(){
        val cacheConfig = config
        val iniConfig = if(cacheConfig == null){
            createNewConfig()
        }else{
            updateConfig(cacheConfig)
        }
        iniRepository.saveConfig(iniConfig)
        back()
    }

    private fun createNewConfig(): IniConfig {
        val iniConfig = IniConfig(name = serviceName.get() ?: "")
        val iniSection = IniSection(name = COMMON)
        iniSection.configs.add(IniProperty(key = ATTR_SERVER_ADDR, value = serviceIp.get() ?: ""))
        iniSection.configs.add(IniProperty(key = ATTR_SERVER_PORT, value = servicePort.get() ?: ""))
        iniSection.configs.add(IniProperty(key = ATTR_TOKEN, value = token.get() ?: ""))
        iniSection.configs.add(IniProperty(key = ATTR_ADMIN_PORT, value = DEFAULT_ADMIN_PORT))
        iniSection.configs.add(IniProperty(key = ATTR_ADMIN_USER, value = DEFAULT_ADMIN_USER))
        iniSection.configs.add(IniProperty(key = ATTR_ADMIN_PWD, value = DEFAULT_ADMIN_PWD))
        iniSection.configs.add(IniProperty(key = ATTR_LOG_FILE, value = DEFAULT_LOG_FILE))
        iniConfig.sections.add(iniSection)
        return iniConfig
    }


    private fun updateConfig(iniConfig: IniConfig): IniConfig {
        iniConfig.name = serviceName.get() ?: ""
        val iniSection = iniConfig.getSection(COMMON) ?: IniSection(name = COMMON)

        iniSection.update(ATTR_SERVER_ADDR, serviceIp.get() ?: "")
        iniSection.update(ATTR_SERVER_PORT, servicePort.get() ?: "")
        iniSection.update(ATTR_TOKEN, token.get() ?: "")

        iniSection.update(ATTR_ADMIN_PORT, DEFAULT_ADMIN_PORT)
        iniSection.update(ATTR_ADMIN_USER, DEFAULT_ADMIN_USER)
        iniSection.update(ATTR_ADMIN_PWD, DEFAULT_ADMIN_PWD)
        iniSection.update(ATTR_LOG_FILE, DEFAULT_LOG_FILE)

        println(iniConfig)
        return iniConfig
    }

}