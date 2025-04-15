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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
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
    fontSize: TextUnit = 14.sp,
    alignment: OutlineBoxTitleAlignment = OutlineBoxTitleAlignment.LEFT,
    alignmentSpacing: Dp = 10.dp,
    content: @Composable (() -> Unit) = {}
) {
    var textWidth by remember { mutableStateOf(0.dp) }
    var textHeight by remember { mutableStateOf(0.dp) }

    var boxWidth by remember { mutableStateOf(0.dp) }
    var boxHeight by remember { mutableStateOf(0.dp) }

    val localDensity = LocalDensity.current

    Box {
        Box (
            modifier = Modifier
                .offset(y = (textHeight/2))
                .border(4.dp, Color.White)
                .shadow(4.dp, RoundedCornerShape(0.dp))
                .width(maxOf(textWidth, size.width))
                .height(maxOf(50.dp, size.height)).onGloballyPositioned {
                    boxWidth = with(localDensity) { it.size.width.toDp() }
                    boxHeight = with(localDensity) { it.size.height.toDp() }
                }
        ) {
            content()
        }
        Box (
            modifier = Modifier
                .offset(x = when(alignment) {
                    OutlineBoxTitleAlignment.OVERHANG -> -(textWidth) + alignmentSpacing
                    OutlineBoxTitleAlignment.LEFT -> alignmentSpacing
                    OutlineBoxTitleAlignment.CENTER -> (boxWidth/2)-(textWidth/2)
                    OutlineBoxTitleAlignment.RIGHT -> (boxWidth-(textWidth+alignmentSpacing))
                })
                .background(Color(0xFF2D2D2D))
                .onGloballyPositioned {
                    textWidth = with(localDensity) { it.size.width.toDp() }
                    textHeight = with(localDensity) { it.size.height.toDp() }
                }
        ) {
            Text(
                title, color = Color.White, fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 4.dp).wrapContentSize(unbounded = true),
                fontSize = fontSize, letterSpacing = 2.sp
            )
        }
    }
}