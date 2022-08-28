package com.loongwind.frp.client.modules

import com.loongwind.ardf.net.ARDF_BASE_URL
import com.loongwind.ardf.net.ARDF_DEBUG
import com.loongwind.ardf.net.interceptor
import com.loongwind.ardf.net.retrofitModule
import com.loongwind.frp.client.api.ApiService
import com.loongwind.frp.client.api.TokenInterceptor
import com.loongwind.frp.client.constant.DEFAULT_ADMIN_PORT
import com.loongwind.frp.client.db.dbModule
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {

    single(named(ARDF_BASE_URL)) {
        "http://127.0.0.1:$DEFAULT_ADMIN_PORT/"
    }

    single(named(ARDF_DEBUG)) { true }

    single {
        get<Retrofit>().create(ApiService::class.java)
    }

    interceptor {
        TokenInterceptor()
    }

    includes(retrofitModule)
    includes(repositoryModule)
    includes(dbModule)
    includes(viewModelModule)
}