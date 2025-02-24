package xdevuikit.core.components

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import xdevuikit.core.utils.DpSize

class FlexMenuController {

}

@Composable
fun VFlexMenu(
    modifier: Modifier = Modifier,
    initialSize: DpSize,
    contentAlignment: Alignment = Alignment.TopCenter,
    controller: FlexMenuController,
    content: @Composable FlexMenuController.() -> Unit
) {
    FlexBox {
        Row(

        ) {

        }
    }
}

@Composable
fun HFlexMenu(
    modifier: Modifier = Modifier,
    initialSize: DpSize,
    contentAlignment: Alignment = Alignment.TopCenter,
    controller: FlexMenuController,
    content: @Composable FlexMenuController.() -> Unit
) {

}