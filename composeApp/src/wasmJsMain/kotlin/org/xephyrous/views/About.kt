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
/**
 * Displays the "About" screen using a standardized layout provided by [defaultScreen].
 *
 * This screen explains what "PlatformX" and "Xephyrous" are using styled text containers.
 * @param coroutineScope The coroutine scope used for managing asynchronous tasks within the screen.
 * @param viewModel The shared [ViewModel] instance for accessing UI and business logic.
 * @param alertHandler An [AlertBox] handler used for displaying alerts or messages.
 * @param modifier Optional [Modifier] for layout customization.
 */
@Composable
fun About(coroutineScope: CoroutineScope, viewModel: ViewModel, alertHandler: AlertBox, modifier: Modifier = Modifier) {

    //Base layout structure for the screen with title and icon.
    defaultScreen(
        coroutineScope,
        viewModel,
        title = "About",
        painter = painterResource(Res.drawable.About),
        alertHandler = alertHandler,
    ) {
        //Centered column layout for placing content vertically
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //First outlined text section explaining PlatformX
            outlineText(
                title = "What is PlatformX?",
                size = DpSize(600.dp, 160.dp),
                maxLines = Int.MAX_VALUE,
                text = "Platform-X is a simple yet effective and lightweight web platform that allows Students and Admins to interact easily." +
                        "This includes easy scheduling of events between parties and content uploads by administrators."
            )

            Spacer(modifier = Modifier.height(30.dp)) //Adds spacing between the sections

            //Second outlined text section explaining Xephyrous
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
