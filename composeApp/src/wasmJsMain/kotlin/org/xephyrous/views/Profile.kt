package org.xephyrous.views

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import org.jetbrains.compose.resources.painterResource
import org.xephyrous.components.*
import org.xephyrous.data.ViewModel
import platformx.composeapp.generated.resources.Profile
import platformx.composeapp.generated.resources.Res
import platformx.composeapp.generated.resources.entire_network

@Composable
fun Profile(coroutineScope: CoroutineScope, viewModel: ViewModel, alertHandler: AlertBox) {
    defaultScreen(coroutineScope, viewModel, title = "Profile", painter = painterResource(Res.drawable.Profile), alertHandler = alertHandler) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
            ) {
                outlineImageTitleless(size = DpSize(300.dp, 300.dp), painter = painterResource(Res.drawable.entire_network))
                Spacer(Modifier.width(60.dp))
                Column {
                    outlineText(title = "USERNAME", size = DpSize(450.dp, 60.dp), text = "Test")
                    Spacer(Modifier.height(60.dp))
                    outlineSecureText(title = "EMAIL", size = DpSize(450.dp, 60.dp), text = viewModel.googleUserInfo!!.email)
                    Spacer(Modifier.height(60.dp))
                    outlineText(title = "ROLE", size = DpSize(450.dp, 60.dp), text = viewModel.userData!!.role.toString())
                }
            }
        }
    }
}
