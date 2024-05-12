package com.jetbrains.spacetutorial

import com.jetbrains.spacetutorial.network.ServerApi
import com.jetbrains.spacetutorial.presentaction.MedicoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<ServerApi> { ServerApi() }
    single<ServerSDK> {
        ServerSDK(
            api = get()
        )
    }
    viewModel { MedicoViewModel(sdk = get()) }
}