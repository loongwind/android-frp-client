package com.loongwind.frp.client.modules

import com.loongwind.frp.client.vm.ConfigVM
import com.loongwind.frp.client.vm.FrpServiceVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module{

    viewModel { FrpServiceVM() }
    viewModel { ConfigVM() }

}