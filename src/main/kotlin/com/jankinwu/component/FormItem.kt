package com.jankinwu.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.konyaco.fluent.component.Text

/**
 * @description: 表单项
 * @author: Jankin Wu
 * @date: 2024-11-13 21:43
 **/

@Composable
fun FormItem(label: String, content: @Composable () -> Unit) {
    Row (
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.width(100.dp)
        ){
            Text("$label: ")
        }
        content()
    }
}