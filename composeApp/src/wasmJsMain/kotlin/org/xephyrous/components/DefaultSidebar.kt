package org.xephyrous.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.xephyrous.views.About
import org.xephyrous.views.ViewController
import org.xephyrous.views.Views
import platformx.composeapp.generated.resources.*

@Composable
fun sidebar(
    viewController: ViewController,
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
                // TODO : Navigate to correct homepage based on viewModel
                viewController.loadView(Views.AnonymousHomepage)
            }
        }
    }
}

@Composable
fun homeSidebar(
    viewController: ViewController,
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
                viewController.setIntermediate {
                    delay(5000)
                }
                viewController.loadView(Views.About)
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
                viewController.loadView(Views.Profile)
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
                viewController.loadView(Views.Contact)
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
                viewController.loadView(Views.Calendar)
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
                viewController.loadView(Views.Courses)
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
                viewController.loadView(Views.Admin)
            }
            Spacer(modifier = Modifier.height(55.dp))
        }
    }
}