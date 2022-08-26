package com.loongwind.frp.client.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loongwind.ardf.base.BaseBindingViewModelActivity
import com.loongwind.ardf.base.event.EVENT_BACK
import com.loongwind.frp.client.constant.KEY_ID
import com.loongwind.frp.client.databinding.ActivityFrpServiceBinding
import com.loongwind.frp.client.vm.FrpServiceVM

class FrpcConfigListActivity : BaseBindingViewModelActivity<ActivityFrpServiceBinding, FrpServiceVM>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.isSelectMode = true
    }

    override fun initDataBinding(binding: ActivityFrpServiceBinding) {
        super.initDataBinding(binding)
        binding.fab.visibility = View.GONE
    }

    override fun onEvent(eventId: Int) {
        if(eventId == EVENT_BACK){
            val intent = Intent()
            intent.putExtra(KEY_ID, viewModel.selectedConfig.get()?.id)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}