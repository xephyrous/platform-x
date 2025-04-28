package org.xephyrous.views

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.xephyrous.components.AlertBox
import org.xephyrous.components.defaultScreen
import org.xephyrous.components.outlineText
import platformx.composeapp.generated.resources.Contact
import platformx.composeapp.generated.resources.Res


@Composable
fun Contact(viewController: ViewController, alertHandler: AlertBox) {
    defaultScreen(viewController, title = "Contact", painter = painterResource(Res.drawable.Contact), alertHandler = alertHandler) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            outlineText(
                title = "EMAIL",
                size = DpSize(300.dp, 50.dp),
                text = "xephyrous.development@gmail.com"
            )

            Spacer(modifier = Modifier.height(30.dp))

            outlineText(
                title = "WEBSITE",
                size = DpSize(300.dp, 50.dp),
                text = "xephyrous.org"
            )

            Spacer(modifier = Modifier.height(30.dp))

            outlineText(
                title = "TOLL-FREE NUMBER",
                size = DpSize(300.dp, 50.dp),
                text = "(888) 123-4567"
            )
        }
    }

}

