package com.loongwind.frp.client.db

import android.content.Context
import com.loongwind.frp.client.BuildConfig
import com.loongwind.frp.client.model.IniConfig
import com.loongwind.frp.client.model.IniProperty
import com.loongwind.frp.client.model.IniSection
import com.loongwind.frp.client.model.MyObjectBox
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.android.Admin
import org.koin.core.qualifier.named
import org.koin.dsl.module


val dbModule = module {


    single<BoxStore> (createdAtStart = true){
        val store = MyObjectBox.builder()
            .androidContext(get<Context>().applicationContext)
            .build()
        if (BuildConfig.DEBUG) {
            val started = Admin(store).start(get())
        }
        store
    }

    single<Box<IniConfig>>(named<IniConfig>()) {
        get<BoxStore>().boxFor(IniConfig::class.java)
    }
    single<Box<IniSection>>(named<IniSection>()) {
        get<BoxStore>().boxFor(IniSection::class.java)
    }
    single<Box<IniProperty>>(named<IniProperty>()) {
        get<BoxStore>().boxFor(IniProperty::class.java)
    }


}