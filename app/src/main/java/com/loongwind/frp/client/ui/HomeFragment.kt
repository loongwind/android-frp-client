package com.loongwind.frp.client.ui

import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.loongwind.ardf.base.BaseBindingViewModelFragment
import com.loongwind.frp.client.constant.*
import com.loongwind.frp.client.databinding.FragmentHomeBinding
import com.loongwind.frp.client.service.FrpcService
import com.loongwind.frp.client.vm.HomeVM

class HomeFragment : BaseBindingViewModelFragment<FragmentHomeBinding, HomeVM>() {


    override fun onEvent(eventId: Int) {
        super.onEvent(eventId)
        when(eventId){
            EVENT_START_SERVICE -> startFrpService()
            EVENT_STOP_SERVICE -> startFrpService()
            EVENT_SELECT -> toSelectConfig()
        }
    }



    private fun startFrpService(){
        val intent = Intent(activity, FrpcService::class.java)
        intent.putExtra(KEY_ID, viewModel.config.get()?.id)
        val type = if(viewModel.isConnect.get()){
            TYPE_START_SERVICE
        }else{
            TYPE_STOP_SERVICE
        }
        intent.putExtra(KEY_TYPE, type)
        activity?.startService(intent)
    }


    private val requestDataLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == AppCompatActivity.RESULT_OK){
            val id = it.data?.getLongExtra(KEY_ID, 0) ?: 0
            viewModel.loadConfig(id)
        }
    }

    private fun toSelectConfig(){
        val intent = Intent(activity, FrpcConfigListActivity::class.java)
        requestDataLauncher.launch(intent)
    }
}