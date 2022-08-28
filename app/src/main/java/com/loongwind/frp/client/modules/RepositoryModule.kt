package com.loongwind.frp.client.modules

import com.loongwind.frp.client.repository.FrpcApiRepository
import com.loongwind.frp.client.repository.IniRepository
import org.koin.dsl.module

val repositoryModule = module{
    single { IniRepository() }
    single { FrpcApiRepository() }
}