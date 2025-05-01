package org.xephyrous.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.*

/**
 * Displays an image inside a titled [outlineBox] without offset support.
 *
 * Commonly used for static image content with a labeled border.
 *
 *
 * @param title The title displayed at the top of the outline box.
 * @param size The size of the entire outline box.
 * @param textSize The size of the title text (default: 14sp).
 * @param alignment The alignment of the title text (default: LEFT).
 * @param alignmentSpacing Space between the title and box content.
 * @param painter The image painter to render the image.
 * @param contentDescription Description for accessibility.
 */
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
            modifier = Modifier.fillMaxSize().padding(15.dp),
            contentDescription = contentDescription,
        )
    }
}

/**
 * Displays an image inside a titled [outlineBox] with offset support.
 *
 * Useful when the outline box needs positional adjustment on screen.
 *
 * @param title The title displayed at the top of the outline box.
 * @param size The size of the entire outline box.
 * @param xOffset Horizontal offset.
 * @param yOffset Vertical offset.
 * @param textSize The size of the title text.
 * @param alignment The alignment of the title text.
 * @param alignmentSpacing Space between the title and box content.
 * @param painter The image painter to render the image.
 * @param contentDescription Description for accessibility.
 */
@Composable
fun outlineImage(
    title: String, size: DpSize,
    xOffset: Dp, yOffset: Dp,
    textSize: TextUnit = 14.sp,
    alignment: OutlineBoxTitleAlignment = OutlineBoxTitleAlignment.LEFT,
    alignmentSpacing: Dp = 10.dp,
    painter: Painter,
    contentDescription: String = ""
) {
    outlineBox(title, size, xOffset, yOffset, textSize, alignment, alignmentSpacing) {
        Image(
            painter = painter,
            modifier = Modifier.fillMaxSize().padding(15.dp),
            contentDescription = contentDescription,
        )
    }
}

/**
 * Displays a clickable image inside a titled [outlineBox] without offset.
 *
 * Often used for navigation or interactivity through image buttons.
 *
 * @param title The title of the outline box.
 * @param size The size of the box.
 * @param textSize The size of the title text.
 * @param alignment The alignment of the title text.
 * @param alignmentSpacing Spacing between title and box content.
 * @param painter The image to display.
 * @param contentDescription Description for accessibility.
 * @param onClick Lambda to execute when image is clicked.
 */
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
            modifier = Modifier.fillMaxSize().padding(15.dp).clickable{ onClick() }
        )
    }
}

/**
 * Displays a clickable image inside a titled [outlineBox] with offset.
 *
 * Similar to [clickableOutlineImage] but allows positioning control.
 *
 * @param title The title of the outline box.
 * @param size The size of the box.
 * @param xOffset Horizontal offset of the box.
 * @param yOffset Vertical offset of the box.
 * @param textSize The size of the title text.
 * @param alignment The alignment of the title.
 * @param alignmentSpacing Spacing between title and content.
 * @param painter The image to display.
 * @param contentDescription Description for accessibility.
 * @param onClick Lambda to execute when image is clicked.
 */
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
    outlineBox(title, size, xOffset, yOffset, textSize, alignment, alignmentSpacing) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize().padding(15.dp).clickable{ onClick() }
        )
    }
}

/**
 * Displays a titleless image inside a border ([outlineBoxTitleless]).
 *
 * Ideal for visual elements that don't require a title label.
 *
 * @param size The size of the image box.
 * @param painter The image to render.
 * @param padding The inner padding of the image (default: 15.dp).
 * @param contentDescription Description for accessibility.
 */
@Composable
fun outlineImageTitleless(
    size: DpSize,
    painter: Painter,
    padding: Dp = 15.dp,
    contentDescription: String = ""
) {
    outlineBoxTitleless(size) {
        Image(
            painter = painter,
            modifier = Modifier.fillMaxSize().padding(padding),
            contentDescription = contentDescription,
        )
    }
}