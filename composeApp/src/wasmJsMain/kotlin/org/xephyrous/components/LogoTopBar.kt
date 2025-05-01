package org.xephyrous.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import platformx.composeapp.generated.resources.Res
import platformx.composeapp.generated.resources.logo

@Composable
//formatting for website image(logo)
fun topBar() {
    Box(
        Modifier
            .fillMaxWidth()
            .height(75.dp)
    ) {
        Box {
            Image(
                painter = painterResource(Res.drawable.logo),
                contentDescription = "Site Logo",
            )
        }
    }
}