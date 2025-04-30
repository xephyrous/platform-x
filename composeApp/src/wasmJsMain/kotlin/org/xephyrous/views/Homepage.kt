package org.xephyrous.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.painterResource
import kotlinx.coroutines.CoroutineScope
import org.xephyrous.components.AlertBox
import org.xephyrous.components.homepageTemplate
import org.xephyrous.components.outlineBox
import org.xephyrous.data.ViewModel
import platformx.composeapp.generated.resources.Res
import platformx.composeapp.generated.resources.entire_network

@Composable
fun Homepage(coroutineScope: CoroutineScope, viewModel: ViewModel, alertHandler: AlertBox) {
    homepageTemplate(coroutineScope, viewModel, alertHandler = alertHandler) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(Res.drawable.entire_network),
                contentDescription = "background",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.Center)
                    .alpha(0.15f)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                if (viewModel.oAuthToken == null) {
                    Text(
                        "Please Log In to use PlatformX",
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                if (viewModel.oAuthToken != null) {
                    outlineBox(
                        title = "Upcoming Events",
                        size = DpSize(900.dp, 250.dp)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            if (viewModel.events.isEmpty()) {
                                Text(
                                    text = "There are currently no events.",
                                    color = Color.LightGray,
                                    fontSize = 16.sp,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            } else {
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

                    outlineBox(
                        title = "Featured Courses",
                        size = DpSize(900.dp, 250.dp)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            if (viewModel.courses.isEmpty()) {
                                Text(
                                    text = "There are currently no courses.",
                                    color = Color.LightGray,
                                    fontSize = 16.sp,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            } else {
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
    }
}
