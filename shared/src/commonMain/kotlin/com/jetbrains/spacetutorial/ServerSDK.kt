package com.jetbrains.spacetutorial

import com.jetbrains.spacetutorial.dto.MedicoCreateDTO
import com.jetbrains.spacetutorial.dto.MedicoDTO
import com.jetbrains.spacetutorial.dto.MedicoUpdateDTO
import com.jetbrains.spacetutorial.network.ServerApi

class ServerSDK(val api: ServerApi) {

    @Throws(Exception::class)
    suspend fun getMedicos(): List<MedicoDTO> {
        return api.getAllMedicos().content
    }

    @Throws(Exception::class)
    suspend fun addMedico(medico: MedicoCreateDTO) {
        return api.addMedico(medico)
    }

    @Throws(Exception::class)
    suspend fun updateMedico(medico: MedicoUpdateDTO) {
        return api.updateMedico(medico)
    }
}