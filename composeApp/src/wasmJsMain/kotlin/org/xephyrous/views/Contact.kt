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


@Composable
//Contact Page, using defaultScreen component for basic format of page
fun Contact(coroutineScope: CoroutineScope, viewModel: ViewModel, alertHandler: AlertBox) {
    defaultScreen(coroutineScope, viewModel, title = "Contact", painter = painterResource(Res.drawable.Contact), alertHandler = alertHandler) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Using outlineText component to get white box with text in it for each

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

