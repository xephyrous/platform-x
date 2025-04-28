package org.xephyrous.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.*
import org.jetbrains.compose.resources.painterResource
import org.xephyrous.UserRole
import org.xephyrous.apis.OAuth
import org.xephyrous.data.ViewModel
import org.xephyrous.views.TransitionType
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
                viewController.loadView(Views.Homepage)
            }
        }
    }
}

@Composable
fun homeSidebar(
    viewController: ViewController,
    viewModel: ViewModel,
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
            when (viewModel.userData?.role) {
                UserRole.User -> {
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
                        viewController.loadView(Views.About)
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
                        viewController.loadView(Views.Profile)
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
                        viewController.loadView(Views.Contact)
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
                        viewController.loadView(Views.Calendar)
                    }
                    clickableOutlineImage(
                        title = "Courses",
                        size = DpSize(60.dp, 60.dp),
                        xOffset = 40.dp,
                        yOffset = 390.dp,
                        textSize = textSize,
                        alignment = alignment,
                        alignmentSpacing = alignmentSpacing,
                        painter = painterResource(Res.drawable.Courses),
                        contentDescription = "Courses"
                    ) {
                        viewController.loadView(Views.Courses)
                    }
                }
                UserRole.Admin -> {
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
                        viewController.loadView(Views.About)
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
                        viewController.loadView(Views.Profile)
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
                        viewController.loadView(Views.Contact)
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
                        viewController.loadView(Views.Calendar)
                    }
                    clickableOutlineImage(
                        title = "Courses",
                        size = DpSize(60.dp, 60.dp),
                        xOffset = 40.dp,
                        yOffset = 390.dp,
                        textSize = textSize,
                        alignment = alignment,
                        alignmentSpacing = alignmentSpacing,
                        painter = painterResource(Res.drawable.Courses),
                        contentDescription = "Courses"
                    ) {
                        viewController.loadView(Views.Courses)
                    }
                    clickableOutlineImage(
                        title = "Admin",
                        size = DpSize(60.dp, 60.dp),
                        xOffset = 40.dp,
                        yOffset = 480.dp,
                        textSize = textSize,
                        alignment = alignment,
                        alignmentSpacing = alignmentSpacing,
                        painter = painterResource(Res.drawable.Admin),
                        contentDescription = "Admin"
                    ) {
                        viewController.loadView(Views.Admin)
                    }
                }
                UserRole.Anonymous -> {
                    clickableOutlineImage(
                        title = "Login",
                        size = DpSize(60.dp, 60.dp),
                        xOffset = 40.dp,
                        yOffset = 30.dp,
                        textSize = textSize,
                        alignment = alignment,
                        alignmentSpacing = alignmentSpacing,
                        painter = painterResource(Res.drawable.entire_network),
                        contentDescription = "Login"
                    ) {
                        OAuth.redirect(arrayOf("openid", "email", "https://www.googleapis.com/auth/datastore"))
                    }
                    clickableOutlineImage(
                        title = "About",
                        size = DpSize(60.dp, 60.dp),
                        xOffset = 40.dp,
                        yOffset = 120.dp,
                        textSize = textSize,
                        alignment = alignment,
                        alignmentSpacing = alignmentSpacing,
                        painter = painterResource(Res.drawable.About),
                        contentDescription = "About"
                    ) {
                        viewController.loadView(Views.About)
                    }
                    clickableOutlineImage(
                        title = "Courses",
                        size = DpSize(60.dp, 60.dp),
                        xOffset = 40.dp,
                        yOffset = 210.dp,
                        textSize = textSize,
                        alignment = alignment,
                        alignmentSpacing = alignmentSpacing,
                        painter = painterResource(Res.drawable.Courses),
                        contentDescription = "Courses"
                    ) {
                        viewController.loadView(Views.Courses)
                    }
                }
                else -> {
                    clickableOutlineImage(
                        title = "some ting wong",
                        size = DpSize(60.dp, 60.dp),
                        xOffset = 40.dp,
                        yOffset = 30.dp,
                        textSize = textSize,
                        alignment = alignment,
                        alignmentSpacing = alignmentSpacing,
                        painter = painterResource(Res.drawable.entire_network),
                        contentDescription = "Login"
                    ) {
                        OAuth.redirect(arrayOf("openid", "email", "https://www.googleapis.com/auth/datastore"))
                    }
                }
            }
        }
    }
}