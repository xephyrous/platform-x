package org.xephyrous.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.xephyrous.apis.Firebase
import org.xephyrous.components.AlertBox
import org.xephyrous.components.defaultScreen
import org.xephyrous.components.outlineBox
import org.xephyrous.components.viewPanel
import org.xephyrous.data.CourseData
import org.xephyrous.data.ViewModel
import platformx.composeapp.generated.resources.Courses
import platformx.composeapp.generated.resources.Res

/**
 * Displays the Courses screen, showing a list of courses fetched from Firebase Firestore.
 * Each course can be clicked to show detailed info in a dialog panel.
 *
 *
 * @param coroutineScope Scope for launching coroutines.
 * @param viewModel The shared application state and user/course data.
 * @param alertHandler A component used to show error messages if needed.
 */
@Composable
fun Courses(
    coroutineScope: CoroutineScope, viewModel: ViewModel, alertHandler: AlertBox
) {
    //State to control visibility of the course detail dialog
    var showDialog by remember { mutableStateOf(false) }

    //State to track which course is selected for detail view
    var selectedCourse by remember { mutableStateOf(Pair("", CourseData(0, "", "", "", "", ""))) }

    //Load the list of courses when OAuth token becomes available
    LaunchedEffect(viewModel.oAuthToken) {
        coroutineScope.launch {
            Firebase.Firestore.listDocuments<CourseData>(
                path = "courses",
                idToken = viewModel.firebaseUserInfo!!.idToken
            ).onSuccess {
                viewModel.courses = it.toList()
            }.onFailure {
                alertHandler.displayAlert("Load Fail", "Failed to load courses: ${it.message}")
            }
        }
    }

    //Main screen layout with title and icon
    defaultScreen(
        coroutineScope,
        viewModel,
        title = "Courses",
        painter = painterResource(Res.drawable.Courses),
        alertHandler = alertHandler
    ) {
        val localDensity = LocalDensity.current

        var boxWidth by remember { mutableStateOf(0.dp) }
        var boxHeight by remember { mutableStateOf(0.dp) }

        //Column layout for entire screen content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned {
                    boxWidth = with(localDensity) { it.size.width.toDp() }
                    boxHeight = with(localDensity) { it.size.height.toDp() }
                }
                .padding(horizontal = 48.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Show message when no courses are available
            if (viewModel.courses.isEmpty()) {
                Text(
                    text = "We have no courses to show you right now!",
                    maxLines = 1,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
            //Scrollable list of courses
            LazyColumn (
                Modifier.fillMaxSize().scrollable(rememberScrollState(), Orientation.Vertical),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(viewModel.courses.size) { index ->
                    Spacer(modifier = Modifier.height(10.dp))
                    //Render each course inside an outlineBox
                    outlineBox(
                        title = "${viewModel.courses[index].second.coursePrefix}${viewModel.courses[index].second.courseNumber}", DpSize(boxWidth - 196.dp, 100.dp),
                    ) {
                        Column(Modifier.fillMaxSize().clickable{
                            //Set selected course and show detail panel
                            selectedCourse = viewModel.courses[index]
                            showDialog = true
                        }) {
                            //Display course name
                            Text(
                                text = viewModel.courses[index].first,
                                maxLines = 1,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                textAlign = TextAlign.Left,
                                modifier = Modifier.weight(1f).fillMaxWidth().padding(10.dp)
                            )
                            //Display location and time
                            Text(
                                text = viewModel.courses[index].second.location + " | " + viewModel.courses[index].second.time,
                                maxLines = 1,
                                color = Color.Gray,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Left,
                                modifier = Modifier.fillMaxWidth().padding(10.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }

/**
 * Displays a panel containing detailed information about the currently selected course.
 *
 * The panel includes:
 * - Course name
 * - Course description
 * - Instructor name
 * - Location and time
 */
    viewPanel("COURSE OVERVIEW", DpSize(0.dp, 0.dp), DpSize(1200.dp, 800.dp), showDialog, closeHandler = { showDialog = false }) {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            //Course name
            Text(
                text = selectedCourse.first,
                maxLines = 1,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(10.dp)
            )
            //Course description
            Text(
                text = selectedCourse.second.description,
                maxLines = Int.MAX_VALUE,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f).padding(10.dp)
            )
            //Instructor name
            Text(
                text = "Taught By: " + selectedCourse.second.instructor,
                maxLines = 1,
                color = Color.LightGray,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(10.dp)
            )
            //Location and time
            Text(
                text = selectedCourse.second.location + " | " + selectedCourse.second.time,
                maxLines = 1,
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(10.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
