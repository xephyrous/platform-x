package org.xephyrous.views

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import org.jetbrains.compose.resources.painterResource
import org.xephyrous.components.AlertBox
import org.xephyrous.components.defaultScreen
import org.xephyrous.data.ViewModel
import platformx.composeapp.generated.resources.Event
import platformx.composeapp.generated.resources.Res


@Composable
fun Event(coroutineScope: CoroutineScope, viewModel: ViewModel, alertHandler: AlertBox) {
    defaultScreen(coroutineScope, viewModel, title = "Event", painter = painterResource(Res.drawable.Event), alertHandler = alertHandler) {

    }
}
