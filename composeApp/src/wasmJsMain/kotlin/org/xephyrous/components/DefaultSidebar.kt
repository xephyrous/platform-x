package org.xephyrous.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.xephyrous.Screens
import org.xephyrous.UserRole
import org.xephyrous.apis.OAuth
import org.xephyrous.data.ViewModel
import platformx.composeapp.generated.resources.*

/**
 * Creates the sidebar with navigation options based on user roles.
 *
 * The sidebar includes clickable images for each screen depending on the user's role.
 * The sidebar adapts for `UserRole.User`, `UserRole.Admin`, and `UserRole.Anonymous`.
 *
 * @param coroutineScope Coroutine scope used to launch asynchronous tasks.
 * @param viewModel ViewModel to manage screen navigation and visibility.
 * @param title The title for the sidebar.
 * @param textSize The font size for the text.
 * @param alignment The alignment of the title within the sidebar.
 * @param alignmentSpacing Spacing between elements in the sidebar.
 * @param painter Painter for the sidebar image.
 * @param contentDescription The content description for the image.
 */
@Composable
fun sidebar(
    coroutineScope: CoroutineScope,
    viewModel: ViewModel,
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
            //Creates an outline for the sidebar image with a title and specified properties.
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
            //Creates a clickable image that will navigate to the homepage when clicked.
            clickableOutlineImage(
                title = "Return",
                size = DpSize(60.dp, 60.dp),
                xOffset = 40.dp,
                yOffset = 120.dp,
                textSize = textSize,
                alignment = alignment,
                alignmentSpacing = alignmentSpacing,
                painter = painterResource(Res.drawable.Return),
                contentDescription = "Site Back Button"
            ) {
                //Navigates back to the homepage with a delay for UI transition
                coroutineScope.launch {
                    viewModel.visible = false
                    delay(300)
                    viewModel.currentScreen = Screens.Homepage
                    viewModel.visible = true
                }
            }
        }
    }
}

/**
 * Creates the home sidebar with navigation options based on user roles.
 *
 * The sidebar contains clickable images for navigation to various screens such as "About", "Profile",
 * "Contact", etc., and adapts according to the user's role (`UserRole.User`, `UserRole.Admin`, `UserRole.Anonymous`).
 *
 * @param coroutineScope Coroutine scope used to launch asynchronous tasks.
 * @param viewModel ViewModel to manage screen navigation and visibility.
 * @param textSize The font size for the text.
 * @param alignment The alignment of the title within the sidebar.
 * @param alignmentSpacing Spacing between elements in the sidebar.
 */
@Composable
fun homeSidebar(
    coroutineScope: CoroutineScope,
    viewModel: ViewModel,
    textSize: TextUnit = 14.sp,
    alignment: OutlineBoxTitleAlignment = OutlineBoxTitleAlignment.OVERHANG,
    alignmentSpacing: Dp = 30.dp
) {
    //Helper function to navigate to the specified screen with animation.
    fun navigateTo(screen: Screens) {
        coroutineScope.launch {
            viewModel.visible = false
            delay(300)  //Small delay for UI transition effect
            viewModel.currentScreen = screen
            viewModel.visible = true
        }
    }

    Box(
        Modifier
            .fillMaxHeight()
            .width(110.dp)
    ) {
        Box(
            Modifier.fillMaxSize().verticalScroll(rememberScrollState(), reverseScrolling = true)
        ) {
            //Check user role and display different navigation options based on the role
            when (viewModel.userData?.role) {
                UserRole.User -> {
                    //User role options: About, Profile, Contact, Calendar, Courses, Events
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
                    ) { navigateTo(Screens.About) }

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
                    ) { navigateTo(Screens.Profile) }

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
                    ) { navigateTo(Screens.Contact) }

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
                    ) { navigateTo(Screens.Calendar) }

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
                    ) { navigateTo(Screens.Courses) }

                    clickableOutlineImage(
                        title = "Events",
                        size = DpSize(60.dp, 60.dp),
                        xOffset = 40.dp,
                        yOffset = 480.dp,
                        textSize = textSize,
                        alignment = alignment,
                        alignmentSpacing = alignmentSpacing,
                        painter = painterResource(Res.drawable.Event),
                        contentDescription = "Events"
                    ) { navigateTo(Screens.Event) }
                }

                UserRole.Admin -> {
                    //Admin role options: All user options + Admin
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
                    ) { navigateTo(Screens.About) }

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
                    ) { navigateTo(Screens.Profile) }

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
                    ) { navigateTo(Screens.Contact) }

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
                    ) { navigateTo(Screens.Calendar) }

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
                    ) { navigateTo(Screens.Courses) }

                    clickableOutlineImage(
                        title = "Events",
                        size = DpSize(60.dp, 60.dp),
                        xOffset = 40.dp,
                        yOffset = 480.dp,
                        textSize = textSize,
                        alignment = alignment,
                        alignmentSpacing = alignmentSpacing,
                        painter = painterResource(Res.drawable.Event),
                        contentDescription = "Events"
                    ) { navigateTo(Screens.Event) }

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
                    ) { navigateTo(Screens.Admin) }
                }

                UserRole.Anonymous -> {
                    //Anonymous users can only access Login,About,and Contact
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
                    ) { navigateTo(Screens.About) }

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
                    ) { navigateTo(Screens.Contact) }
                }

                else -> {

                }
            }
        }
    }
}
