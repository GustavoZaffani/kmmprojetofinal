package com.jetbrains.spacetutorial

import com.jetbrains.spacetutorial.dto.MedicoDTO
import com.jetbrains.spacetutorial.network.ServerApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.dsl.module

class KoinHelper : KoinComponent {
    private val sdk: ServerSDK by inject<ServerSDK>()

    suspend fun getLaunches(): List<MedicoDTO> {
        return sdk.getMedicos()
    }
}

fun initKoin() {
    startKoin {
        modules(module {
            single<ServerApi> { ServerApi() }
            single<ServerSDK> {
                ServerSDK(
                    api = get()
                )
            }
        })
    }
}