package org.xephyrous.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun outlineBox(title: String, size: DpSize = DpSize(200.dp, 75.dp), content: @Composable () -> Unit = {}) {
    var textWidth by remember { mutableStateOf(0) }
    var textHeight by remember { mutableStateOf(0) }

    Box {
        Box (
            modifier = Modifier
                .offset(y = (textHeight  / 2).dp)
                .border(4.dp, Color.White)
                .shadow(4.dp, RoundedCornerShape(0.dp))
                .width(maxOf(textWidth.dp, size.width))
                .height(maxOf(50.dp, size.height))
        ) {
            content()
        }

        Box (
            modifier = Modifier
                .offset(x = 10.dp)
                .background(Color(0xFF2D2D2D))
                .onGloballyPositioned {
                    textWidth = it.size.width
                    textHeight = it.size.height
                }
        ) {
            Text(
                title, color = Color.White, fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(5.dp),
                letterSpacing = 2.sp
            )
        }
    }
}