package org.xephyrous.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.*

object AlertBox {
    private var text: String = ""
    private var title: String = ""
    private var displayed by mutableStateOf(false)

    @Composable
    fun createAlert() {
        val height by animateDpAsState(if (displayed) 80.dp else 0.dp)

        AnimatedVisibility(
            visible = displayed,
            enter = fadeIn(
                animationSpec = tween(durationMillis = 150)
            ),
            exit = fadeOut(
                animationSpec = tween(durationMillis = 150)
            )
        ) {
            Box(
                Modifier.fillMaxSize().clickable(indication = null, interactionSource = MutableInteractionSource(), enabled = true) { displayed = false },
                contentAlignment = Alignment.BottomCenter
            ) {
                Box(modifier = Modifier.size(width = 800.dp, height = height).clickable(enabled = false){}) {
                    outlineBox(
                        title = title,
                        size = DpSize(800.dp, height),
                        fontSize = 22.sp,
                        alignment = OutlineBoxTitleAlignment.LEFT
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = text,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun displayAlert(
        alertTitle: String,
        alertText: String
    ) {
        text = alertText
        title = alertTitle
        displayed = true

        GlobalScope.launch {
            delay(3000L)
            displayed = false
        }
    }
}