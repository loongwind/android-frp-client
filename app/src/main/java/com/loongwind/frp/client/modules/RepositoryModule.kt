package com.loongwind.frp.client.modules

import com.loongwind.frp.client.repository.*
import org.koin.dsl.module

val repositoryModule = module{
    single { GlobalCache() }
    single { IniRepository() }
    single { FrpcApiRepository() }
    single { FileRepository() }
    single { PreferencesRepository() }
}