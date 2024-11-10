package com.jankinwu.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @description: tts请求实体类
 * @author: Jankin Wu
 * @date: 2024-11-09 21:03
 **/
@Serializable
data class AudioRequest(
    @SerialName("text") val text: String,
    @SerialName("text_lang") val textLang: String,
    @SerialName("ref_audio_path") val refAudioPath: String,
    @SerialName("aux_ref_audio_paths") val auxRefAudioPaths: List<String>,
    @SerialName("prompt_text") val promptText: String,
    @SerialName("prompt_lang") val promptLang: String,
    @SerialName("top_k") val topK: Int,
    @SerialName("top_p") val topP: Double,
    @SerialName("temperature") val temperature: Double,
    @SerialName("text_split_method") val textSplitMethod: String,
    @SerialName("batch_size") val batchSize: Int,
    @SerialName("batch_threshold") val batchThreshold: Double,
    @SerialName("split_bucket") val splitBucket: String,
    @SerialName("return_fragment") val returnFragment: Boolean,
    @SerialName("speed_factor") val speedFactor: Double,
    @SerialName("streaming_mode") val streamingMode: String,
    @SerialName("seed") val seed: Int,
    @SerialName("parallel_infer") val parallelInfer: String,
    @SerialName("repetition_penalty") val repetitionPenalty: Double
)
