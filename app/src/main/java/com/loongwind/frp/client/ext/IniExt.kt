package com.loongwind.frp.client.ext

import com.loongwind.frp.client.model.IniConfig
import com.loongwind.frp.client.model.IniProperty
import com.loongwind.frp.client.model.IniSection


fun IniConfig.getAddrAndPort() : String{
    return  sections.firstOrNull { section ->
        section.name == "common"
    }?.let { section ->
        val serverAddr =  section.configs.firstOrNull {
            it.key == "server_addr"
        }?.value

        val serverPort =  section.configs.firstOrNull {
            it.key == "server_port"
        }?.value

        "$serverAddr:$serverPort"
    } ?: ""
}


fun IniSection.get(key:String) : String?{
    return configs.firstOrNull{
        it.key == key
    }?.value
}

fun IniSection.update(key:String, value:String){
    var property = configs.firstOrNull{
        it.key == key
    }
    if(property == null){
        property = IniProperty(key=key, value = value)
        configs.add(property)
    }else{
        property.value = value
    }
}

fun IniConfig.get(section: String, key:String) : String?{
    return sections.firstOrNull {
        it.name == section
    }?.get(key)
}

fun IniConfig.getSection(name:String) : IniSection?{
    return sections.firstOrNull {
        it.name == name
    }
}