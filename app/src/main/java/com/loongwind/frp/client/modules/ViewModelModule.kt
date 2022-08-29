package com.loongwind.frp.client.modules

import com.loongwind.frp.client.vm.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module{

    viewModel { FrpServiceVM() }
    viewModel { ConfigVM() }
    viewModel { FrpServiceDetailsVM() }
    viewModel { FrpAddConfigVM() }
    viewModel { HomeVM() }
    viewModel { LogVM() }
    viewModel { MineVM() }

}