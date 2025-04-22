package org.xephyrous.components

import androidx.compose.foundation.layout.*
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
            .width(90.dp)
    ) {
        Column (
            Modifier.fillMaxSize().align(alignment = Alignment.Center)
        ) {
            outlineImage(
                title = title,
                size = DpSize(70.dp, 70.dp),
                textSize = textSize,
                alignment = alignment,
                alignmentSpacing = alignmentSpacing,
                painter = painter,
                contentDescription = contentDescription
            )
            Spacer(modifier = Modifier.height(60.dp))
            clickableOutlineImage(
                title = "Return",
                size = DpSize(70.dp, 70.dp),
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
            .width(90.dp)
    ) {
        Column (
            Modifier.fillMaxSize().align(alignment = Alignment.Center)
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            clickableOutlineImage(
                title = "About",
                size = DpSize(70.dp, 70.dp),
                textSize = textSize,
                alignment = alignment,
                alignmentSpacing = alignmentSpacing,
                painter = painterResource(Res.drawable.About),
                contentDescription = "About"
            ) {
                navController.navigate("About")
            }
            Spacer(modifier = Modifier.height(55.dp))
            clickableOutlineImage(
                title = "Profile",
                size = DpSize(70.dp, 70.dp),
                textSize = textSize,
                alignment = alignment,
                alignmentSpacing = alignmentSpacing,
                painter = painterResource(Res.drawable.Profile),
                contentDescription = "Profile"
            ) {
                navController.navigate("Profile")
            }
            Spacer(modifier = Modifier.height(55.dp))
            clickableOutlineImage(
                title = "Contact",
                size = DpSize(70.dp, 70.dp),
                textSize = textSize,
                alignment = alignment,
                alignmentSpacing = alignmentSpacing,
                painter = painterResource(Res.drawable.Contact),
                contentDescription = "Contact"
            ) {
                navController.navigate("Contact")
            }
            Spacer(modifier = Modifier.height(55.dp))
            clickableOutlineImage(
                title = "Calendar",
                size = DpSize(70.dp, 70.dp),
                textSize = textSize,
                alignment = alignment,
                alignmentSpacing = alignmentSpacing,
                painter = painterResource(Res.drawable.About),
                contentDescription = "Calendar"
            ) {
                navController.navigate("Calendar")
            }
            Spacer(modifier = Modifier.height(55.dp))
            clickableOutlineImage(
                title = "Schedule",
                size = DpSize(70.dp, 70.dp),
                textSize = textSize,
                alignment = alignment,
                alignmentSpacing = alignmentSpacing,
                painter = painterResource(Res.drawable.Schedule),
                contentDescription = "Schedule"
            ) {
                navController.navigate("Schedule")
            }
            Spacer(modifier = Modifier.height(55.dp))
            clickableOutlineImage(
                title = "Courses",
                size = DpSize(70.dp, 70.dp),
                textSize = textSize,
                alignment = alignment,
                alignmentSpacing = alignmentSpacing,
                painter = painterResource(Res.drawable.Courses),
                contentDescription = "Courses"
            ) {
                navController.navigate("Courses")
            }
            Spacer(modifier = Modifier.height(55.dp))
            clickableOutlineImage(
                title = "Admin",
                size = DpSize(70.dp, 70.dp),
                textSize = textSize,
                alignment = alignment,
                alignmentSpacing = alignmentSpacing,
                painter = painterResource(Res.drawable.Admin),
                contentDescription = "Admin"
            ) {
                navController.navigate("Admin")
            }
            Spacer(modifier = Modifier.height(55.dp))
        }
    }
}