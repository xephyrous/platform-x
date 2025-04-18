package org.xephyrous.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import org.jetbrains.compose.resources.painterResource
import platformx.composeapp.generated.resources.Res
import platformx.composeapp.generated.resources.entire_network

@Composable
fun sidebar(
    navController: NavController,
    title: String,
    textSize: TextUnit = 14.sp,
    alignment: OutlineBoxTitleAlignment = OutlineBoxTitleAlignment.OVERHANG,
    alignmentSpacing: Dp = 30.dp,
    painter: Painter,
    contentDescription: String = "",
) {
    Box(
        Modifier
            .fillMaxHeight()
            .width(65.dp)
    ) {
        Column (Modifier.fillMaxSize()) {
            outlineImage(
                title = title,
                size = DpSize(50.dp, 50.dp),
                textSize = textSize,
                alignment = alignment,
                alignmentSpacing = alignmentSpacing,
                painter = painter,
                contentDescription = contentDescription
            )
            Spacer(modifier = Modifier.height(12.dp))
            clickableOutlineImage(
                title = "Return",
                size = DpSize(50.dp, 50.dp),
                textSize = textSize,
                alignment = alignment,
                alignmentSpacing = alignmentSpacing,
                painter = painterResource(Res.drawable.entire_network), // replace with actual back logo
                contentDescription = "Site Back Button"
            ) {
                navController.navigate("Homepage")
            }
        }
    }
}