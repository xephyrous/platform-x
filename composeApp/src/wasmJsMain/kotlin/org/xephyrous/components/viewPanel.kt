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
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.sp

@Composable
fun viewPanel(panelTitle: String, startingSize: DpSize, openSize: DpSize, showPanel: Boolean, closeHandler : () -> Unit, content: @Composable () -> Unit) {
    val width by animateDpAsState(if (showPanel) openSize.width else startingSize.width)
    val height by animateDpAsState(if (showPanel) openSize.height else startingSize.height)

    AnimatedVisibility(
        visible = showPanel,
        enter = fadeIn(
            animationSpec = tween(durationMillis = 150)
        ),
        exit = fadeOut(
            animationSpec = tween(durationMillis = 150)
        )
    ) {
        Box(
            Modifier.fillMaxSize().clickable(indication = null, interactionSource = MutableInteractionSource(), enabled = true) { closeHandler() },
            contentAlignment = Alignment.Center
        ) {
            Box(modifier = Modifier.size(width = width, height = height).clickable(enabled = false){}) {
                outlineBox(
                    title = panelTitle,
                    size = DpSize(width, height),
                    fontSize = 22.sp,
                    alignment = OutlineBoxTitleAlignment.LEFT
                ) {
                    content()
                }
            }
        }
    }
}

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

    AnimatedVisibility(
        visible = showPanel,
        enter = fadeIn(
            animationSpec = tween(durationMillis = 150)
        ),
        exit = fadeOut(
            animationSpec = tween(durationMillis = 150)
        )
    ) {
        Box(
            Modifier.fillMaxSize().clickable(indication = null, interactionSource = MutableInteractionSource(), enabled = true) { closeHandler() }
        ) {
            Box(modifier = Modifier.size(width = width, height = height).offset(x = x, y = y).clickable(enabled = false){}) {
                outlineBox(
                    title = panelTitle,
                    size = DpSize(width, height),
                    fontSize = 22.sp,
                    alignment = OutlineBoxTitleAlignment.LEFT
                ) {
                    content()
                }
            }
        }
    }
}