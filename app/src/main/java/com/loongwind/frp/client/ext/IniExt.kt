package com.loongwind.frp.client.ext

import com.loongwind.frp.client.model.IniConfig


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