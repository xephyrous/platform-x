package org.xephyrous.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import org.xephyrous.data.ViewModel

/**
 * Default screen layout used by most pages to maintain a consistent structure.
 *
 * This layout includes a top bar, a main content area, and a customizable right-hand sidebar
 * which typically includes navigation buttons or visual cues (e.g., images).
 * It also supports dynamic alerts via the provided [AlertBox].
 *
 *
 * @param coroutineScope The coroutine scope used for async operations in this screen.
 * @param viewModel The shared application [ViewModel] holding global state.
 * @param title Title to be displayed in the sidebar.
 * @param textSize Font size for text elements in the sidebar (default is 14sp).
 * @param alignment Alignment of sidebar titles within outline boxes (default is [OutlineBoxTitleAlignment.OVERHANG]).
 * @param alignmentSpacing Spacing between sidebar elements (default is 30.dp).
 * @param painter A [Painter] resource used to display an image in the sidebar.
 * @param contentDescription A content description for accessibility, associated with the image.
 * @param alertHandler The [AlertBox] instance responsible for showing alerts.
 * @param content A composable lambda providing the main screen content.
 */
@Composable
fun defaultScreen(
    coroutineScope: CoroutineScope,
    viewModel: ViewModel,
    title: String,
    textSize: TextUnit = 14.sp,
    alignment: OutlineBoxTitleAlignment = OutlineBoxTitleAlignment.OVERHANG,
    alignmentSpacing: Dp = 30.dp,
    painter: Painter,
    contentDescription: String = "",
    alertHandler: AlertBox,
    content: @Composable (() -> Unit) = {}
) {
    Row {
        Column(Modifier.weight(1f)) {
            topBar()
            Box(modifier = Modifier.fillMaxSize()) {
                content()
                alertHandler.createAlert()
            }
        }
        //Sidebar for buttons and visuals displayed on the right side of the screen
        sidebar(coroutineScope, viewModel, title, textSize, alignment, alignmentSpacing, painter, contentDescription)

    }
}