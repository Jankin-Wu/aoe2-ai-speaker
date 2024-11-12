package com.jankinwu.config

import baseUrlState
import com.jankinwu.utils.loadConfig
import com.jankinwu.utils.saveConfig
import kotlinx.serialization.Serializable
import modelCodeState

/**
 * @description: 配置项实体类
 * @author: Jankin Wu
 * @date: 2024-11-10 11:43
 **/
@Serializable
data class AppConfig(
    var baseUrl: String? = "",
    var modelCode: String? = ""
)

var configFileName = "app_config.json"

fun loadAppConfig() {
    val appConfig = loadConfig<AppConfig>(configFileName)
    baseUrlState.value = appConfig.baseUrl?: ""
    modelCodeState.value = appConfig.modelCode?: ""
}

fun saveAppConfig() {
    val appConfig = AppConfig(
        baseUrl = baseUrlState.value,
        modelCode = baseUrlState.value,
    )
    saveConfig(appConfig, configFileName)
}

