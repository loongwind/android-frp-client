package com.loongwind.frp.client.ui

import android.content.Intent
import com.loongwind.ardf.base.BaseBindingViewModelFragment
import com.loongwind.frp.client.constant.*
import com.loongwind.frp.client.databinding.FragmentHomeFrpServiceBinding
import com.loongwind.frp.client.service.FrpcService
import com.loongwind.frp.client.vm.HomeServiceVM

class HomeServiceFragment : BaseBindingViewModelFragment<FragmentHomeFrpServiceBinding, HomeServiceVM>() {


    override fun onEvent(eventId: Int) {
        super.onEvent(eventId)
        when(eventId){
            EVENT_START_SERVICE -> startFrpService()
            EVENT_STOP_SERVICE -> startFrpService()
            EVENT_CLICK_ITEM -> toFrpServiceDetails()
        }
    }

    private fun toFrpServiceDetails(){
        viewModel.clickItem?.let {
            val intent = Intent(activity, FrpServiceDetailsActivity::class.java)
            intent.putExtra(KEY_ID, it.id)
            startActivity(intent)
        }
    }


    private fun startFrpService(){
        val intent = Intent(activity, FrpcService::class.java)
        intent.putExtra(KEY_ID, viewModel.selectedItem.get()?.id)
        val type = if(viewModel.isConnect.get()){
            TYPE_START_SERVICE
        }else{
            TYPE_STOP_SERVICE
        }
        intent.putExtra(KEY_TYPE, type)
        activity?.startService(intent)
    }
}