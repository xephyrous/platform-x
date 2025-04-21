package org.xephyrous.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.onClick
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.*

@Composable
fun outlineImage(
    title: String, size: DpSize,
    textSize: TextUnit = 14.sp,
    alignment: OutlineBoxTitleAlignment = OutlineBoxTitleAlignment.LEFT,
    alignmentSpacing: Dp = 10.dp,
    painter: Painter,
    contentDescription: String = ""
) {
    outlineBox(title, size, textSize, alignment, alignmentSpacing) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
        )
    }
}

@Composable
fun clickableOutlineImage(
    title: String, size: DpSize,
    textSize: TextUnit = 14.sp,
    alignment: OutlineBoxTitleAlignment = OutlineBoxTitleAlignment.LEFT,
    alignmentSpacing: Dp = 10.dp,
    painter: Painter,
    contentDescription: String = "",
    onClick: () -> Unit
) {
    outlineBox(title, size, textSize, alignment, alignmentSpacing) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier.clickable{ onClick() }
        )
    }
}

@Composable
fun outlineImageTitleless(
    size: DpSize,
    painter: Painter,
    contentDescription: String = ""
) {
    outlineBox("", size, 0.sp, OutlineBoxTitleAlignment.LEFT, 0.dp) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
        )
    }
}