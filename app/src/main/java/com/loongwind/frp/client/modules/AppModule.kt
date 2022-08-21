package com.loongwind.frp.client.modules

import com.loongwind.ardf.net.ARDF_BASE_URL
import com.loongwind.ardf.net.ARDF_DEBUG
import com.loongwind.ardf.net.retrofitModule
import com.loongwind.frp.client.api.ApiService
import com.loongwind.frp.client.db.dbModule
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {

    single(named(ARDF_BASE_URL)) {
        "https://www.fastmock.site/mock/6d5084df89b4c7a49b28052a0f51c29a/test/"
    }

    single(named(ARDF_DEBUG)) { true }

    single {
        get<Retrofit>().create(ApiService::class.java)
    }

    includes(retrofitModule)
    includes(repositoryModule)
    includes(dbModule)
    includes(viewModelModule)
}