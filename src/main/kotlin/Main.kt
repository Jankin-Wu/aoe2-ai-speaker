import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.jankinwu.component.FormItem
import com.jankinwu.config.modelMap
import com.jankinwu.config.saveAppConfig
import com.jankinwu.utils.audioChannel
import com.jankinwu.utils.playAudioQueue
import com.konyaco.fluent.component.*
import kotlinx.coroutines.launch
import java.awt.Dimension
import java.awt.Toolkit

//@Composable
//@Preview
//fun App() {
//    var text by remember { mutableStateOf("Hello, World!") }
//
//    MaterialTheme {
//        Button(onClick = {
//            text = "Hello, Desktop!"
//        }) {
//            Text(text)
//        }
//    }
//}
var baseUrlState = mutableStateOf("")
var modelCodeState = mutableStateOf("")
var isRunning = mutableStateOf(false)


fun main() = application {
    val windowWidth by remember { mutableStateOf(400) }
    val windowHeight by remember { mutableStateOf(300) }
    val coroutineScope = rememberCoroutineScope()
    Window(
        onCloseRequest = ::exitApplication,
        title = "AOE2 AI Speaker",
        state = WindowState(
            width = windowWidth.dp,
            height = windowHeight.dp,
            position = WindowPosition((getScreenWidth() / 4).dp, (getScreenHeight() / 3).dp),
        ),
        icon = painterResource("images/speaker.png"),
    ) {
        MainPage()
    }

    coroutineScope.launch {
        playAudioQueue(audioChannel)
    }
}

@Preview
@Composable
fun MainPage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.width(300.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            var value by remember { mutableStateOf(TextFieldValue()) }
            FormItem("BaseUrl") {
                TextField(
                    value = value,
                    onValueChange = { value = it },
//                header = { Text("Base Url:") },
                    placeholder = {
                        Text(
                            "http://127.0.0.1:8080",
                            color = Color.LightGray
                        )
                    },
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            var modelCode by remember { mutableStateOf("") }

            FormItem("ModelName") {
                MenuFlyoutContainer(
                    flyout = {
                        modelMap.entries.forEach {
                            MenuFlyoutItem(text = { Text(it.value.modelName) }, onClick = { modelCode = it.key })
                        }
                    },
                    content = {
                        DropDownButton(onClick = { isFlyoutVisible = !isFlyoutVisible }, content = {
                            Box(Modifier.width(108.dp)) {
                                Text("")
                            }
                        })
                    },
                    adaptivePlacement = true,
                    placement = FlyoutPlacement.BottomAlignedStart
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row (
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ){
                Button(onClick = { saveAppConfig() }, modifier = Modifier) {
                    Text("保存")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = {
                        isRunning.value = !isRunning.value
                    },
                    modifier = Modifier
                ) {
                    if (isRunning.value) {
                        Text("停止")
                    } else {
                        Text("启动")
                    }
                }
            }
        }
    }
}

fun getScreenWidth(): Int {
    val screenSize: Dimension = Toolkit.getDefaultToolkit().screenSize
    return screenSize.width
}

fun getScreenHeight(): Int {
    val screenSize: Dimension = Toolkit.getDefaultToolkit().screenSize
    return screenSize.height
}