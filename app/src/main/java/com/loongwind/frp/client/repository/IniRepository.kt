package com.loongwind.frp.client.repository

import com.loongwind.frp.client.model.IniConfig
import com.loongwind.frp.client.model.IniConfig_
import com.loongwind.frp.client.model.IniProperty
import com.loongwind.frp.client.model.IniSection
import io.objectbox.Box
import io.objectbox.android.ObjectBoxLiveData
import io.objectbox.kotlin.equal
import io.objectbox.kotlin.query
import io.objectbox.query.QueryBuilder
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class IniRepository : KoinComponent{

    private val iniConfigBox : Box<IniConfig> by inject(named<IniConfig>())
    private val iniSectionBox : Box<IniSection> by inject(named<IniSection>())
    private val iniPropertyBox : Box<IniProperty> by inject(named<IniProperty>())

    private var allConfigLiveData : ObjectBoxLiveData<IniConfig>? = null

    fun saveConfig(iniConfig:IniConfig){
        iniConfigBox.put(iniConfig)

        iniConfig.sections.filter { it.id > 0 }.forEach { section ->
            iniSectionBox.put(section)
            section.configs.filter { it.id > 0 }.forEach {
                iniPropertyBox.put(it)
            }
        }
    }

    fun deleteConfig(iniConfig: IniConfig){
        iniConfigBox.remove(iniConfig)
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


    fun getConfigLiveDataById(id: Long): ObjectBoxLiveData<IniConfig> {
        return ObjectBoxLiveData(iniConfigBox.query().equal(IniConfig_.id, id).build())
    }

    fun getAllConfig() : List<IniConfig>?{
        return iniConfigBox.all
    }

    fun getAllConfigLiveData() : ObjectBoxLiveData<IniConfig>{
        var configs = allConfigLiveData
        if(configs == null){
            configs = ObjectBoxLiveData(iniConfigBox.query().build())
            allConfigLiveData = configs
        }
        return configs
    }


    fun generateConfigContent(id: Long) : String{
        val config = getConfigById(id)
        val content = StringBuffer()
        config?.sections?.forEach { section ->
            content.append("[${section.name}]\n")
            section.configs.filter { it.value.isNotEmpty() }.forEach {
                content.append("${it.key} = ${it.value}\n")
            }
            content.append("\n")
        }
        return content.toString()
    }

    fun getSectionById(id:Long) : IniSection?{
        return iniSectionBox.get(id)
    }

    fun saveSection(section: IniSection){
        iniSectionBox.put(section)
        section.configs.filter { it.id > 0 }.forEach {
            iniPropertyBox.put(it)
        }
    }

    fun deleteSection(iniSection: IniSection){
        iniSectionBox.remove(iniSection)
    }
}