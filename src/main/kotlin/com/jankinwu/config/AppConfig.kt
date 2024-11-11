package com.jankinwu.config

import baseUrlState
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

/**
 * @description: 配置项实体类
 * @author: Jankin Wu
 * @date: 2024-11-10 11:43
 **/
@Serializable
data class AppConfig(var baseUrl: String? = "", var modelCode: String? = "")

var configFile = "config.json"

fun loadAppConfig() {
    val json = Json { ignoreUnknownKeys = true }
    val content = readResourceFile(configFile)
    val config = json.decodeFromString<AppConfig>(content)

}

fun saveAppConfig() {
    val appConfig = AppConfig(
        baseUrl = baseUrlState.value,
        modelCode = baseUrlState.value,
    )
    val content = Json.encodeToString(appConfig)
    println(Json.encodeToString(content))
    writeToFile(configFile, content)
}

fun readResourceFile(fileName: String): String {
    val appRootDirectory = System.getProperty("user.dir")
    val file = File("$appRootDirectory/config/$fileName")
    if (!file.exists()) {
        return Json.encodeToString(AppConfig())
    }
    return FileInputStream(file).use { inputStream ->
        val readText = BufferedReader(InputStreamReader(inputStream)).readText()
        readText
    }
}

fun writeToFile(fileName: String, content: String) {
    val appRootDirectory = System.getProperty("user.dir")
    val file = File("$appRootDirectory/config/$fileName")
    val path: Path = Paths.get("$appRootDirectory/config")
    if (!Files.exists(path)) {
        try {
            Files.createDirectories(path)
            println("目录已创建: $appRootDirectory/config")
        } catch (e: Exception) {
            println("创建目录时出现错误: ${e.message}")
        }
    }
    if (!file.exists()) {
        file.createNewFile()
    }
    file.writeText(content)
}