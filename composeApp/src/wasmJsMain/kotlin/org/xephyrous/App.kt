package org.xephyrous

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.xephyrous.apis.Firebase
import org.xephyrous.apis.getAllUrlParams
import org.xephyrous.data.UserData
import org.xephyrous.data.ViewModel
import org.xephyrous.views.*

@Serializable
enum class UserRole {
    Anonymous,
    User,
    Admin
}

@Composable
fun App() {
    val viewModel = remember { ViewModel() }
    val viewController = remember { ViewController(viewModel) }
    val coroutineScope = rememberCoroutineScope()

    // Authentication check
    val params = getAllUrlParams()
    if (params.contains("access_token")) {
        viewModel.oAuthToken = params["access_token"]
        println("<AuthToken> ${viewModel.oAuthToken}")

        // Exchange OAuth credential for Firebase credential
        coroutineScope.launch {
            Firebase.Auth.signInWithOAuth(viewModel.oAuthToken!!).onSuccess {
                viewModel.firebaseInfo = it
                println("<AuthToken> ${viewModel.firebaseInfo?.localId}")

                // Load user data
                Firebase.Firestore.getDocument(
                    "users/${viewModel.firebaseInfo?.localId}",
                    viewModel.firebaseInfo?.idToken ?: "" // TODO : Invalid information UI alert
                ).onSuccess {
                    Firebase.Firestore.getDocument(
                        "users/${viewModel.firebaseInfo?.localId}",
                        viewModel.firebaseInfo!!.idToken
                    ).onSuccess {
                        viewModel.userData = it.toObject<UserData>()

                        // Serve corresponding homepage
                        when(viewModel.userData?.role) {
                            UserRole.User -> { viewController.loadView(Views.UserHomepage) }
                            UserRole.Admin -> { viewController.loadView(Views.AdminHomepage) }
                            UserRole.Anonymous -> { }
                            null -> { }
                        }

                        println("<Role> ${viewModel.userData?.role}")
                    }
                }
            }
        }
    }

    // Navigation
    MaterialTheme {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize().background(Color(0xFF2D2D2D))
        ) {
            viewController.showView()
        }
    }
}
