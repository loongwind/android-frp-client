package com.loongwind.frp.client

import android.app.Application
import com.loongwind.ardf.net.retrofitModule
import com.loongwind.frp.client.db.dbModule
import com.loongwind.frp.client.modules.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule)
        }

    }
}