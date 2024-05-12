package com.jetbrains.spacetutorial.presentaction

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jetbrains.spacetutorial.ServerSDK
import com.jetbrains.spacetutorial.dto.MedicoCreateDTO
import com.jetbrains.spacetutorial.dto.MedicoDTO
import com.jetbrains.spacetutorial.dto.MedicoUpdateDTO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MedicoViewModel(private val sdk: ServerSDK) : ViewModel() {

    private val _state = mutableStateOf(MedicoScreenState())
    val state: State<MedicoScreenState> = _state

    init {
        loadMedicos()
    }

    fun loadMedicos() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, medicos = emptyList())
            try {
                val medicos = sdk.getMedicos()
                _state.value = _state.value.copy(isLoading = false, medicos = medicos)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, medicos = emptyList())
            }
        }
    }

    fun addMedico(medico: MedicoCreateDTO) = runBlocking {
        val asyncProcess = async { sdk.addMedico(medico) }
        asyncProcess.await()
    }

    fun updateMedico(medico: MedicoUpdateDTO) = runBlocking {
        val asyncProcess = async { sdk.updateMedico(medico) }
        asyncProcess.await()
    }
}

data class MedicoScreenState(
    val isLoading: Boolean = false,
    val medicos: List<MedicoDTO> = emptyList()
)