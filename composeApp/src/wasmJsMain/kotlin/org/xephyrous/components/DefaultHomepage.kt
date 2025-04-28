package org.xephyrous.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.xephyrous.data.ViewModel
import org.xephyrous.views.ViewController

@Composable
fun homepageTemplate(
    viewController: ViewController,
    viewModel: ViewModel,
    textSize: TextUnit = 14.sp,
    alignment: OutlineBoxTitleAlignment = OutlineBoxTitleAlignment.OVERHANG,
    alignmentSpacing: Dp = 30.dp,
    alertHandler: AlertBox,
    content: @Composable (() -> Unit) = {}
) {
    Row {
        Column (Modifier.weight(1f)) {
            topBar()
            Box(modifier = Modifier.fillMaxSize()) {
                content()
                alertHandler.createAlert()
            }
        }
        homeSidebar(viewController, viewModel, textSize, alignment, alignmentSpacing)
    }
}