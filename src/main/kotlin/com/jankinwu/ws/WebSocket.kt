package com.jankinwu.ws

import androidx.compose.runtime.*
import com.jankinwu.config.modelMap
import com.jankinwu.dto.TTSRequest
import com.jankinwu.http.sendTTSReq
import com.konyaco.fluent.component.ContentDialog
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import isRunning
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import modelCode

@Composable
fun webSocketClient(scope: CoroutineScope) {
    var connectionAttemptState by remember { mutableStateOf(0) }
    var connectionAttemptCount by remember { mutableStateOf(0) }
    LaunchedEffect(connectionAttemptState) {
        val client = HttpClient {
            install(WebSockets)
        }
        delay(5000)
        try {
            client.webSocket(
                method = HttpMethod.Get,
                host = "localhost",
                port = 8080,
                path = "/websocket/plugin/2"
            ) {
                println("Connected to server.")
                // 发送消息到服务器
                send("Hello, server!")
                connectionAttemptCount = 0
                // 接收服务器发送的消息
                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    val receivedText = frame.readText()
                    println("Received message: $receivedText")

                    handleMessage(receivedText, scope, isRunning.value)
                }
            }
        } catch (e: Throwable) {
            println("Connection attempt failed: ${e.message}")
        } finally {
            client.close()
            delay(15000)
            // 当服务端关闭后尝试重连
            // 通过改变 connectionAttemptCount 的值让旧的协程被取消,新的协程被启动
            connectionAttemptState++
            connectionAttemptCount++
        }
    }
}


fun handleMessage(receivedText: String, scope: CoroutineScope, isRunning: Boolean) {
    val json = Json { ignoreUnknownKeys = true }
    val jsonElement = json.parseToJsonElement(receivedText)
//    val pluginItemCode = jsonElement.jsonObject["pluginItemCode"].toString().toInt()
    val data = jsonElement.jsonObject["data"].toString()
    if (isRunning) {
        val modelConfig = modelMap[modelCode.value]
        if (modelConfig == null) {
            print("未找到模型配置信息")
//            ContentDialog()
            return
        }
        val ttsRequest = modelConfig.let {
            TTSRequest(
                text = "Hello, world!",
                refAudioPath = it.refAudioPath,
                promptText = it.promptText,
                promptLang = it.promptLang,
                speedFactor = it.speedFactor
            )
        }
        sendTTSReq(ttsRequest, scope)
    }
}


