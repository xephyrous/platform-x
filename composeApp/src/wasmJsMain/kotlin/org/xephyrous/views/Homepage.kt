package org.xephyrous.views

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.xephyrous.components.homepageTemplate
import org.xephyrous.components.viewPanel
import org.xephyrous.data.ViewModel

@Composable
fun Homepage(navController: NavController, viewModel: ViewModel, modifier: Modifier = Modifier) {
    homepageTemplate(navController) {
        var panel by remember { mutableStateOf(false) }

        Button(onClick = {
            panel = true
        }) { Text("open panel") }

        viewPanel ("TEST PANEL", DpSize(50.dp, 50.dp), DpSize(1600.dp, 800.dp), panel, closeHandler = { panel = false }) {
            Text("Hello World")
        }
    }
}