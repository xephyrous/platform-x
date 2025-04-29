package org.xephyrous.views

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import org.xephyrous.apis.OAuth
import org.xephyrous.components.AlertBox
import org.xephyrous.components.homepageTemplate
import org.xephyrous.data.ViewModel

@Composable
fun Homepage(coroutineScope: CoroutineScope, viewModel: ViewModel, alertHandler: AlertBox) {
    homepageTemplate(coroutineScope, viewModel, alertHandler = alertHandler) {
        Column {
            Button(
                onClick = {
                    OAuth.redirect(arrayOf("openid", "email", "https://www.googleapis.com/auth/datastore"))
                }
            ) {
                Text("Log In")
            }

            Button(
                onClick = {
                    alertHandler.displayAlert("poo", "test alert")
                }
            ) { Text("test alert") }
        }
    }
}