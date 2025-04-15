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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class OutlineBoxTitleAlignment {
    OVERHANG,
    LEFT,
    CENTER,
    RIGHT
}
    
@Composable
fun outlineBox(
    title: String,
    size: DpSize = DpSize(200.dp, 75.dp),
    alignment: OutlineBoxTitleAlignment = OutlineBoxTitleAlignment.LEFT,
    content: @Composable (() -> Unit) = {}
) {
    var textWidth by remember { mutableStateOf(0.dp) }
    var textHeight by remember { mutableStateOf(0.dp) }

    Box {
        Box (
            modifier = Modifier
                .offset(y = (textHeight/2))
                .border(4.dp, Color.White)
                .shadow(4.dp, RoundedCornerShape(0.dp))
                .width(maxOf(textWidth, size.width))
                .height(maxOf(50.dp, size.height))
        ) {
            content()
        }

        val localDensity = LocalDensity.current

        Box (
            modifier = Modifier
                .offset(x = when(alignment) {
                    OutlineBoxTitleAlignment.OVERHANG -> -(textWidth) + 40.dp
                    OutlineBoxTitleAlignment.LEFT -> 10.dp
                    OutlineBoxTitleAlignment.CENTER -> (size.width/2)-(textWidth/2)
                    OutlineBoxTitleAlignment.RIGHT -> (size.width-(textWidth+10.dp))
                })
                .background(Color(0xFF2D2D2D))
                .onGloballyPositioned {
                    textWidth = with(localDensity) { it.size.width.toDp() }
                    textHeight = with(localDensity) { it.size.height.toDp() }
                }
        ) {
            Text(
                title, color = Color.White, fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 4.dp),
                fontSize = 22.sp, letterSpacing = 2.sp
            )
        }
    }
}