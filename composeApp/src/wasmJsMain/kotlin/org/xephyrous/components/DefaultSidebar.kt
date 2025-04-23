package org.xephyrous.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import org.jetbrains.compose.resources.painterResource
import platformx.composeapp.generated.resources.*

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
            .width(110.dp)
    ) {
        Box (
            Modifier.fillMaxSize().verticalScroll(rememberScrollState())
        ) {
            outlineImage(
                title = title,
                size = DpSize(60.dp, 60.dp),
                xOffset = 40.dp,
                yOffset = 30.dp,
                textSize = textSize,
                alignment = alignment,
                alignmentSpacing = alignmentSpacing,
                painter = painter,
                contentDescription = contentDescription
            )
            clickableOutlineImage(
                title = "Return",
                size = DpSize(60.dp, 60.dp),
                xOffset = 40.dp,
                yOffset = 120.dp,
                textSize = textSize,
                alignment = alignment,
                alignmentSpacing = alignmentSpacing,
                painter = painterResource(Res.drawable.Return), // replace with actual back logo
                contentDescription = "Site Back Button"
            ) {
                navController.navigate("AnonymousHomepage")
            }
        }
    }
}

@Composable
fun homeSidebar(
    navController: NavController,
    textSize: TextUnit = 14.sp,
    alignment: OutlineBoxTitleAlignment = OutlineBoxTitleAlignment.OVERHANG,
    alignmentSpacing: Dp = 30.dp
) {
    Box(
        Modifier
            .fillMaxHeight()
            .width(110.dp)
    ) {
        Box (
            Modifier.fillMaxSize().verticalScroll(rememberScrollState(), reverseScrolling = true)
        ) {
            clickableOutlineImage(
                title = "About",
                size = DpSize(60.dp, 60.dp),
                xOffset = 40.dp,
                yOffset = 30.dp,
                textSize = textSize,
                alignment = alignment,
                alignmentSpacing = alignmentSpacing,
                painter = painterResource(Res.drawable.About),
                contentDescription = "About"
            ) {
                navController.navigate("About")
            }
            clickableOutlineImage(
                title = "Profile",
                size = DpSize(60.dp, 60.dp),
                xOffset = 40.dp,
                yOffset = 120.dp,
                textSize = textSize,
                alignment = alignment,
                alignmentSpacing = alignmentSpacing,
                painter = painterResource(Res.drawable.Profile),
                contentDescription = "Profile"
            ) {
                navController.navigate("Profile")
            }
            clickableOutlineImage(
                title = "Contact",
                size = DpSize(60.dp, 60.dp),
                xOffset = 40.dp,
                yOffset = 210.dp,
                textSize = textSize,
                alignment = alignment,
                alignmentSpacing = alignmentSpacing,
                painter = painterResource(Res.drawable.Contact),
                contentDescription = "Contact"
            ) {
                navController.navigate("Contact")
            }
            clickableOutlineImage(
                title = "Calendar",
                size = DpSize(60.dp, 60.dp),
                xOffset = 40.dp,
                yOffset = 300.dp,
                textSize = textSize,
                alignment = alignment,
                alignmentSpacing = alignmentSpacing,
                painter = painterResource(Res.drawable.Calendar),
                contentDescription = "Calendar"
            ) {
                navController.navigate("Calendar")
            }
            clickableOutlineImage(
                title = "Schedule",
                size = DpSize(60.dp, 60.dp),
                xOffset = 40.dp,
                yOffset = 390.dp,
                textSize = textSize,
                alignment = alignment,
                alignmentSpacing = alignmentSpacing,
                painter = painterResource(Res.drawable.Schedule),
                contentDescription = "Schedule"
            ) {
                navController.navigate("Schedule")
            }
            clickableOutlineImage(
                title = "Courses",
                size = DpSize(60.dp, 60.dp),
                xOffset = 40.dp,
                yOffset = 480.dp,
                textSize = textSize,
                alignment = alignment,
                alignmentSpacing = alignmentSpacing,
                painter = painterResource(Res.drawable.Courses),
                contentDescription = "Courses"
            ) {
                navController.navigate("Courses")
            }
            clickableOutlineImage(
                title = "Admin",
                size = DpSize(60.dp, 60.dp),
                xOffset = 40.dp,
                yOffset = 570.dp,
                textSize = textSize,
                alignment = alignment,
                alignmentSpacing = alignmentSpacing,
                painter = painterResource(Res.drawable.Admin),
                contentDescription = "Admin"
            ) {
                navController.navigate("Admin")
            }
        }
    }
}