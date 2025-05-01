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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import org.xephyrous.data.ViewModel

/**
 * Template composable used for structuring the homepage layout.
 *
 * This layout includes a top bar, main content area, and a sidebar aligned to the right.
 * The sidebar contains navigation buttons, while the center displays dynamic content and alerts.
 *
 * @param coroutineScope The scope for executing coroutines within this layout.
 * @param viewModel The application's shared [ViewModel] containing state and data.
 * @param textSize Optional parameter to adjust the font size used in the sidebar (default is 14sp).
 * @param alignment Determines the title alignment for sidebar outline boxes (default is [OutlineBoxTitleAlignment.OVERHANG]).
 * @param alignmentSpacing Space between the title and content in sidebar items (default is 30.dp).
 * @param alertHandler Handler for displaying alert popups using [AlertBox].
 * @param content The main screen content to be shown in the center column of the layout.
 */
@Composable
fun homepageTemplate(
    coroutineScope: CoroutineScope,
    viewModel: ViewModel,
    textSize: TextUnit = 14.sp,
    alignment: OutlineBoxTitleAlignment = OutlineBoxTitleAlignment.OVERHANG,
    alignmentSpacing: Dp = 30.dp,
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
        //Sidebar for navigation and additional actions
        homeSidebar(coroutineScope, viewModel, textSize, alignment, alignmentSpacing)
    }
}