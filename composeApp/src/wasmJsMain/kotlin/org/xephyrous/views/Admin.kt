package org.xephyrous.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import org.jetbrains.compose.resources.painterResource
import org.xephyrous.components.defaultScreen
import platformx.composeapp.generated.resources.Res
import platformx.composeapp.generated.resources.Admin


@Composable
fun Admin(navController: NavController, modifier: Modifier = Modifier) {
    defaultScreen(navController = navController, title = "Admin", painter = painterResource(Res.drawable.Admin)) {

    }
}
