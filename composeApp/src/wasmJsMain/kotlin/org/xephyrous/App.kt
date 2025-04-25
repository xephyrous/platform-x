package org.xephyrous

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.xephyrous.apis.getAllUrlParams
import org.xephyrous.data.ViewModel
import org.xephyrous.views.*

enum class UserRole {
    Anonymous,
    User,
    Admin
}

@Composable
fun App() {
    val viewModel = remember { ViewModel() }
    val viewController = remember { ViewController(viewModel) }

    // Authentication check
    val params = getAllUrlParams()
    if (params.contains("access_token")) {
        viewModel.authToken = params["access_token"]
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
