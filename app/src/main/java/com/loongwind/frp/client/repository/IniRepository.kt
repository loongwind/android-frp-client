package com.loongwind.frp.client.repository

import com.loongwind.frp.client.model.IniConfig
import com.loongwind.frp.client.model.IniConfig_
import io.objectbox.Box
import io.objectbox.kotlin.query
import io.objectbox.query.QueryBuilder
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class IniRepository : KoinComponent{

    private val iniConfigBox : Box<IniConfig> by inject()

    fun savConfig(iniConfig:IniConfig){
        iniConfigBox.put(iniConfig)
    }

    fun getConfigByName(name: String) : IniConfig?{
        val config = iniConfigBox.query {
            equal(IniConfig_.name, name, QueryBuilder.StringOrder.CASE_INSENSITIVE)
        }.findFirst()
        return config
    }

    fun getConfigById(id: Long): IniConfig? {
        return iniConfigBox.get(id)
    }

    fun getAllConfig() : List<IniConfig>?{
        return iniConfigBox.all
    }


    fun generateConfigContent(id: Long) : String{
        val config = getConfigById(id)
        val content = StringBuffer()
        config?.sections?.forEach { section ->
            content.append("[${section.name}]\n")
            section.configs.forEach {
                content.append("${it.key} = ${it.value}\n")
            }
            content.append("\n")
        }
        return content.toString()
    }
}