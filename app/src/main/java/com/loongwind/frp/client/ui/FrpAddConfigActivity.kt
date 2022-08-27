package com.loongwind.frp.client.ui

import android.os.Bundle
import com.loongwind.ardf.base.BaseBindingViewModelActivity
import com.loongwind.frp.client.constant.EVENT_RESULT_SUCCESS
import com.loongwind.frp.client.constant.KEY_ID
import com.loongwind.frp.client.constant.KEY_SECTION_ID
import com.loongwind.frp.client.databinding.ActivityFrpAddConfigBinding
import com.loongwind.frp.client.vm.FrpAddConfigVM

class FrpAddConfigActivity : BaseBindingViewModelActivity<ActivityFrpAddConfigBinding, FrpAddConfigVM>(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.serviceConfigId = intent.getLongExtra(KEY_ID, 0)
        viewModel.sectionId = intent.getLongExtra(KEY_SECTION_ID, 0)
        viewModel.loadSection()
    }

    override fun onEvent(eventId: Int) {
        super.onEvent(eventId)
        when(eventId){
            EVENT_RESULT_SUCCESS -> {
                setResult(RESULT_OK)
                finish()
            }
        }
    }
}