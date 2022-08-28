package com.loongwind.frp.client.vm

import androidx.databinding.Observable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.Observer
import com.loongwind.ardf.base.BaseViewModel
import com.loongwind.ardf.base.event.EVENT_ITEM_CLICK
import com.loongwind.ardf.net.ErrorHandle
import com.loongwind.frp.client.constant.*
import com.loongwind.frp.client.model.IniConfig
import com.loongwind.frp.client.model.IniSection
import com.loongwind.frp.client.repository.FrpcApiRepository
import com.loongwind.frp.client.repository.IniRepository
import io.objectbox.android.ObjectBoxLiveData
import io.objectbox.annotation.Id
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.internal.notifyAll
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeVM : BaseViewModel(), KoinComponent {
    val sectionList = ObservableArrayList<IniSection>()
    val config = ObservableField<IniConfig>()

    val isConnect = ObservableBoolean()

    private val iniRepository by inject<IniRepository>()
    private val frpcApiRepository by inject<FrpcApiRepository>()

    private var configListData : ObjectBoxLiveData<IniConfig>? = null

    init {
        isConnect.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if(isConnect.get()){
                    if(config.get() == null){
                        isConnect.set(false)
                        postHintText("请选择Frpc服务")
                    }else{
                        postEvent(EVENT_START_SERVICE)
                        loadConfigStatus()
                    }
                }else{
                    postEvent(EVENT_STOP_SERVICE)
                    clearSectionStatus()
                }
            }

        })
    }

    private val configObserver : (List<IniConfig>)->Unit = {
        config.set(it.first())
        val newSections: List<IniSection> =
            config.get()?.sections?.filter { it.name != COMMON }?.toList() ?: arrayListOf()
        updateStatus(sectionList, newSections)
        sectionList.clear()
        sectionList.addAll(newSections)
    }

    fun loadConfig(id: Long){
        configListData?.removeObserver(configObserver)
        configListData = iniRepository.getConfigLiveDataById(id)
        configListData?.observeForever(configObserver)
    }

    private val onError : ErrorHandle ={
        isConnect.set(false)
        postHintText("启动失败，请检查配置是否正确")
        true
    }

    private fun loadConfigStatus() = launch(onError = onError){
        delay(300)
        frpcApiRepository.getStatus(sectionList)
    }

    private fun updateStatus(oldSections : List<IniSection>, newSections : List<IniSection>){
        if(!isConnect.get()){
            return
        }
        newSections.forEach { newSection ->
            val oldSection = oldSections.firstOrNull { it.name == newSection.name }
            newSection.isRunning.set(oldSection?.isRunning?.get() ?: false)
            newSection.error.set(oldSection?.error?.get() ?: "")
        }
    }

    private fun clearSectionStatus(){
        sectionList.forEach {
            it.isRunning.set(false)
            it.error.set("")
        }
    }



    fun onSelectConfig(){
        postEvent(EVENT_SELECT)
    }

    fun onLogClick(){
        postEvent(EVENT_LOG)
    }

    fun switchConnect(){
//        postEvent(EVENT_START_SERVICE)
    }

    override fun onCleared() {
        configListData?.removeObserver(configObserver)
        super.onCleared()
    }
}