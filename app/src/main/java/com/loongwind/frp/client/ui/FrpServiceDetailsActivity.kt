package com.loongwind.frp.client.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.loongwind.ardf.base.BaseBindingViewModelActivity
import com.loongwind.frp.client.constant.EVENT_ADD
import com.loongwind.frp.client.constant.EVENT_ADD_SERVICE
import com.loongwind.frp.client.constant.EVENT_DETAILS
import com.loongwind.frp.client.constant.KEY_ID
import com.loongwind.frp.client.databinding.ActivityFrpServiceDetailsBinding
import com.loongwind.frp.client.vm.FrpServiceDetailsVM

class FrpServiceDetailsActivity : BaseBindingViewModelActivity<ActivityFrpServiceDetailsBinding, FrpServiceDetailsVM>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getLongExtra(KEY_ID, 0)
        viewModel.loadData(id)
    }

    private val requestDataLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_OK){
            viewModel.loadData(viewModel.id)
        }
    }

    override fun onEvent(eventId: Int) {
        super.onEvent(eventId)
        when(eventId){
            EVENT_ADD -> toAddConfig()
            EVENT_DETAILS -> toServiceEdit()
        }
    }


    private fun toAddConfig(){
        val intent = Intent(this, FrpAddConfigActivity::class.java)
        intent.putExtra(KEY_ID, viewModel.id)
        requestDataLauncher.launch(intent)
    }

    private fun toServiceEdit(){
        val intent = Intent(this, ConfigActivity::class.java)
        intent.putExtra(KEY_ID, viewModel.config.get()?.id)
        startActivity(intent)
    }





}