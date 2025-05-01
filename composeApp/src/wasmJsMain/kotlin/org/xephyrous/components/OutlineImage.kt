package org.xephyrous.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.*

@Composable
//outlineImage component format, used by pages (no title)
fun outlineImage(
    title: String, size: DpSize,
    textSize: TextUnit = 14.sp,
    alignment: OutlineBoxTitleAlignment = OutlineBoxTitleAlignment.LEFT,
    alignmentSpacing: Dp = 10.dp,
    painter: Painter,
    contentDescription: String = ""
) {
    //formatting
    outlineBox(title, size, textSize, alignment, alignmentSpacing) {
        Image(
            painter = painter,
            modifier = Modifier.fillMaxSize().padding(15.dp),
            contentDescription = contentDescription,
        )
    }
}

@Composable
//outlineImage component format, used by pages (with title)
fun outlineImage(
    title: String, size: DpSize,
    xOffset: Dp, yOffset: Dp,
    textSize: TextUnit = 14.sp,
    alignment: OutlineBoxTitleAlignment = OutlineBoxTitleAlignment.LEFT,
    alignmentSpacing: Dp = 10.dp,
    painter: Painter,
    contentDescription: String = ""
) {
    //formatting
    outlineBox(title, size, xOffset, yOffset, textSize, alignment, alignmentSpacing) {
        Image(
            painter = painter,
            modifier = Modifier.fillMaxSize().padding(15.dp),
            contentDescription = contentDescription,
        )
    }
}

@Composable
//clickableOutlineImage component used by pages to navigate to corresponding pages or panels when clicked, default layout, used by pages
fun clickableOutlineImage(
    title: String, size: DpSize,
    textSize: TextUnit = 14.sp,
    alignment: OutlineBoxTitleAlignment = OutlineBoxTitleAlignment.LEFT,
    alignmentSpacing: Dp = 10.dp,
    painter: Painter,
    contentDescription: String = "",
    onClick: () -> Unit
) {
    //formatting, does not support Offset
    outlineBox(title, size, textSize, alignment, alignmentSpacing) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize().padding(15.dp).clickable{ onClick() }
        )
    }
}
//clickableOutlineImage component used by pages to navigate to corresponding pages or panels when clicked, can change layout, used by pages
@Composable
fun clickableOutlineImage(
    title: String, size: DpSize,
    xOffset: Dp, yOffset: Dp,
    textSize: TextUnit = 14.sp,
    alignment: OutlineBoxTitleAlignment = OutlineBoxTitleAlignment.LEFT,
    alignmentSpacing: Dp = 10.dp,
    painter: Painter,
    contentDescription: String = "",
    onClick: () -> Unit
) {
    //formatting, supports Offset
    outlineBox(title, size, xOffset, yOffset, textSize, alignment, alignmentSpacing) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize().padding(15.dp).clickable{ onClick() }
        )
    }
}

@Composable
//outlineImage component with no title, used by pages
fun outlineImageTitleless(
    size: DpSize,
    painter: Painter,
    padding: Dp = 15.dp,
    contentDescription: String = ""
) {
    //formatting
    outlineBoxTitleless(size) {
        Image(
            painter = painter,
            modifier = Modifier.fillMaxSize().padding(padding),
            contentDescription = contentDescription,
        )
    }
}