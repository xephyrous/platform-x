package org.xephyrous

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.xephyrous.apis.Firebase
import org.xephyrous.apis.OAuth
import org.xephyrous.apis.getAllUrlParams
import org.xephyrous.components.AlertBox
import org.xephyrous.data.UserData
import org.xephyrous.data.ViewModel
import org.xephyrous.views.*

@Serializable
enum class UserRole {
    @SerialName("anonymous")
    Anonymous,

    @SerialName("user")
    User,

    @SerialName("admin")
    Admin
}

enum class Screens {
    Homepage,
    About,
    Admin,
    Calendar,
    Contact,
    Courses,
    Event,
    Profile
}

@Composable
fun App() {
    val viewModel = remember { ViewModel() }
    val alertHandler = remember { AlertBox }
    val coroutineScope = rememberCoroutineScope()

    // Authentication check
    val params = getAllUrlParams()
    if (params.contains("access_token")) {
        viewModel.oAuthToken = params["access_token"]

        // Exchange OAuth credential for Firebase credential
        coroutineScope.launch {
            Firebase.Auth.signInWithOAuth(viewModel.oAuthToken!!).onSuccess {
                viewModel.firebaseUserInfo = it

                // Load or create user data
                if (it.isNewUser) {
//                    Firebase.Firestore.createDocument(
//                        "users",
//                        viewModel.firebaseUserInfo?.localId.toString(),
//                        UserData(UserRole.User),
//                        viewModel.firebaseUserInfo!!.idToken
//                    )
                } else {
                    Firebase.Firestore.getDocument(
                        "users/${viewModel.firebaseUserInfo?.localId}",
                        viewModel.firebaseUserInfo?.idToken ?: "" // TODO : Invalid information UI alert
                    ).onSuccess {
                        Firebase.Firestore.getDocument(
                            "users/${viewModel.firebaseUserInfo?.localId}",
                            viewModel.firebaseUserInfo!!.idToken
                        ).onSuccess {
                            viewModel.userData = it.toObject<UserData>()
                        }

                        OAuth.getUserInfo(viewModel.oAuthToken!!).onSuccess {
                            viewModel.googleUserInfo = it
                        }.onFailure {
                            // TODO : Invalid information UI alert
                        }
                    }
                }
            }
        }
    }

    // Navigation
    MaterialTheme {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2D2D2D))
        ) {
            AnimatedVisibility(
                visible = viewModel.visible,
                enter = fadeIn(animationSpec = tween(300)),
                exit = fadeOut(animationSpec = tween(300))
            ) {
                when (viewModel.currentScreen) {
                    Screens.Homepage -> Homepage(coroutineScope, viewModel, alertHandler)
                    Screens.About -> About(coroutineScope, viewModel, alertHandler)
                    Screens.Admin -> Admin(coroutineScope, viewModel, alertHandler)
                    Screens.Calendar -> Calendar(coroutineScope, viewModel, alertHandler)
                    Screens.Contact -> Contact(coroutineScope, viewModel, alertHandler)
                    Screens.Courses -> Courses(coroutineScope, viewModel, alertHandler)
                    Screens.Event -> Event(coroutineScope, viewModel, alertHandler)
                    Screens.Profile -> Profile(coroutineScope, viewModel, alertHandler)
                }
            }
        }
    }
}
