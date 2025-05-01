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

/**
 * Enum class that represents the different title alignment options
 * for the `outlineBox` composable.
 */
enum class OutlineBoxTitleAlignment {
    OVERHANG,
    LEFT,
    CENTER,
    RIGHT
}

/**
 * A composable function that renders a box with a customizable title, border, and shadow effect.
 * The box can hold content and its title alignment can be adjusted.
 *
 * @param title The title to be displayed in the box.
 * @param size The size of the box. Defaults to a width of 200dp and height of 75dp.
 * @param fontSize The font size for the title text. Defaults to 14sp.
 * @param alignment The alignment of the title within the box. Defaults to [OutlineBoxTitleAlignment.LEFT].
 * @param alignmentSpacing The spacing between the title and the box edges. Defaults to 10dp.
 * @param backColor The background color of the box. Defaults to dark grey (`0xFF2D2D2D`).
 * @param content A composable lambda that defines the content inside the box.
 */
@Composable
fun outlineBox(
    title: String,
    size: DpSize = DpSize(200.dp, 75.dp),
    fontSize: TextUnit = 14.sp,
    alignment: OutlineBoxTitleAlignment = OutlineBoxTitleAlignment.LEFT,
    alignmentSpacing: Dp = 10.dp,
    backColor: Color = Color(0xFF2D2D2D),
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
                .shadow(4.dp, RoundedCornerShape(0.dp))
                .background(backColor)
                .width(if (alignment == OutlineBoxTitleAlignment.OVERHANG) size.width else maxOf(textWidth, size.width))
                .height(maxOf(50.dp, size.height)).onGloballyPositioned {
                    boxWidth = with(localDensity) { it.size.width.toDp() }
                    boxHeight = with(localDensity) { it.size.height.toDp() }
                }
        ) {

            //Custom border rendering logic, top wall
            when (alignment) {
                OutlineBoxTitleAlignment.OVERHANG -> {
                    Box(
                        Modifier
                            .size(boxWidth, 4.dp)
                            .offset( x = alignmentSpacing + 1.dp)
                            .background(Color.White),
                    )
                }
                OutlineBoxTitleAlignment.LEFT -> {
                    Box(
                        Modifier.size(alignmentSpacing-2.dp, 4.dp).background(Color.White),
                    )
                    Box(
                        Modifier
                            .size(boxWidth, 4.dp)
                            .offset(alignmentSpacing + 1.dp + textWidth)
                            .background(Color.White),
                    )
                }
                OutlineBoxTitleAlignment.CENTER -> {
                    Box(
                        Modifier.size((boxWidth/2 - textWidth/2) - 2.dp, 4.dp).background(Color.White),
                    )
                    Box(
                        Modifier
                            .size(boxWidth, 4.dp)
                            .offset(size.width/2 + textWidth/2 + 2.dp)
                            .background(Color.White),
                    )
                }
                OutlineBoxTitleAlignment.RIGHT -> {
                    Box(
                        Modifier.size(boxWidth-(textWidth+alignmentSpacing+4.dp), 4.dp).background(Color.White),
                    )
                    Box(
                        Modifier
                            .size(boxWidth, 4.dp)
                            .offset(size.width + 2.dp - alignmentSpacing)
                            .background(Color.White),
                    )
                }
            }

            //Left wall - with overhang difference
            if (alignment == OutlineBoxTitleAlignment.OVERHANG) {
                Box(
                    Modifier.size(4.dp, boxHeight - (textHeight/2 + 1.dp)).offset(y = textHeight/2 + 1.dp).background(Color.White),
                )
            } else {
                Box(
                    Modifier.size(4.dp, boxHeight).background(Color.White),
                )
            }

            //Right wall
            Box(
                Modifier.size(4.dp, boxHeight).offset(x = boxWidth-4.dp).background(Color.White),
            )

            //Bottom wall
            Box(
                Modifier.size(boxWidth, 4.dp).offset(y = boxHeight-4.dp).background(Color.White),
            )

            //Custom content placement inside the box
            Box(Modifier.padding(4.dp)) {
                content()
            }
        }
        Box (
            modifier = Modifier
                .offset(x = when(alignment) {
                    OutlineBoxTitleAlignment.OVERHANG -> -(textWidth) + alignmentSpacing
                    OutlineBoxTitleAlignment.LEFT -> alignmentSpacing
                    OutlineBoxTitleAlignment.CENTER -> (boxWidth/2)-(textWidth/2)
                    OutlineBoxTitleAlignment.RIGHT -> (boxWidth-(textWidth+alignmentSpacing))
                })
                .onGloballyPositioned {
                    textWidth = with(localDensity) { it.size.width.toDp() }
                    textHeight = with(localDensity) { it.size.height.toDp() }
                }
        ) {
            Text(
                title, color = Color.White, fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 4.dp).wrapContentSize(unbounded = false),
                fontSize = fontSize, letterSpacing = 2.sp
            )
        }
    }
}

/**
 * A composable function that renders a box with a customizable title, border, and shadow effect.
 * The box can hold content and its title alignment can be adjusted. This version allows for
 * additional horizontal and vertical offsets to position the box differently on the screen.
 *
 * @param title The title to be displayed in the box.
 * @param size The size of the box. Defaults to a width of 200dp and height of 75dp.
 * @param xOffset The horizontal offset to apply to the box. Defaults to 0dp.
 * @param yOffset The vertical offset to apply to the box. Defaults to 0dp.
 * @param fontSize The font size for the title text. Defaults to 14sp.
 * @param alignment The alignment of the title within the box. Defaults to [OutlineBoxTitleAlignment.LEFT].
 * @param alignmentSpacing The spacing between the title and the box edges. Defaults to 10dp.
 * @param backColor The background color of the box. Defaults to dark grey (`0xFF2D2D2D`).
 * @param content A composable lambda that defines the content inside the box.
 */
@Composable
fun outlineBox(
    title: String,
    size: DpSize = DpSize(200.dp, 75.dp),
    xOffset: Dp = 0.dp,
    yOffset: Dp = 0.dp,
    fontSize: TextUnit = 14.sp,
    alignment: OutlineBoxTitleAlignment = OutlineBoxTitleAlignment.LEFT,
    alignmentSpacing: Dp = 10.dp,
    backColor: Color = Color(0xFF2D2D2D),
    content: @Composable (() -> Unit) = {}
) {
    var textWidth by remember { mutableStateOf(0.dp) }
    var textHeight by remember { mutableStateOf(0.dp) }

    var boxWidth by remember { mutableStateOf(0.dp) }
    var boxHeight by remember { mutableStateOf(0.dp) }

    val localDensity = LocalDensity.current

    Box (
        modifier = Modifier.offset(x =  xOffset, y = yOffset)
    ) {
        Box (
            modifier = Modifier
                .offset(y = (textHeight/2))
                .shadow(4.dp, RoundedCornerShape(0.dp))
                .background(backColor)
                .width(if (alignment == OutlineBoxTitleAlignment.OVERHANG) size.width else maxOf(textWidth, size.width))
                .height(maxOf(50.dp, size.height)).onGloballyPositioned {
                    boxWidth = with(localDensity) { it.size.width.toDp() }
                    boxHeight = with(localDensity) { it.size.height.toDp() }
                }
        ) {

            //Custom border rendering logic, top wall
            when (alignment) {
                OutlineBoxTitleAlignment.OVERHANG -> {
                    Box(
                        Modifier
                            .size(boxWidth, 4.dp)
                            .offset( x = alignmentSpacing + 1.dp)
                            .background(Color.White),
                    )
                }
                OutlineBoxTitleAlignment.LEFT -> {
                    Box(
                        Modifier.size(alignmentSpacing-2.dp, 4.dp).background(Color.White),
                    )
                    Box(
                        Modifier
                            .size(boxWidth, 4.dp)
                            .offset(alignmentSpacing + 1.dp + textWidth)
                            .background(Color.White),
                    )
                }
                OutlineBoxTitleAlignment.CENTER -> {
                    Box(
                        Modifier.size((boxWidth/2 - textWidth/2) - 2.dp, 4.dp).background(Color.White),
                    )
                    Box(
                        Modifier
                            .size(boxWidth, 4.dp)
                            .offset(size.width/2 + textWidth/2 + 2.dp)
                            .background(Color.White),
                    )
                }
                OutlineBoxTitleAlignment.RIGHT -> {
                    Box(
                        Modifier.size(boxWidth-(textWidth+alignmentSpacing+4.dp), 4.dp).background(Color.White),
                    )
                    Box(
                        Modifier
                            .size(boxWidth, 4.dp)
                            .offset(size.width + 2.dp - alignmentSpacing)
                            .background(Color.White),
                    )
                }
            }

            //Left wall - with overhang difference
            if (alignment == OutlineBoxTitleAlignment.OVERHANG) {
                Box(
                    Modifier.size(4.dp, boxHeight - (textHeight/2 + 1.dp)).offset(y = textHeight/2 + 1.dp).background(Color.White),
                )
            } else {
                Box(
                    Modifier.size(4.dp, boxHeight).background(Color.White),
                )
            }

            //Right wall
            Box(
                Modifier.size(4.dp, boxHeight).offset(x = boxWidth-4.dp).background(Color.White),
            )

            //Bottom wall
            Box(
                Modifier.size(boxWidth, 4.dp).offset(y = boxHeight-4.dp).background(Color.White),
            )

            //Custom Content Server
            Box(Modifier.padding(4.dp)) {
                content()
            }
        }
        Box (
            modifier = Modifier
                .offset(x = when(alignment) {
                    OutlineBoxTitleAlignment.OVERHANG -> -(textWidth) + alignmentSpacing
                    OutlineBoxTitleAlignment.LEFT -> alignmentSpacing
                    OutlineBoxTitleAlignment.CENTER -> (boxWidth/2)-(textWidth/2)
                    OutlineBoxTitleAlignment.RIGHT -> (boxWidth-(textWidth+alignmentSpacing))
                })
                .onGloballyPositioned {
                    textWidth = with(localDensity) { it.size.width.toDp() }
                    textHeight = with(localDensity) { it.size.height.toDp() }
                }
        ) {
            Text(
                title, color = Color.White, fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 4.dp).wrapContentSize(unbounded = false),
                fontSize = fontSize, letterSpacing = 2.sp
            )
        }
    }
}
/**
 * A composable function that renders a box without a title.
 * This is useful for when you want a container without the need for a title.
 *
 *
 * @param size The size of the box. Defaults to a width of 200dp and height of 75dp.
 * @param content A composable lambda that defines the content inside the box.
 */
@Composable
fun outlineBoxTitleless(
    size: DpSize = DpSize(200.dp, 75.dp),
    content: @Composable (() -> Unit) = {}
) {
    Box (
        modifier = Modifier
            .border(4.dp, Color.White)
            .shadow(4.dp, RoundedCornerShape(0.dp))
            .width(maxOf(50.dp, size.width))
            .height(maxOf(50.dp, size.height))
    ) {
        content()
    }
}

/**
 * A composable function that renders a box without a title, with adjustable offsets for placement.
 * This is useful when you need a container without a title and with custom positioning.
 *
 * @param size The size of the box. Defaults to a width of 200dp and height of 75dp.
 * @param xOffset The x-axis offset for the box placement. Defaults to 0dp.
 * @param yOffset The y-axis offset for the box placement. Defaults to 0dp.
 * @param content A composable lambda that defines the content inside the box.
 */
@Composable
fun outlineBoxTitleless(
    size: DpSize = DpSize(200.dp, 75.dp),
    xOffset: Dp = 0.dp,
    yOffset: Dp = 0.dp,
    content: @Composable (() -> Unit) = {}
) {
    Box (
        modifier = Modifier
            .offset(x = xOffset, y = yOffset)
            .border(4.dp, Color.White)
            .shadow(4.dp, RoundedCornerShape(0.dp))
            .width(maxOf(50.dp, size.width))
            .height(maxOf(50.dp, size.height))
    ) {
        content()
    }
}