package org.xephyrous.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.sp

/**
 * Creates a panel that can open and close with an animation, based on the visibility state.
 * The panel expands or contracts in size when the `showPanel` state changes, with smooth transitions.
 *
 * This composable creates a panel that expands or contracts, and includes an option to close it
 * by clicking anywhere outside the panel area. The panel content is provided as a composable lambda,
 * and the transition effects are controlled with the `showPanel` state. The panel also animates
 * its size (width and height) using the `animateDpAsState` function to smoothly adjust the panel's size
 * when it opens or closes.
 *
 * @param panelTitle The title displayed in the panel's header.
 * @param startingSize The initial size of the panel when it is closed.
 * @param openSize The size of the panel when it is opened.
 * @param showPanel A boolean state that determines if the panel is open or closed.
 *                  When true, the panel will open; when false, it will close.
 * @param closeHandler A callback function invoked when the user clicks outside the panel to close it.
 * @param content A composable lambda that defines the content to be displayed inside the panel.
 */
@Composable
fun viewPanel(panelTitle: String, startingSize: DpSize, openSize: DpSize, showPanel: Boolean, closeHandler : () -> Unit, content: @Composable () -> Unit) {
    val width by animateDpAsState(if (showPanel) openSize.width else startingSize.width)
    val height by animateDpAsState(if (showPanel) openSize.height else startingSize.height)

    //Smoothly animate the panel's visibility with fade in and fade out transitions
    AnimatedVisibility(
        visible = showPanel,
        enter = fadeIn(
            animationSpec = tween(durationMillis = 150)
        ),
        exit = fadeOut(
            animationSpec = tween(durationMillis = 150)
        )
    ) {
        //Panel container that can be clicked to close the panel
        Box(
            Modifier.fillMaxSize().clickable(indication = null, interactionSource = MutableInteractionSource(), enabled = true) { closeHandler() },
            contentAlignment = Alignment.Center
        ) {
            //Content inside the panel, with size adjustments based on the open/close state
            Box(modifier = Modifier.size(width = width, height = height).clickable(enabled = false){}) {
                outlineBox(
                    title = panelTitle,
                    size = DpSize(width, height),
                    fontSize = 22.sp,
                    alignment = OutlineBoxTitleAlignment.LEFT
                ) {
                    content()  //Panel content passed in as composable lambda
                }
            }
        }
    }
}

/**
 * Creates a panel that can open and close with animation and supports both size and position transitions.
 * This composable allows the panel to change its size and position (X and Y offsets) smoothly when toggling visibility.
 * It includes a close button that can be clicked anywhere outside the panel to close it.
 *
 * @param panelTitle The title displayed in the panel's header.
 * @param startingSize The initial size of the panel when it is closed.
 * @param openSize The size of the panel when it is opened.
 * @param startingXOffset The initial X offset of the panel when it is closed.
 * @param startingYOffset The initial Y offset of the panel when it is closed.
 * @param openXOffset The X offset of the panel when it is opened.
 * @param openYOffset The Y offset of the panel when it is opened.
 * @param showPanel A boolean state that determines if the panel is open or closed.
 *                  When true, the panel will open; when false, it will close.
 * @param closeHandler A callback function invoked when the user clicks outside the panel to close it.
 * @param content A composable lambda that defines the content to be displayed inside the panel.
 */
@Composable
fun viewPanel(
    panelTitle: String,
    startingSize: DpSize,
    openSize: DpSize,
    startingXOffset: Dp,
    startingYOffset: Dp,
    openXOffset: Dp,
    openYOffset: Dp,
    showPanel: Boolean,
    closeHandler : () -> Unit,
    content: @Composable () -> Unit)
{
    val width by animateDpAsState(if (showPanel) openSize.width else startingSize.width)
    val height by animateDpAsState(if (showPanel) openSize.height else startingSize.height)

    val x by animateDpAsState(if (showPanel) openXOffset else startingXOffset)
    val y by animateDpAsState(if (showPanel) openYOffset else startingYOffset)

    //Smoothly animate the panel's visibility with fade in and fade out transitions
    AnimatedVisibility(
        visible = showPanel,
        enter = fadeIn(
            animationSpec = tween(durationMillis = 150)
        ),
        exit = fadeOut(
            animationSpec = tween(durationMillis = 150)
        )
    ) {
        //Panel container that can be clicked to close the panel
        Box(
            Modifier.fillMaxSize().clickable(indication = null, interactionSource = MutableInteractionSource(), enabled = true) { closeHandler() }
        ) {
            //Content inside the panel, with size and position adjustments based on the open/close state
            Box(modifier = Modifier.size(width = width, height = height).offset(x = x, y = y).clickable(enabled = false){}) {
                outlineBox(
                    title = panelTitle,
                    size = DpSize(width, height),
                    fontSize = 22.sp,
                    alignment = OutlineBoxTitleAlignment.LEFT
                ) {
                    content()  //Panel content passed in as composable lambda
                }
            }
        }
    }
}