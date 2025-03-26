package org.xephyrous.views

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import org.xephyrous.Screens

@Composable
fun Homepage(navController: NavController, modifier: Modifier = Modifier) {
    Column {
        Text("Homepage")
        Button (
            onClick = { navController.navigate(Screens.Login.name) },
        ) {
            Text("Login")
        }
    }
}