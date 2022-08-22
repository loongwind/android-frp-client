package com.loongwind.frp.client.ext

import com.loongwind.frp.client.model.IniConfig
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