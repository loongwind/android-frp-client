package com.loongwind.frp.client.utils

import com.loongwind.frp.client.model.IniConfig
import com.loongwind.frp.client.model.IniProperty
import com.loongwind.frp.client.model.IniSection
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.regex.Pattern

fun readIniCfg(file: String): IniConfig {
    var strLine: String
    val bufferedReader = BufferedReader(FileReader(file))
    var section = ""
    var configs = arrayListOf<IniProperty>()
    val sectionList = arrayListOf<IniSection>()
    bufferedReader.use { reader ->
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            strLine = line?.trim { it <= ' ' } ?: ""
            strLine = strLine.split("[;]".toRegex()).toTypedArray()[0]
            val p: Pattern = Pattern.compile("\\[.+]")
            val m = p.matcher(strLine)
            if (m.matches()) {
                if(section != ""){
                    val iniSection = IniSection(name = section)
                    iniSection.configs.addAll(configs)
                    sectionList.add(iniSection)
                }
                section = strLine.substring(1, strLine.length - 1)
                configs = arrayListOf()
            }else{
                strLine = strLine.trim { it <= ' ' }
                val strArray = strLine.split("=".toRegex()).toTypedArray()
                if (strArray.size == 2) {
                    val key = strArray[0].trim { it <= ' ' }
                    val value = strArray[1].trim { it <= ' ' }
                    configs.add(IniProperty(key = key, value = value))
                }
            }
        }
        val iniSection = IniSection(name = section)
        iniSection.configs.addAll(configs)
        sectionList.add(iniSection)
    }
    val iniConfig = IniConfig()
    iniConfig.sections.addAll(sectionList)
    return iniConfig
}



fun writeIniCfg(filePath: String, iniConfig : IniConfig) {
    val content = StringBuffer()
    iniConfig.sections.forEach { section ->
        content.append("[${section.name}]\n")
        section.configs.forEach {
            content.append("${it.key} = ${it.value}\n")
        }
        content.append("\n")
    }
    val file = File(filePath)
    file.writeText(content.toString())
}