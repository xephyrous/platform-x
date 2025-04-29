package org.xephyrous.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import org.xephyrous.apis.OAuth
import org.xephyrous.components.AlertBox
import org.xephyrous.components.homepageTemplate
import org.xephyrous.components.outlineBox
import org.xephyrous.data.ViewModel

@Composable
fun Homepage(coroutineScope: CoroutineScope, viewModel: ViewModel, alertHandler: AlertBox) {
    homepageTemplate(coroutineScope, viewModel, alertHandler = alertHandler) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // ✅ This block is kept completely unchanged
            if (viewModel.oAuthToken == null) {
                Button(
                    onClick = {
                        OAuth.redirect(arrayOf("openid", "email", "https://www.googleapis.com/auth/datastore"))
                    },
                    modifier = Modifier.width(200.dp).height(60.dp)
                ) {
                    Text("Log In", fontSize = 20.sp)
                }
            }

            // 🔽 Dynamic Events Panel
            if (viewModel.events.isNotEmpty()) {
                outlineBox(
                    title = "Upcoming Events",
                    size = DpSize(900.dp, 250.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        viewModel.events.take(3).forEach { event ->
                            Text(
                                text = "• ${event.name} - ${event.time.month}/${event.time.day}/${event.time.year}",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
            }

            // 🔽 Dynamic Courses Panel
            if (viewModel.courses.isNotEmpty()) {
                outlineBox(
                    title = "Featured Courses",
                    size = DpSize(900.dp, 250.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        viewModel.courses.take(3).forEach { (name, course) ->
                            Text(
                                text = "• ${course.coursePrefix}${course.courseNumber} - $name",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
