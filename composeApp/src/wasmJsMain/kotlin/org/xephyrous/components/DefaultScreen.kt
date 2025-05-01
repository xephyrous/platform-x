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

@Composable
//Default screen used by most pages to get a basic format for each page
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
        //Sidebar for buttons located on right side of pages
        sidebar(coroutineScope, viewModel, title, textSize, alignment, alignmentSpacing, painter, contentDescription)

    }
}