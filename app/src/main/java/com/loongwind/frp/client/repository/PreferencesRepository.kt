package com.loongwind.frp.client.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.loongwind.frp.client.constant.KEY_CONFIG_ID
import com.loongwind.frp.client.constant.KEY_ID
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PreferencesRepository : KoinComponent  {

    private val sharedPreferences by inject<SharedPreferences>()


    fun saveSelectedConfig(id:Long){
        sharedPreferences.edit {
            putLong(KEY_CONFIG_ID, id)
        }
    }

    fun getSelectedConfigId() : Long{
        return sharedPreferences.getLong(KEY_CONFIG_ID, 0)
    }

}