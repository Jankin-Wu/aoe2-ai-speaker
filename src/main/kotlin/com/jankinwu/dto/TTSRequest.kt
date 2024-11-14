package com.jankinwu.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @description: tts请求实体类
 * @author: Jankin Wu
 * @date: 2024-11-09 21:03
 **/
@Serializable
data class TTSRequest(
    @SerialName("text") val text: String,
    @SerialName("text_lang") val textLang: String = "auto",
    @SerialName("ref_audio_path") var refAudioPath: String,
    @SerialName("aux_ref_audio_paths") val auxRefAudioPaths: List<String>,
    @SerialName("prompt_text") var promptText: String = "",
    @SerialName("prompt_lang") var promptLang: String = "zh",
    @SerialName("top_k") val topK: Int = 5,
    @SerialName("top_p") val topP: Int = 1,
    @SerialName("temperature") val temperature: Int = 1,
    @SerialName("text_split_method") val textSplitMethod: String? = "cut0",
    @SerialName("batch_size") val batchSize: Int = 1,
    @SerialName("batch_threshold") val batchThreshold: Double = 0.75,
    @SerialName("split_bucket") val splitBucket: String = "True",
    @SerialName("return_fragment") val returnFragment: String = "False",
    @SerialName("speed_factor") val speedFactor: Double = 1.0,
    @SerialName("streaming_mode") val streamingMode: String = "False",
    @SerialName("seed") val seed: Int = -1,
    @SerialName("parallel_infer") val parallelInfer: String = "True",
    @SerialName("repetition_penalty") val repetitionPenalty: Double = 1.35
)
