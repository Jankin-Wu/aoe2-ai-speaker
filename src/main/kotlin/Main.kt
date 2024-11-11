import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.konyaco.fluent.component.*

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

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        DropdownMenuExample()
    }
}

@Preview
@Composable
fun DropdownMenuExample() {
    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            var value by remember { mutableStateOf(TextFieldValue()) }
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
            Spacer(modifier = Modifier.height(16.dp))
            MenuFlyoutContainer(
                flyout = {
                    MenuFlyoutItem(text = { Text("Send") }, onClick = { isFlyoutVisible = false })
                    MenuFlyoutItem(text = { Text("Reply") }, onClick = { isFlyoutVisible = false })
                    MenuFlyoutItem(text = { Text("Reply All") }, onClick = { isFlyoutVisible = false })
                },
                content = { DropDownButton(onClick = { isFlyoutVisible = !isFlyoutVisible }, content = { Text("Email") }) },
                adaptivePlacement = true,
                placement = FlyoutPlacement.BottomAlignedStart
            )
        }
    }
}
