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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.xephyrous.data.ViewModel
import org.xephyrous.views.*

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
fun App(
    navController: NavHostController = rememberNavController()
) {
    val viewModel = remember { ViewModel() }
    val backStateEntry by navController.currentBackStackEntryAsState()
    val currentScreen = Screens.valueOf(
        backStateEntry?.destination?.route ?: Screens.Homepage.name
    )

    MaterialTheme {
        BoxWithConstraints (
            modifier = Modifier.fillMaxSize().background(Color(0xFF2D2D2D))
        ) {
            NavHost (
                navController = navController,
                startDestination = Screens.Homepage.name
            ) {
                composable(route = Screens.Homepage.name) {
                    Homepage(navController, viewModel)
                }
                composable(route = Screens.About.name) {
                    About(navController)
                }
                composable(route = Screens.Admin.name) {
                    Admin(navController)
                }
                composable(route = Screens.Calendar.name) {
                    Calendar(navController)
                }
                composable(route = Screens.Contact.name) {
                    Contact(navController)
                }
                composable(route = Screens.Courses.name) {
                    Courses(navController)
                }
                composable(route = Screens.Event.name) {
                    Event(navController)
                }
                composable(route = Screens.Profile.name) {
                    Profile(navController)
                }
            }
        }
    }
}