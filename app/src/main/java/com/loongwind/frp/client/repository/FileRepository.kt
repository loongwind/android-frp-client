package com.loongwind.frp.client.repository

import com.loongwind.frp.client.constant.LOGFILE
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.qualifier.named
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import kotlin.math.log

class FileRepository : KoinComponent {


    fun getLogContent() : String {
        val logFile = get<File>(named(LOGFILE))

        if(!logFile.exists()){
            return ""
        }
        val fileReader = FileReader(logFile)

        val text = fileReader.readText()
        fileReader.close()
        return text
    }


    fun clearLogContent(){
        val logFile = get<File>(named(LOGFILE))
        if(!logFile.exists()){
            return
        }
        val fileWriter = FileWriter(logFile)
        fileWriter.write("")
        fileWriter.close()
    }
}