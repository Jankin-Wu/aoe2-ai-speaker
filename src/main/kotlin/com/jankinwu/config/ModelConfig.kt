package com.jankinwu.config

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import com.jankinwu.utils.loadConfig
import kotlinx.serialization.Serializable

@Serializable
data class ModelConfig(
    var modelCode: String,
    var modelName: String,
    var promptText: String,
    var promptLang: String,
    var refAudioPath: String,
    var speedFactor: Double? = 1.0,
    var weightsPath: String
)

var modelMap: SnapshotStateMap<String, ModelConfig>  = mutableStateMapOf()

val modelList = mutableListOf<ModelConfig>()

const val modelConfigFileName = "model_config.json"

fun loadModelConfig() {
    loadConfig<List<ModelConfig>>(modelConfigFileName)?.let { modelList.addAll(it) }
    modelMap = convertListToStateMap(modelList)
}

fun convertListToStateMap(modelList: MutableList<ModelConfig>): SnapshotStateMap<String, ModelConfig> {
    val modelMap = mutableStateMapOf<String, ModelConfig>()
    for (modelConfig in modelList) {
        modelMap[modelConfig.modelCode] = modelConfig
    }
    return modelMap
}

