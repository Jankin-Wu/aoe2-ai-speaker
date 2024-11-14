package com.jankinwu.utils

/**
 * @description:
 * @author: Jankin Wu
 * @date: 2024-11-14 23:50
 **/
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip

val audioChannel = Channel<ByteArray>(Channel.UNLIMITED)

//fun main() = application {
//
//    val windowWidth by remember { mutableStateOf(400) }
//    val windowHeight by remember { mutableStateOf(300) }
//    val coroutineScope = rememberCoroutineScope()
//
//    Window(
//        onCloseRequest = ::exitApplication,
//        title = "AOE2 AI Speaker",
//        state = WindowState(
//            width = windowWidth.dp,
//            height = windowHeight.dp,
//        ),
//        icon = painterResource("images/speaker.png"),
//    ) {
//        val filePath = "D:\\QuarkDownloads\\尼奈素材\\nine\\nine_1.wav"
//        Button(onClick = {
//            val wavBytes = readWavFileToByteArray(filePath)
//            coroutineScope.launch {
//                audioChannel.send(wavBytes)
//            }
//        }) {
//            Text("播放音频")
//        }
//    }
//
//    coroutineScope.launch {
//        playAudioQueue(audioChannel)
//    }
//}

suspend fun playAudioQueue(audioChannel: Channel<ByteArray>) {
    for (wavBytes in audioChannel) {
        playAudio(wavBytes)
    }
}

suspend fun playAudio(wavBytes: ByteArray) {
    withContext(Dispatchers.IO) {
        try {
            val inputStream: InputStream = ByteArrayInputStream(wavBytes)
            val audioInputStream: AudioInputStream = AudioSystem.getAudioInputStream(inputStream)
            val clip: Clip = AudioSystem.getClip()
            clip.open(audioInputStream)

            val playbackCompleted = CompletableDeferred<Unit>()
            clip.addLineListener { event ->
                if (event.type == javax.sound.sampled.LineEvent.Type.STOP) {
                    clip.close()
                    playbackCompleted.complete(Unit)
                }
            }

            clip.start()
            playbackCompleted.await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun readWavFileToByteArray(filePath: String): ByteArray {
    return try {
        val file = File(filePath)
        file.readBytes()
    } catch (e: IOException) {
        e.printStackTrace()
        ByteArray(0)
    }
}