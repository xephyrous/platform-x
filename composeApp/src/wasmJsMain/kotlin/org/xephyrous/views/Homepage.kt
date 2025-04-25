package org.xephyrous.views

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import org.xephyrous.apis.OAuth
import org.xephyrous.components.homepageTemplate
import org.xephyrous.components.viewPanel

@Composable
fun Homepage(viewController: ViewController, modifier: Modifier = Modifier) {
    homepageTemplate(viewController) {
        var panel by remember { mutableStateOf(false) }

        Column {
            Button (
                onClick = {
                    OAuth.redirect(arrayOf("openid", "email", "https://www.googleapis.com/auth/datastore"))
                }
            ) {
                Text("Log In")
            }

            Button(
                onClick = {
                    panel = true
                }
            ) { Text("open panel") }
        }

        viewPanel ("TEST PANEL", DpSize(50.dp, 50.dp), DpSize(1600.dp, 800.dp), panel, closeHandler = { panel = false }) {
            Text("Hello World")
        }
    }
}