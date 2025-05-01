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
import platformx.composeapp.generated.resources.Contact
import platformx.composeapp.generated.resources.Res

/**
 * Contact page for displaying contact information.
 *
 * This composable creates a contact page with the following:
 * - Displays a title "Contact" in the top bar using [defaultScreen].
 * - Shows three sections with contact information (Email, Website, GitHub) inside
 *   a centered column layout.
 * - Each section is styled with the [outlineText] component, which provides a
 *   white box with text.
 *
 * The layout is designed to be centered, and each piece of contact information is
 * separated by a 30dp space.
 *
 * @param coroutineScope The coroutine scope used for launching asynchronous tasks.
 * @param viewModel The [ViewModel] that holds the app's data and business logic.
 * @param alertHandler The [AlertBox] used for displaying alerts.
 */

@Composable
fun Contact(coroutineScope: CoroutineScope, viewModel: ViewModel, alertHandler: AlertBox) {
    //Base layout structure for the screen with title and icon.
    defaultScreen(coroutineScope, viewModel, title = "Contact", painter = painterResource(Res.drawable.Contact), alertHandler = alertHandler) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Using outlineText component to display each contact info section
            outlineText(
                title = "EMAIL",
                size = DpSize(300.dp, 50.dp),
                text = "xephyrous.dev@gmail.com"
            )

            Spacer(modifier = Modifier.height(30.dp))

            outlineText(
                title = "WEBSITE",
                size = DpSize(300.dp, 50.dp),
                text = "xephyrous.org"
            )

            Spacer(modifier = Modifier.height(30.dp))

            outlineText(
                title = "GITHUB",
                size = DpSize(300.dp, 50.dp),
                text = "https://github.com/xephyrous"
            )
        }
    }
}

