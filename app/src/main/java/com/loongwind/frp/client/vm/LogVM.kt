package com.loongwind.frp.client.vm

import androidx.databinding.ObservableField
import com.loongwind.ardf.base.BaseViewModel
import com.loongwind.frp.client.repository.FileRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LogVM  : BaseViewModel(), KoinComponent{
    val logContent = ObservableField<String>()

    private val fileRepository by inject<FileRepository>()

    init{
        logContent.set(fileRepository.getLogContent())
    }


    fun refreshLog(){
        logContent.set(fileRepository.getLogContent())
    }

    fun clearLog(){
        fileRepository.clearLogContent()
        logContent.set("")
    }

}