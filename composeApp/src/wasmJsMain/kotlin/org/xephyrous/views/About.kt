package org.xephyrous.views

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import org.jetbrains.compose.resources.painterResource
import org.xephyrous.components.AlertBox
import org.xephyrous.components.defaultScreen
import org.xephyrous.components.outlineText
import org.xephyrous.data.ViewModel
import platformx.composeapp.generated.resources.About
import platformx.composeapp.generated.resources.Res

@Composable
fun About(coroutineScope: CoroutineScope, viewModel: ViewModel, alertHandler: AlertBox, modifier: Modifier = Modifier) {
    defaultScreen(
        coroutineScope,
        viewModel,
        title = "About",
        painter = painterResource(Res.drawable.About),
        alertHandler = alertHandler,
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            outlineText(
                title = "What is PlatformX?",
                size = DpSize(600.dp, 160.dp),
                maxLines = Int.MAX_VALUE,
                text = "Platform-X is a simple yet effective and lightweight web platform that allows Students and Admins to interact easily." +
                        "This includes easy scheduling of events between parties and content uploads by administrators."
            )

            Spacer(modifier = Modifier.height(30.dp))

            outlineText(
                title = "What is Xephyrous?",
                size = DpSize(600.dp, 160.dp),
                maxLines = Int.MAX_VALUE,
                text = "Xephyrous is an organization focused on making a wide variety of open source products that encompass a wide range of features and goals. " +
                        "This includes anything from free image manipulation tools to Minecraft item sorting games."
            )
        }
    }
}
