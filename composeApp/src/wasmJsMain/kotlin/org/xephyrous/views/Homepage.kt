package org.xephyrous.views

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import org.xephyrous.apis.OAuth
import org.xephyrous.components.homepageTemplate
import org.xephyrous.data.ViewModel

@Composable
fun Homepage(navController: NavController, viewModel: ViewModel, modifier: Modifier = Modifier) {
    homepageTemplate(navController) {
        val coroutineScope = rememberCoroutineScope()

        Column {
            Text("Homepage")
            Button(
                onClick = {
                    coroutineScope.launch {
                        OAuth.redirect(
                            scope = arrayOf("openid", "email", "https://www.googleapis.com/auth/datastore"),
                            prompt = arrayOf("consent", "select_account")
                        )
                    }
                }
            ) {
                Text("Log In!")
            }
        }
    }
}