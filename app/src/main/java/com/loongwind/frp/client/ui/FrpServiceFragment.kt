package com.loongwind.frp.client.ui

import android.content.Intent
import com.loongwind.ardf.base.BaseBindingViewModelActivity
import com.loongwind.ardf.base.BaseBindingViewModelFragment
import com.loongwind.ardf.base.ext.toast
import com.loongwind.frp.client.constant.EVENT_ADD_SERVICE
import com.loongwind.frp.client.constant.EVENT_CLICK_ITEM
import com.loongwind.frp.client.constant.KEY_ID
import com.loongwind.frp.client.databinding.ActivityFrpServiceBinding
import com.loongwind.frp.client.vm.FrpServiceVM

class FrpServiceFragment : BaseBindingViewModelFragment<ActivityFrpServiceBinding, FrpServiceVM>(){


    override fun onEvent(eventId: Int) {
        super.onEvent(eventId)
        when(eventId){
            EVENT_ADD_SERVICE -> startActivity(Intent(activity, ConfigActivity::class.java))
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
}