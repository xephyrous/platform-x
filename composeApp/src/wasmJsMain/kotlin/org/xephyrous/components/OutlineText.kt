package org.xephyrous.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*

@Composable
fun outlineText(
    title: String, size: DpSize,
    titleSize: TextUnit = 14.sp,
    textSize: TextUnit = 14.sp,
    alignment: OutlineBoxTitleAlignment = OutlineBoxTitleAlignment.LEFT,
    alignmentSpacing: Dp = 10.dp,
    maxLines: Int = 1,
    text: String
) {
    outlineBox(title, size, textSize, alignment, alignmentSpacing) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = text,
                maxLines = maxLines,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = textSize,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun outlineTextTitleless(
    size: DpSize,
    xOffset: Dp = 0.dp,
    yOffest: Dp = 0.dp,
    textSize: TextUnit = 14.sp,
    maxLines: Int = 1,
    text: String
) {
    outlineBoxTitleless(size, xOffset, yOffest) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = text,
                maxLines = maxLines,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = textSize,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun clickableOutlineTextTitleless(
    size: DpSize,
    xOffset: Dp = 0.dp,
    yOffest: Dp = 0.dp,
    textSize: TextUnit = 14.sp,
    maxLines: Int = 1,
    text: String,
    onClick: () -> Unit
) {
    outlineBoxTitleless(size, xOffset, yOffest) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize().clickable { onClick() }
        ) {
            Text(
                text = text,
                maxLines = maxLines,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = textSize,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun outlineSecureText(
    title: String, size: DpSize,
    titleSize: TextUnit = 14.sp,
    textSize: TextUnit = 14.sp,
    alignment: OutlineBoxTitleAlignment = OutlineBoxTitleAlignment.LEFT,
    alignmentSpacing: Dp = 10.dp,
    maxLines: Int = 1,
    text: String
) {
    var show by remember { mutableStateOf(false) }

    outlineBox(title, size, titleSize, alignment, alignmentSpacing) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = if (show) text else "•".repeat(text.length),
                maxLines = maxLines,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = textSize,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f).offset(x = (size.height-10.dp)/2)
            )

            val image = if (show) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            val description = if (show) "Hide password" else "Show password"

            IconButton(
                onClick = { show = !show },
                modifier = Modifier.size(size.height - 10.dp, size.height - 10.dp),
            ) {
                Icon(
                    imageVector = image, description,
                    tint = Color.White
                )
            }
        }
    }
}