package com.loongwind.frp.client.ui

import android.os.Bundle
import com.loongwind.ardf.base.BaseBindingViewModelActivity
import com.loongwind.frp.client.constant.KEY_ID
import com.loongwind.frp.client.databinding.ActivityConfigBinding
import com.loongwind.frp.client.vm.ConfigVM

class ConfigActivity : BaseBindingViewModelActivity<ActivityConfigBinding, ConfigVM>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.id = intent.getLongExtra(KEY_ID, 0)
        viewModel.loadData()
    }
}