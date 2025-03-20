package org.xephyrous

import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.xephyrous.views.*

enum class Screens(val title: String) {
    Homepage("Homepage"),
    Login("Login"),
}

@Composable
fun App(
    navController: NavHostController = rememberNavController()
) {
    val backStateEntry by navController.currentBackStackEntryAsState()
    val currentScreen = Screens.valueOf(
        backStateEntry?.destination?.route ?: Screens.Homepage.name
    )

    Scaffold {
        NavHost (
            navController = navController,
            startDestination = Screens.Login.name
        ) {
            composable(route = Screens.Homepage.name) {
                Homepage(navController)
            }
            composable(route = Screens.Login.name) {
                Login(navController)
            }
        }
    }
}