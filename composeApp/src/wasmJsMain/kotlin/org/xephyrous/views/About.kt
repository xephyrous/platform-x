package org.xephyrous.views

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import org.jetbrains.compose.resources.painterResource
import org.xephyrous.components.*
import platformx.composeapp.generated.resources.Res
import platformx.composeapp.generated.resources.About

@Composable
fun About(navController: NavController, modifier: Modifier = Modifier) {
    defaultScreen(
        navController = navController,
        title = "About",
        painter = painterResource(Res.drawable.About)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 1.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            outlineText(
                title = "What is PlatformX?",
                size = DpSize(600.dp, 160.dp),
                maxLines = Int.MAX_VALUE,
                text = "Platform-X is a simple yet effective and lightweight web platform that allows Students and Instructors to interact easily." +
                        "This includes easy scheduling of meetings between parties and content uploads by instructors."
            )

            Spacer(modifier = Modifier.height(30.dp))

            outlineText(
                title = "What is Xephyrous?",
                size = DpSize(600.dp, 160.dp),
                maxLines = Int.MAX_VALUE,
                text = "Xephyrous is an organization focused on making a wide variety of open source products that encompass a wide range of features and goals. " +
                        "This includes anything from free image manipulation tools to Minecraft item sorting games."
            )
        }
    }
}
