package com.jetbrains.spacetutorial.network

import com.jetbrains.spacetutorial.dto.MedicoCreateDTO
import com.jetbrains.spacetutorial.dto.MedicoList
import com.jetbrains.spacetutorial.dto.MedicoUpdateDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class ServerApi {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }

        defaultRequest {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJBUEkgVm9sbC5tZWQiLCJzdWIiOiJhZG1pbiIsImV4cCI6MTcxNTU2NjIwMn0.n8jUdmwFm8DbtBimBDJZ2igclU-VYqE2HuV3IQch0V4")
        }
    }

    suspend fun getAllMedicos(): MedicoList {
        return httpClient.get("http://192.168.68.129:8080/medicos").body()
    }

    suspend fun addMedico(medico: MedicoCreateDTO) {
        httpClient.post("http://192.168.68.129:8080/medicos") {
            setBody(medico)
        }
    }

    suspend fun updateMedico(medico: MedicoUpdateDTO) {
        httpClient.put("http://192.168.68.129:8080/medicos") {
            setBody(medico)
        }
    }
}