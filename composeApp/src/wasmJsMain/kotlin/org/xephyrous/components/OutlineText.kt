package org.xephyrous.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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

/**
 * Creates a white outline box around text with a title.
 *
 * This composable places a text inside a box with an optional title. The text is centered and can handle
 * multiple lines. The box size and appearance are controlled by the [size], [alignment], and other parameters.
 *
 * @param title The title of the outline box.
 * @param size The size of the outline box.
 * @param titleSize The font size for the title, default is 14.sp.
 * @param textSize The font size for the text, default is 14.sp.
 * @param alignment The alignment of the title within the box, default is [OutlineBoxTitleAlignment.LEFT].
 * @param alignmentSpacing The spacing between the title and text, default is 10.dp.
 * @param maxLines The maximum number of lines for the text, default is 1.
 * @param text The text content to be displayed inside the box.
 */
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
    outlineBox(title, size, titleSize, alignment, alignmentSpacing) {
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
                modifier = Modifier.weight(1f).padding(horizontal = 10.dp)
            )
        }
    }
}

/**
 * Creates a white outline box around text with no title.
 *
 * This composable places text inside a box without a title. The text is centered and can handle multiple lines.
 *
 * @param size The size of the outline box.
 * @param xOffset The horizontal offset of the box, default is 0.dp.
 * @param yOffset The vertical offset of the box, default is 0.dp.
 * @param textSize The font size for the text, default is 14.sp.
 * @param maxLines The maximum number of lines for the text, default is 1.
 * @param text The text content to be displayed inside the box.
 */
@Composable
fun outlineTextTitleless(
    size: DpSize,
    xOffset: Dp = 0.dp,
    yOffset: Dp = 0.dp,
    textSize: TextUnit = 14.sp,
    maxLines: Int = 1,
    text: String
) {
    outlineBoxTitleless(size, xOffset, yOffset) {
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

/**
 * Creates a clickable white outline box with text inside and no title.
 *
 * This composable places text inside a clickable box without a title. The text is centered and can handle multiple lines.
 * The box becomes clickable and triggers the provided [onClick] lambda function.
 *
 *
 * @param size The size of the outline box.
 * @param xOffset The horizontal offset of the box, default is 0.dp.
 * @param yOffset The vertical offset of the box, default is 0.dp.
 * @param textSize The font size for the text, default is 14.sp.
 * @param maxLines The maximum number of lines for the text, default is 1.
 * @param text The text content to be displayed inside the box.
 * @param onClick The lambda function to be executed when the box is clicked.
 */
@Composable
fun clickableOutlineTextTitleless(
    size: DpSize,
    xOffset: Dp = 0.dp,
    yOffset: Dp = 0.dp,
    textSize: TextUnit = 14.sp,
    maxLines: Int = 1,
    text: String,
    onClick: () -> Unit
) {
    outlineBoxTitleless(size, xOffset, yOffset) {
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

/**
 * Creates a white outline box around secure text (e.g., password), with a toggle to show or hide the text.
 *
 * This composable creates a text field inside a box with a title. The text is initially obscured by bullet characters,
 * and an icon button is provided to toggle between showing and hiding the text. This is useful for password inputs.
 *
 * @param title The title of the outline box.
 * @param size The size of the outline box.
 * @param titleSize The font size for the title, default is 14.sp.
 * @param textSize The font size for the text, default is 14.sp.
 * @param alignment The alignment of the title within the box, default is [OutlineBoxTitleAlignment.LEFT].
 * @param alignmentSpacing The spacing between the title and text, default is 10.dp.
 * @param maxLines The maximum number of lines for the text, default is 1.
 * @param text The text content to be displayed inside the box.
 */
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
            //Determine the appropriate icon based on the visibility state.
            val image = if (show) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            val description = if (show) "Hide password" else "Show password"

            //Icon button to toggle visibility of the text.
            IconButton(
                onClick = { show = !show },
                modifier = Modifier.size(size.height - 10.dp, size.height - 10.dp),
            ) {
                //Display the icon representing visibility status.
                Icon(
                    imageVector = image, description,
                    tint = Color.White
                )
            }
        }
    }
}