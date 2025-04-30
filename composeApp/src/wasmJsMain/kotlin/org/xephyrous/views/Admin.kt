package org.xephyrous.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowBackIos
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
import org.xephyrous.components.*
import org.xephyrous.data.*
import platformx.composeapp.generated.resources.*
import kotlin.random.Random

var adminUpdate = false

fun generateRandomString(length: Int = 20): String {
    val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    return buildString {
        repeat(length) {
            append(chars[Random.nextInt(chars.length)])
        }
    }
}

@Composable
fun Admin(coroutineScope: CoroutineScope, viewModel: ViewModel, alertHandler: AlertBox, modifier: Modifier = Modifier) {
    defaultScreen(
        coroutineScope,
        viewModel,
        title = "Admin",
        painter = painterResource(Res.drawable.Admin),
        alertHandler = alertHandler,
    ) {
        var boxWidth by remember { mutableStateOf(0.dp) }
        var boxHeight by remember { mutableStateOf(0.dp) }

        val localDensity = LocalDensity.current

        var adminPanel by remember { mutableStateOf(false) }
        var openPanel by remember { mutableStateOf("") }

        Box(
            modifier = modifier
                .fillMaxSize()
                .onGloballyPositioned {
                    boxWidth = with(localDensity) { it.size.width.toDp() }
                    boxHeight = with(localDensity) { it.size.height.toDp() }
                }
        ) {
            clickableOutlineImage(
                title = "MODIFY USERS",
                size = DpSize(boxWidth/9, boxWidth/9),
                xOffset = boxWidth/4-boxWidth/18,
                yOffset = boxHeight/2-boxWidth/18,
                painter = painterResource(Res.drawable.modify_users),
                alignment = OutlineBoxTitleAlignment.RIGHT,
                contentDescription = "Modify users button",
            ) {
                openPanel = "Modify Users"
                adminPanel = true
            }

            clickableOutlineImage(
                title = "MODIFY EVENTS",
                size = DpSize(boxWidth/9, boxWidth/9),
                xOffset = boxWidth*2/4-boxWidth/18,
                yOffset = boxHeight/2-boxWidth/18,
                painter = painterResource(Res.drawable.modify_events),
                alignment = OutlineBoxTitleAlignment.CENTER,
                contentDescription = "Modify events button",
            ) {
                openPanel = "Modify Events"
                adminPanel = true
            }

            clickableOutlineImage(
                title = "MODIFY COURSES",
                size = DpSize(boxWidth/9, boxWidth/9),
                xOffset = boxWidth*3/4-boxWidth/18,
                yOffset = boxHeight/2-boxWidth/18,
                painter = painterResource(Res.drawable.modify_courses),
                alignment = OutlineBoxTitleAlignment.LEFT,
                contentDescription = "Modify courses button",
            ) {
                openPanel = "Modify Courses"
                adminPanel = true
            }

            viewPanel (openPanel, DpSize(boxWidth/9, boxWidth/9), DpSize(boxWidth-50.dp, boxHeight-50.dp),
                startingXOffset = boxWidth/2-boxWidth/18, startingYOffset = boxHeight, openXOffset = 25.dp, openYOffset = 25.dp,
                adminPanel, closeHandler = { adminPanel = false }
            ) {
                when(openPanel) {
                    "Modify Users" -> {
                        // user list
                    }
                    "Modify Events" -> {
                        outlineBox(
                            "Add Events", size = DpSize(boxWidth/9, boxWidth/9),
                            (boxWidth-50.dp)/3-boxWidth/18, (boxHeight-50.dp)/2-boxWidth/18,
                        ) {
                            Box(
                                Modifier.fillMaxSize().clickable{
                                    openPanel = "Add Events"
                                }, contentAlignment = Alignment.Center,
                            ) {
                                Text(text = "+", color = Color.White, fontSize = 50.sp)
                            }
                        }
                        outlineBox(
                            "Remove Events", size = DpSize(boxWidth/9, boxWidth/9),
                            (boxWidth-50.dp)*2/3-boxWidth/18, (boxHeight-50.dp)/2-boxWidth/18,
                        ) {
                            Box(
                                Modifier.fillMaxSize().clickable {
                                    coroutineScope.launch {
                                        Firebase.Firestore.listDocuments<EventData>(
                                            path = "events",
                                            idToken = viewModel.firebaseUserInfo!!.idToken
                                        ).onSuccess {
                                            viewModel.allEvents = it.toList()
                                        }.onFailure {
                                            alertHandler.displayAlert("Load Fail", "Failed to load events: ${it.message}")
                                        }
                                        openPanel = "Remove Events"
                                    }
                                }, contentAlignment = Alignment.Center,
                            ) {
                                Text(text = "-", color = Color.White, fontSize = 50.sp)
                            }
                        }
                    }
                    "Add Events" -> {
                        Column (horizontalAlignment = Alignment.CenterHorizontally) {
                            Row(Modifier.fillMaxWidth()) {
                                Text(
                                    text = "Add Events",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.weight(1f).padding(horizontal = 10.dp)
                                )
                                IconButton(
                                    onClick = { openPanel = "Modify Events" },
                                    modifier = Modifier.size(25.dp),
                                    enabled = true,
                                ) {
                                    Icon(Icons.AutoMirrored.Sharp.ArrowBackIos, contentDescription = "Go Back", Modifier.fillMaxSize(), tint = Color.White)
                                }
                            }
                            var name by remember { mutableStateOf("") }
                            var description by remember { mutableStateOf("") }
                            var location by remember { mutableStateOf("") }
                            var day by remember { mutableStateOf("") }
                            var month by remember { mutableStateOf("") }
                            var year by remember { mutableStateOf("") }
                            outlineInput(
                                "Name",
                                DpSize(boxWidth - 246.dp, 75.dp),
                                CallbackStore(
                                    EnumLambda(Callback.VALUE_CHANGE) {
                                        name = it as String
                                    }
                                )
                            )

                            Spacer(Modifier.height(25.dp))

                            outlineInput(
                                "Description",
                                DpSize(boxWidth - 246.dp, 75.dp),
                                CallbackStore(
                                    EnumLambda(Callback.VALUE_CHANGE) {
                                        description = it as String
                                    }
                                )
                            )

                            Spacer(Modifier.height(25.dp))

                            outlineInput(
                                "Location",
                                DpSize(boxWidth - 246.dp, 75.dp),
                                CallbackStore(
                                    EnumLambda(Callback.VALUE_CHANGE) {
                                        location = it as String
                                    }
                                )
                            )

                            Spacer(Modifier.height(25.dp))

                            outlineInput(
                                "Day",
                                DpSize(boxWidth - 246.dp, 75.dp),
                                CallbackStore(
                                    EnumLambda(Callback.VALUE_CHANGE) {
                                        day = it as String
                                    }
                                )
                            )

                            Spacer(Modifier.height(25.dp))

                            outlineInput(
                                "Month",
                                DpSize(boxWidth - 246.dp, 75.dp),
                                CallbackStore(
                                    EnumLambda(Callback.VALUE_CHANGE) {
                                        month = it as String
                                    }
                                )
                            )

                            Spacer(Modifier.height(25.dp))

                            outlineInput(
                                "Year",
                                DpSize(boxWidth - 246.dp, 75.dp),
                                CallbackStore(
                                    EnumLambda(Callback.VALUE_CHANGE) {
                                        year = it as String
                                    }
                                )
                            )

                            Spacer(Modifier.weight(1f))

                            outlineBoxTitleless(DpSize(150.dp, 50.dp)) {
                                Box(Modifier.fillMaxSize().clickable{
                                    coroutineScope.launch {
                                        if (adminUpdate) return@launch // guard clause
                                        adminUpdate = true
                                        val days = day.toIntOrNull() ?: return@launch
                                        if (days > 31 || days < 1) return@launch
                                        val months = month.toIntOrNull() ?: return@launch
                                        if (months > 12 || months < 1) return@launch
                                        val years = year.toIntOrNull() ?: return@launch
                                        // Push changes to database
                                        Firebase.Firestore.createDocument(
                                            "events",
                                            generateRandomString(),
                                            EventData(name.replace("_", " "), description.replace("_", " "), location.replace("_", " "), LocalDate(years, months, days)),
                                            viewModel.firebaseUserInfo!!.idToken,
                                        ).onSuccess {
                                            alertHandler.displayAlert("Success!", "Added Event Successfully")
                                        }
                                        adminUpdate = false
                                    }
                                }) {
                                    Text(
                                        text = "Add",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp).align(Alignment.Center)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                    "Remove Events" -> {
                        Column {
                            Row(Modifier.fillMaxWidth()) {
                                Text(
                                    text = "Remove Events",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.weight(1f).padding(horizontal = 10.dp)
                                )
                                IconButton(
                                    onClick = { openPanel = "Modify Events" },
                                    modifier = Modifier.size(25.dp),
                                    enabled = true,
                                ) {
                                    Icon(Icons.AutoMirrored.Sharp.ArrowBackIos, contentDescription = "Go Back", Modifier.fillMaxSize(), tint = Color.White)
                                }
                            }
                            LazyColumn (
                                Modifier.fillMaxSize().scrollable(rememberScrollState(), Orientation.Vertical),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                items(viewModel.allEvents.size) { index ->
                                    Spacer(modifier = Modifier.height(10.dp))
                                    outlineBox(
                                        title = viewModel.allEvents[index].second.name, DpSize(boxWidth - 246.dp, 100.dp),
                                    ) {
                                        Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
                                            Column(Modifier.fillMaxHeight().weight(1f)) {
                                                Text(
                                                    text = viewModel.allEvents[index].second.description.take(50) + if (viewModel.allEvents[index].second.description.length > 50) "..." else "",
                                                    maxLines = 1,
                                                    color = Color.White,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 20.sp,
                                                    textAlign = TextAlign.Left,
                                                    modifier = Modifier.weight(1f).fillMaxWidth().padding(10.dp)
                                                )
                                                Text(
                                                    text = viewModel.allEvents[index].second.location + " | " + viewModel.allEvents[index].second.time,
                                                    maxLines = 1,
                                                    color = Color.Gray,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 14.sp,
                                                    textAlign = TextAlign.Left,
                                                    modifier = Modifier.fillMaxWidth().padding(10.dp)
                                                )
                                            }
                                            Spacer(modifier = Modifier.width(10.dp))
                                            outlineBoxTitleless(DpSize(150.dp, 50.dp)) {
                                                Box(Modifier.fillMaxSize().clickable{
                                                    coroutineScope.launch {
                                                        if (adminUpdate) return@launch // guard clause
                                                        adminUpdate = true
                                                        // Push changes to database
                                                        Firebase.Firestore.deleteUser(
                                                            "events/${viewModel.allEvents[index].first}",
                                                            viewModel.firebaseUserInfo!!.idToken,
                                                        )

                                                        viewModel.allEvents = viewModel.allEvents.filterNot { it == viewModel.allEvents[index] }
                                                        adminUpdate = false
                                                    }
                                                }) {
                                                    Text(
                                                        text = "REMOVE",
                                                        color = Color.White,
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 20.sp,
                                                        textAlign = TextAlign.Center,
                                                        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp).align(Alignment.Center)
                                                    )
                                                }
                                            }
                                            Spacer(modifier = Modifier.width(10.dp))
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(10.dp))
                                }
                            }
                        }
                    }
                    "Modify Courses" -> {
                        outlineBox(
                            "Add Courses", size = DpSize(boxWidth/9, boxWidth/9),
                            (boxWidth-50.dp)/3-boxWidth/18, (boxHeight-50.dp)/2-boxWidth/18,
                        ) {
                            Box(
                                Modifier.fillMaxSize().clickable{
                                    openPanel = "Add Courses"
                                }, contentAlignment = Alignment.Center,
                            ) {
                                Text(text = "+", color = Color.White, fontSize = 50.sp)
                            }
                        }
                        outlineBox(
                            "Remove Courses", size = DpSize(boxWidth/9, boxWidth/9),
                            (boxWidth-50.dp)*2/3-boxWidth/18, (boxHeight-50.dp)/2-boxWidth/18,
                        ) {
                            Box(
                                Modifier.fillMaxSize().clickable{
                                    coroutineScope.launch {
                                        Firebase.Firestore.listDocuments<CourseData>(
                                            path = "courses",
                                            idToken = viewModel.firebaseUserInfo!!.idToken
                                        ).onSuccess {
                                            viewModel.courses = it.toList()
                                        }.onFailure {
                                            alertHandler.displayAlert("Load Fail", "Failed to load courses: ${it.message}")
                                        }
                                        openPanel = "Remove Courses"
                                    }
                                }, contentAlignment = Alignment.Center,
                            ) {
                                Text(text = "-", color = Color.White, fontSize = 50.sp)
                            }
                        }
                    }
                    "Add Courses" -> {
                        Column (horizontalAlignment = Alignment.CenterHorizontally) {
                            Row(Modifier.fillMaxWidth()) {
                                Text(
                                    text = "Add Courses",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.weight(1f).padding(horizontal = 10.dp)
                                )
                                IconButton(
                                    onClick = { openPanel = "Modify Courses" },
                                    modifier = Modifier.size(25.dp),
                                    enabled = true,
                                ) {
                                    Icon(Icons.AutoMirrored.Sharp.ArrowBackIos, contentDescription = "Go Back", Modifier.fillMaxSize(), tint = Color.White)
                                }
                            }
                            var name by remember { mutableStateOf("") }
                            var description by remember { mutableStateOf("") }
                            var location by remember { mutableStateOf("") }
                            var time by remember { mutableStateOf("") }
                            var instructor by remember { mutableStateOf("") }
                            var coursePrefix by remember { mutableStateOf("") }
                            var courseNumber by remember { mutableStateOf("") }

                            outlineInput(
                                "Name",
                                DpSize(boxWidth - 246.dp, 75.dp),
                                CallbackStore(
                                    EnumLambda(Callback.VALUE_CHANGE) {
                                        name = it as String
                                    }
                                )
                            )

                            Spacer(Modifier.height(25.dp))

                            outlineInput(
                                "Description",
                                DpSize(boxWidth - 246.dp, 75.dp),
                                CallbackStore(
                                    EnumLambda(Callback.VALUE_CHANGE) {
                                        description = it as String
                                    }
                                )
                            )

                            Spacer(Modifier.height(25.dp))

                            outlineInput(
                                "Location",
                                DpSize(boxWidth - 246.dp, 75.dp),
                                CallbackStore(
                                    EnumLambda(Callback.VALUE_CHANGE) {
                                        location = it as String
                                    }
                                )
                            )

                            Spacer(Modifier.height(25.dp))

                            outlineInput(
                                "time",
                                DpSize(boxWidth - 246.dp, 75.dp),
                                CallbackStore(
                                    EnumLambda(Callback.VALUE_CHANGE) {
                                        time = it as String
                                    }
                                )
                            )

                            Spacer(Modifier.height(25.dp))

                            outlineInput(
                                "instructor",
                                DpSize(boxWidth - 246.dp, 75.dp),
                                CallbackStore(
                                    EnumLambda(Callback.VALUE_CHANGE) {
                                        instructor = it as String
                                    }
                                )
                            )

                            Spacer(Modifier.height(25.dp))

                            outlineInput(
                                "coursePrefix",
                                DpSize(boxWidth - 246.dp, 75.dp),
                                CallbackStore(
                                    EnumLambda(Callback.VALUE_CHANGE) {
                                        coursePrefix = it as String
                                    }
                                )
                            )

                            Spacer(Modifier.height(25.dp))

                            outlineInput(
                                "courseNumber",
                                DpSize(boxWidth - 246.dp, 75.dp),
                                CallbackStore(
                                    EnumLambda(Callback.VALUE_CHANGE) {
                                        courseNumber = it as String
                                    }
                                )
                            )

                            Spacer(Modifier.weight(1f))

                            outlineBoxTitleless(DpSize(150.dp, 50.dp)) {
                                Box(Modifier.fillMaxSize().clickable{
                                    coroutineScope.launch {
                                        if (adminUpdate) return@launch // guard clause
                                        adminUpdate = true
                                        val courseNumbers = courseNumber.toIntOrNull() ?: return@launch
                                        // Push changes to database
                                        Firebase.Firestore.createDocument(
                                            "courses",
                                            name.replace("_", " "),
                                            CourseData(courseNumbers, coursePrefix, description.replace("_", " "), time.replace("_", " "), location.replace("_", " "), instructor.replace("_", " ")),
                                            viewModel.firebaseUserInfo!!.idToken
                                        ).onSuccess {
                                            alertHandler.displayAlert("Success!", "Added Course Successfully")
                                        }
                                        adminUpdate = false
                                    }
                                }) {
                                    Text(
                                        text = "Add",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp).align(Alignment.Center)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                    "Remove Courses" -> {
                        Column {
                            Row(Modifier.fillMaxWidth()) {
                                Text(
                                    text = "Remove Courses",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.weight(1f).padding(horizontal = 10.dp)
                                )
                                IconButton(
                                    onClick = { openPanel = "Modify Courses" },
                                    modifier = Modifier.size(25.dp),
                                    enabled = true,
                                ) {
                                    Icon(
                                        Icons.AutoMirrored.Sharp.ArrowBackIos,
                                        contentDescription = "Go Back",
                                        Modifier.fillMaxSize(),
                                        tint = Color.White
                                    )
                                }
                            }
                            LazyColumn(
                                Modifier.fillMaxSize().scrollable(rememberScrollState(), Orientation.Vertical),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                items(viewModel.courses.size) { index ->
                                    Spacer(modifier = Modifier.height(10.dp))
                                    outlineBox(
                                        title = "${viewModel.courses[index].second.coursePrefix}${viewModel.courses[index].second.courseNumber}",
                                        DpSize(boxWidth - 246.dp, 100.dp),
                                    ) {
                                        Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
                                            Column(Modifier.fillMaxHeight().weight(1f)) {
                                                Text(
                                                    text = viewModel.courses[index].first,
                                                    maxLines = 1,
                                                    color = Color.White,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 20.sp,
                                                    textAlign = TextAlign.Left,
                                                    modifier = Modifier.weight(1f).fillMaxWidth().padding(10.dp)
                                                )
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
                                            Spacer(modifier = Modifier.width(10.dp))
                                            outlineBoxTitleless(DpSize(150.dp, 50.dp)) {
                                                Box(Modifier.fillMaxSize().clickable {
                                                    coroutineScope.launch {
                                                        if (adminUpdate) return@launch // guard clause
                                                        adminUpdate = true
                                                        // Push changes to database
                                                        Firebase.Firestore.deleteUser(
                                                            "courses/${viewModel.courses[index].first}",
                                                            viewModel.firebaseUserInfo!!.idToken,
                                                        )

                                                        viewModel.courses =
                                                            viewModel.courses.filterNot { it == viewModel.courses[index] }
                                                        adminUpdate = false
                                                    }
                                                }) {
                                                    Text(
                                                        text = "REMOVE",
                                                        color = Color.White,
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 20.sp,
                                                        textAlign = TextAlign.Center,
                                                        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)
                                                            .align(Alignment.Center)
                                                    )
                                                }
                                            }
                                            Spacer(modifier = Modifier.width(10.dp))
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(10.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}