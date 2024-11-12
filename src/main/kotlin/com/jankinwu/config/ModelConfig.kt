package com.jankinwu.config

import androidx.compose.runtime.mutableStateMapOf
import com.jankinwu.utils.loadConfig

data class ModelConfig(
    var modelCode: String,
    var modelName: String,
    var promptText: String,
    var promptLang: String,
    var refAudioPath: String,
    var speedFactor: Double,
)

val modelMap = mutableStateMapOf<String, ModelConfig>()

val modelList = mutableListOf<ModelConfig>()

const val modelConfigFileName = "model_config.json"

fun loadModelConfig() {
    val modelList = loadConfig<List<ModelConfig>>(modelConfigFileName)
}

