package org.xephyrous.views

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.xephyrous.apis.OAuth
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

        viewPanel ("TEST PANEL", DpSize(500.dp, 500.dp), panel, closeHandler = { panel = false }) {
            Text("Hello World")
        }
    }
}