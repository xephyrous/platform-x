package org.xephyrous.views

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import org.xephyrous.Screens

@Composable
fun Login(navController: NavController, modifier: Modifier = Modifier) {
    MaterialTheme {
        Column {
            Text("Login")
            Button (
                onClick = { navController.navigate(Screens.Homepage.name) },
            ) {
                Text("Homepage")
            }
        }
    }
}
