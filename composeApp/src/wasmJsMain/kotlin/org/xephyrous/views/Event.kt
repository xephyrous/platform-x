package org.xephyrous.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import org.jetbrains.compose.resources.painterResource
import org.xephyrous.components.defaultScreen
import platformx.composeapp.generated.resources.Res
import platformx.composeapp.generated.resources.Event


@Composable
fun Event(viewController: ViewController, modifier: Modifier = Modifier) {
    defaultScreen(viewController, title = "Event", painter = painterResource(Res.drawable.Event)) {

    }
}
