package org.xephyrous

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.xephyrous.apis.getAllUrlParams
import org.xephyrous.data.ViewModel
import org.xephyrous.views.*

enum class Screens{
    AnonymousHomepage,
    UserHomepage,
    AdminHomepage
}

enum class UserRole {
    Anonymous,
    User,
    Admin
}

@Composable
fun App(
    navController: NavHostController = rememberNavController()
) {
    val viewModel = remember { ViewModel() }

    // Authentication
    val params = getAllUrlParams()
    if (params.contains("access_token")) {
        println(params["access_token"])
    }

    // Navigation
    MaterialTheme {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize().background(Color(0xFF2D2D2D))
        ) {
            NavHost(
                navController = navController,
                startDestination = Screens.AnonymousHomepage.name
            ) {
                composable(route = Screens.AnonymousHomepage.name) {
                    Homepage(navController, viewModel)
                }

                composable(route = Screens.UserHomepage.name) {
                    Homepage(navController, viewModel)
                }

                composable(route = Screens.AdminHomepage.name) {
                    Homepage(navController, viewModel)
                }
            }
        }
    }
}
