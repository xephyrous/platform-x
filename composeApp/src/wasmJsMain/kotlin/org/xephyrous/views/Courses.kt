package org.xephyrous.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import org.jetbrains.compose.resources.painterResource
import org.xephyrous.components.AlertBox
import org.xephyrous.components.defaultScreen
import org.xephyrous.components.outlineText
import org.xephyrous.data.ViewModel
import platformx.composeapp.generated.resources.Courses
import platformx.composeapp.generated.resources.Res

@Composable
fun Courses(coroutineScope: CoroutineScope, viewModel: ViewModel, alertHandler: AlertBox) {
    defaultScreen(
        coroutineScope, viewModel, title = "Courses", painter = painterResource(Res.drawable.Courses), alertHandler = alertHandler) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            outlineText(
                title = "CLOUD COMPUTING AND DISTRIBUTED SYSTEMS",
                size = DpSize(600.dp, 100.dp),
                text = "Gain expertise in cloud platforms (AWS, Azure, GCP), microservices, and distributed computing paradigms.",
                maxLines = 5
            )
            outlineText(
                title = "FULL-STACK WEB DEVELOPMENT",
                size = DpSize(600.dp, 100.dp),
                text = "Master frontend and backend development with technologies like React, Node.js, databases, and cloud deployment.",
                maxLines = 5
            )
            outlineText(
                title = "SECURE SOFTWARE ENGINEERING",
                size = DpSize(600.dp, 100.dp),
                text = "Learn how to build secure applications by understanding common vulnerabilities, encryption techniques, and security best practices.",
                maxLines = 5
            )
            outlineText(
                title = "SOFTWARE ARCHITECTURE AND DESIGN PATTERNS",
                size = DpSize(600.dp, 100.dp),
                text = "Understand architectural styles, design principles, and common patterns for building scalable and maintainable software systems.",
                maxLines = 5
            )
        }
    }
}