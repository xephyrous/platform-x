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

/**
 * Displays the Profile screen.
 * This composable lays out the user's profile view, showing their profile image,
 * email, and role. It wraps the content inside a defaultScreen layout.
 *
 * @param coroutineScope Scope used for launching asynchronous actions.
 * @param viewModel The ViewModel containing user data.
 * @param alertHandler A composable used to display alerts if needed.
 */
@Composable
fun Profile(coroutineScope: CoroutineScope, viewModel: ViewModel, alertHandler: AlertBox) {
    //Wrap the content inside a default screen layout with a profile image and title
    defaultScreen(coroutineScope, viewModel, title = "Profile", painter = painterResource(Res.drawable.Profile), alertHandler = alertHandler) {

        //Center the profile content on the screen
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
            ) {
                //Display user's profile image
                outlineImageTitleless(size = DpSize(300.dp, 300.dp), painter = painterResource(Res.drawable.entire_network))

                Spacer(Modifier.width(60.dp)) //Horizontal spacing between image and info

                Column {
                    //Display user's email in a secure outlined text field
                    outlineSecureText(title = "EMAIL", size = DpSize(450.dp, 60.dp), text = viewModel.googleUserInfo!!.email)

                    Spacer(Modifier.height(60.dp)) //Vertical spacing between email and role

                    //Display user's role in a regular outlined text field
                    outlineText(title = "ROLE", size = DpSize(450.dp, 60.dp), text = viewModel.userData!!.role.toString())
                }
            }
        }
    }
}
