package com.jankinwu.utils

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


inline fun <reified T> saveConfig(data: T, fileName: String) {
    val content = Json.encodeToString(data)
    println(Json.encodeToString(content))
    writeToFile(fileName, content)
}

inline fun <reified T : Any> loadConfig(configFileName: String): T? {
    val json = Json { ignoreUnknownKeys = true }
    val content = readResourceFile(configFileName)
    val config = content?.let { json.decodeFromString<T>(it) }
    return config
}
fun readResourceFile(fileName: String): String? {
    val appRootDirectory = System.getProperty("user.dir")
    val file = File("$appRootDirectory/config/$fileName")
    if (!file.exists()) {
        return null
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