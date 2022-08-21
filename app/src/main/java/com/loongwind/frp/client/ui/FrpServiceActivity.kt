package com.loongwind.frp.client.ui

import android.content.Intent
import com.loongwind.ardf.base.BaseBindingViewModelActivity
import com.loongwind.ardf.base.ext.toast
import com.loongwind.frp.client.constant.EVENT_ADD_SERVICE
import com.loongwind.frp.client.databinding.ActivityFrpServiceBinding
import com.loongwind.frp.client.vm.FrpServiceVM

class FrpServiceActivity : BaseBindingViewModelActivity<ActivityFrpServiceBinding, FrpServiceVM>(){


    override fun onEvent(eventId: Int) {
        super.onEvent(eventId)
        when(eventId){
            EVENT_ADD_SERVICE -> startActivity(Intent(this, ConfigActivity::class.java))
        }
    }
}