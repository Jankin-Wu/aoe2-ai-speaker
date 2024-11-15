package com.jankinwu.http

import baseUrlState
import com.jankinwu.dto.TTSRequest
import com.jankinwu.utils.audioChannel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

/**
 * @description: http 客户端
 * @author: Jankin Wu
 * @date: 2024-11-14 22:50
 **/
val client = HttpClient(CIO) {
    install(ContentNegotiation) {
        json(Json {ignoreUnknownKeys = true})
    }
}

fun sendTTSReq(data: TTSRequest, scope: CoroutineScope){
    scope.launch {
        // Launching a coroutine to perform the network request
        withContext(Dispatchers.IO) {
            try {
                val response: HttpResponse = client.post("${baseUrlState.value}/tts") {
                    contentType(ContentType.Application.Json)
                    setBody(data)
                }
                audioChannel.send(response.body())
            } catch (e: Exception) {
                println(e)
            }
        }
    }
}

fun sendSwitchingModelReq(weightPath: String, scope: CoroutineScope){
    scope.launch {
        // Launching a coroutine to perform the network request
        withContext(Dispatchers.IO) {
            try {
                val response: HttpResponse = client.post("${baseUrlState.value}/set_gpt_weights?weights_path=$weightPath") {
                    contentType(ContentType.Application.Json)
                }
                audioChannel.send(response.body())
            } catch (e: Exception) {
                println(e)
            }
        }
    }
}