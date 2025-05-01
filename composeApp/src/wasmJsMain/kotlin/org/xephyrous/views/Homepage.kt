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
//Homepage with website image
fun Homepage(coroutineScope: CoroutineScope, viewModel: ViewModel, alertHandler: AlertBox) {
    homepageTemplate(coroutineScope, viewModel, alertHandler = alertHandler) {
        Box(modifier = Modifier.fillMaxSize()) {

            //Website Image Format
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
                //If no user has been logged in then log in button will appear
                if (viewModel.oAuthToken == null) {
                    Text(
                        "Please Log In to use PlatformX",
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                //If user has logged in then upcoming events and featured courses will appear if there are some
                if (viewModel.oAuthToken != null) {
                    //upcoming events using outlineBox component
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
                                //event formatting
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
                    //featured courses using outlineBox component
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
                                //formatting for courses
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
