package org.xephyrous.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import org.jetbrains.compose.resources.painterResource
import org.xephyrous.components.defaultScreen
import platformx.composeapp.generated.resources.Res
import platformx.composeapp.generated.resources.Profile


@Composable
fun Profile(navController: NavController, modifier: Modifier = Modifier) {
    defaultScreen(navController = navController, title = "Profile", painter = painterResource(Res.drawable.Profile)) {

    }
}
