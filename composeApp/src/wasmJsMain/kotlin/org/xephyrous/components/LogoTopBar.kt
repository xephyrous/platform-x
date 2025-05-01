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

/**
 * Top bar composable displaying the website's logo.
 *
 * This composable creates a horizontal container ([Box]) with a fixed height,
 * displaying the site logo centered within it. The image is loaded from the
 * [Res.drawable.logo] resource and intended to serve as branding at the top of the screen.
 *
 * - Uses [Image] to render the logo.
 * - Container fills the width and has a height of 75dp.
 */
@Composable
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