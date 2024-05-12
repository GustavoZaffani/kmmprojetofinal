package com.jetbrains.spacetutorial.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MedicoList(
    @SerialName("content")
    val content: List<MedicoDTO>
)

@Serializable
data class MedicoDTO(
    @SerialName("id")
    val id: Long,
    @SerialName("nome")
    val nome: String,
    @SerialName("email")
    val email: String,
    @SerialName("crm")
    val crm: String,
    @SerialName("especialidade")
    val especialidade: String,
)

@Serializable
data class MedicoCreateDTO(
    @SerialName("nome")
    val nome: String,
    @SerialName("email")
    val email: String,
    @SerialName("crm")
    val crm: String,
    @SerialName("especialidade")
    val especialidade: String,
)

@Serializable
data class MedicoUpdateDTO(
    @SerialName("id")
    val id: Long,
    @SerialName("nome")
    val nome: String
)