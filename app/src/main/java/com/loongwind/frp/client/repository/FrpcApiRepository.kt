package com.loongwind.frp.client.repository

import com.loongwind.frp.client.api.ApiService
import com.loongwind.frp.client.model.IniSection
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FrpcApiRepository : KoinComponent{

    private val apiService by inject<ApiService> ()


    suspend fun getStatus(sections: List<IniSection>){
        val response = apiService.getFrpcStatus()
        sections.forEach { section ->
            val responseItem = response.tcp.firstOrNull {
                it.name == section.name
            }
            section.isRunning.set(responseItem?.status == "running")
            section.error.set(responseItem?.err)
        }
    }
}