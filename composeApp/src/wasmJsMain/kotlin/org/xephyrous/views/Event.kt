package org.xephyrous.views

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.painterResource
import org.xephyrous.components.AlertBox
import org.xephyrous.components.defaultScreen
import platformx.composeapp.generated.resources.Event
import platformx.composeapp.generated.resources.Res


@Composable
fun Event(viewController: ViewController, alertHandler: AlertBox) {
    defaultScreen(viewController, title = "Event", painter = painterResource(Res.drawable.Event), alertHandler = alertHandler) {

    }
}
