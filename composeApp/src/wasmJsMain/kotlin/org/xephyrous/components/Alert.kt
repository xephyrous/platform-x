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

/**
 * Object that handles the creation and display of a customizable alert box.
 *
 * The [AlertBox] object provides a static function to display an alert with a title and a message.
 * The alert appears at the bottom of the screen and automatically disappears after a short duration (3 seconds).
 * The alert box can be dismissed by clicking anywhere outside the box.
 *
 *
 * The alert box uses animation for both appearance and disappearance:
 * - It fades in when shown.
 * - It fades out after the specified duration or when clicked.
 *
 * You can customize the text and title for each alert.
 */
object AlertBox {
    private var text: String = ""
    private var title: String = ""
    private var displayed by mutableStateOf(false)

    /**
     * Creates and displays the alert box.
     *
     * This composable function creates the alert box UI and handles its animation.
     * The alert box is shown when the `displayed` state is true, and it automatically hides after a short delay.
     * The user can dismiss the alert by clicking on the screen outside the alert box.
     *
     */

    @Composable
    fun createAlert() {
        //Height animation for the alert box
        val height by animateDpAsState(if (displayed) 80.dp else 0.dp)

        //Animated visibility for the alert box
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
                    //Outline box for the alert with title and message
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

    /**
     * Displays the alert with the provided title and message.
     *
     * This function sets the title and message for the alert, and triggers the display of the alert box.
     * The alert will automatically disappear after 3 seconds.
     *
     * @param alertTitle The title of the alert box.
     * @param alertText The message to be displayed in the alert box.
     */

    @OptIn(DelicateCoroutinesApi::class)
    fun displayAlert(
        alertTitle: String,
        alertText: String
    ) {
        //Set the title and text for the alert
        text = alertText
        title = alertTitle
        displayed = true

        //Dismiss the alert after a delay of 3 seconds
        GlobalScope.launch {
            delay(3000L)
            displayed = false
        }
    }
}