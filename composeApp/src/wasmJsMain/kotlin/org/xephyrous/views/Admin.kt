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

/**
 * Generates a random alphanumeric string of a specified [length].
 *
 * @param length The length of the random string to generate. Default is 20.
 * @return A randomly generated alphanumeric string.
 */
fun generateRandomString(length: Int = 20): String {
    val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    return buildString {
        repeat(length) {
            append(chars[Random.nextInt(chars.length)])
        }
    }
}

/**
 * Displays the Admin screen using the [defaultScreen] layout.
 *
 * This screen presents three interactive buttons allowing the admin to modify users, events,
 * and courses. Clicking each button opens the corresponding panel.
 *
 * @param coroutineScope The coroutine scope used for asynchronous operations.
 * @param viewModel The shared [ViewModel] for accessing and managing app state.
 * @param alertHandler An [AlertBox] instance for managing alert popups.
 * @param modifier Optional [Modifier] for layout adjustments.
 */
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

        //Measures the full size of the screen to dynamically place elements
        Box(
            modifier = modifier
                .fillMaxSize()
                .onGloballyPositioned {
                    boxWidth = with(localDensity) { it.size.width.toDp() }
                    boxHeight = with(localDensity) { it.size.height.toDp() }
                }
        ) {
            //Button: Opens the "Modify Users" panel
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
            //Button: Opens the "Modify Events" panel
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
            //Button: Opens the "Modify Courses" panel
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

            //Display the panel overlay based on the selected admin option
            viewPanel (openPanel, DpSize(boxWidth/9, boxWidth/9), DpSize(boxWidth-50.dp, boxHeight-50.dp),
                startingXOffset = boxWidth/2-boxWidth/18, startingYOffset = boxHeight, openXOffset = 25.dp, openYOffset = 25.dp,
                adminPanel, closeHandler = { adminPanel = false }
            ) {
                when(openPanel)
                    /**
                     * Modify Users Panel
                     *
                     * Admin can update user roles or remove users.
                     */
                {
                    "Modify Users" -> {
                        // user list
                    }
                    /**
                     * Modify Events Panel
                     *
                     * Provides admin options to either add or remove events.
                     */
                    "Modify Events" -> {
                        //Button to navigate to Add Events form
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
                        //Button to trigger async load of event list and navigate to Remove Events panel
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
                    /**
                     * Add Events Panel
                     *
                     * Form that allows the admin to input details for a new event.
                     */
                    "Add Events" -> {
                        Column (horizontalAlignment = Alignment.CenterHorizontally) {
                            //Header with title and back button
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
                            //Input fields to gather event details
                            var name by remember { mutableStateOf("") }
                            var description by remember { mutableStateOf("") }
                            var location by remember { mutableStateOf("") }
                            var day by remember { mutableStateOf("") }
                            var month by remember { mutableStateOf("") }
                            var year by remember { mutableStateOf("") }

                            /**
                             * Input fields for event details. Each input field corresponds to a specific
                             * property of the event being added (e.g., Name, Description, Location, Day,
                             * Month, Year).
                             *
                             * - Each input field is styled using the [outlineInput] component.
                             * - A [Spacer] is used between each field for consistent spacing.
                             * - The [CallbackStore] with [EnumLambda(Callback.VALUE_CHANGE)] ensures that
                             *   the state of each field is updated when the user enters data.
                             */
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

                            Spacer(Modifier.weight(1f)) //Pushes content to top if extra vertical space is available

                            //Submit Button for Adding a New Event
                            outlineBoxTitleless(DpSize(150.dp, 50.dp)) {
                                Box(Modifier.fillMaxSize().clickable{
                                    coroutineScope.launch {
                                        if (adminUpdate) return@launch //Guard clause to prevent multiple submissions
                                        adminUpdate = true
                                        //Validate input fields
                                        val days = day.toIntOrNull() ?: return@launch
                                        if (days > 31 || days < 1) return@launch
                                        val months = month.toIntOrNull() ?: return@launch
                                        if (months > 12 || months < 1) return@launch
                                        val years = year.toIntOrNull() ?: return@launch
                                        //Push new event to Firestore
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
                    /**
                     * Remove Events Panel
                     *
                     * Displays a list of all events in the system and allows the admin to remove
                     * any event from both the database and the local state. Includes a back button
                     * to return to the Modify Events menu.
                     */
                    "Remove Events" -> {
                        Column {
                            //Header with back button
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
                            //Scrollable list of event
                            LazyColumn (
                                Modifier.fillMaxSize().scrollable(rememberScrollState(), Orientation.Vertical),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                items(viewModel.allEvents.size) { index ->
                                    Spacer(modifier = Modifier.height(10.dp))
                                    //Each event displayed inside an outlineBox
                                    outlineBox(
                                        title = viewModel.allEvents[index].second.name, DpSize(boxWidth - 246.dp, 100.dp),
                                    ) {
                                        Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
                                            Column(Modifier.fillMaxHeight().weight(1f)) {
                                                //Event description (shortened if needed)
                                                Text(
                                                    text = viewModel.allEvents[index].second.description.take(50) + if (viewModel.allEvents[index].second.description.length > 50) "..." else "",
                                                    maxLines = 1,
                                                    color = Color.White,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 20.sp,
                                                    textAlign = TextAlign.Left,
                                                    modifier = Modifier.weight(1f).fillMaxWidth().padding(10.dp)
                                                )
                                                //Event location and date
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
                                            //Remove Button
                                            outlineBoxTitleless(DpSize(150.dp, 50.dp)) {
                                                Box(Modifier.fillMaxSize().clickable{
                                                    coroutineScope.launch {
                                                        if (adminUpdate) return@launch //Guard clause
                                                        adminUpdate = true
                                                        //Delete event from Firestore
                                                        Firebase.Firestore.deleteUser(
                                                            "events/${viewModel.allEvents[index].first}",
                                                            viewModel.firebaseUserInfo!!.idToken,
                                                        )

                                                        //Remove from local list
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
                    /**
                     * Modify Courses Panel
                     *
                     * Entry panel for course management, giving the admin two options:
                     * - Add a new course by navigating to the "Add Courses" form.
                     * - Remove existing courses after fetching them from the database.
                     * Utilizes the `outlineBox` component for visually distinct and interactive options.
                     */
                    "Modify Courses" -> {
                        outlineBox(
                            //Add Courses Button to navigate to the "Add Courses" panel
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
                                        //Ensure that the courses are fetched from Firestore and populated in the view model before proceeding.
                                        Firebase.Firestore.listDocuments<CourseData>(
                                            path = "courses",
                                            idToken = viewModel.firebaseUserInfo!!.idToken
                                        ).onSuccess {
                                            viewModel.courses = it.toList() //Store fetched course
                                        }.onFailure {
                                            //Alert the admin if fetching the courses failed
                                            alertHandler.displayAlert("Load Fail", "Failed to load courses: ${it.message}")
                                        }
                                        openPanel = "Remove Courses" //Proceed to remove courses after fetching
                                    }
                                }, contentAlignment = Alignment.Center,
                            ) {
                                Text(text = "-", color = Color.White, fontSize = 50.sp)
                            }
                        }
                    }
                    /**
                     * Add Courses Panel
                     *
                     * Displays a form where the admin can input all necessary information
                     * to add a new course, including name, description, location, time,
                     * instructor, course prefix, and course number.
                     * Each input uses the `outlineInput` component and changes are tracked
                     * using a `CallbackStore`. A back button returns to the Modify Courses menu.
                     */
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
                                    onClick = { openPanel = "Modify Courses" }, //Navigate back to Modify Courses panel
                                    modifier = Modifier.size(25.dp),
                                    enabled = true,
                                ) {
                                    Icon(Icons.AutoMirrored.Sharp.ArrowBackIos, contentDescription = "Go Back", Modifier.fillMaxSize(), tint = Color.White)
                                }
                            }
                            //Input fields for course details
                            var name by remember { mutableStateOf("") }
                            var description by remember { mutableStateOf("") }
                            var location by remember { mutableStateOf("") }
                            var time by remember { mutableStateOf("") }
                            var instructor by remember { mutableStateOf("") }
                            var coursePrefix by remember { mutableStateOf("") }
                            var courseNumber by remember { mutableStateOf("") }

                            /**
                             * Renders a series of input fields for entering course-related information.
                             *
                             * This section includes:
                             * - Course Description**: A multi-line input to enter the course overview.
                             * - Location: The location or room where the course is held.
                             * - Time: A text field for specifying the time the course takes place.
                             * - Instructor: The name of the course instructor.
                             * - Course Prefix: Typically a department code
                             * - Course Number: The numeric identifier
                             *
                             * Each input field is styled using the [outlineInput] component.
                             * A [Spacer] is used between each field for consistent spacing.
                             * The [CallbackStore] with [EnumLambda(Callback.VALUE_CHANGE)] ensures that
                             * the state of each field is updated when the user enters data.
                             *
                             * Layout-wise, the inputs are uniformly sized and vertically spaced with `Spacer`
                             * to maintain consistent separation.
                             */

                            //Course Name Input Field
                            outlineInput(
                                "Name",
                                DpSize(boxWidth - 246.dp, 75.dp),
                                CallbackStore(
                                    EnumLambda(Callback.VALUE_CHANGE) {
                                        name = it as String //Update course name
                                    }
                                )
                            )

                            Spacer(Modifier.height(25.dp))

                            //Course Description Input Field
                            outlineInput(
                                "Description",
                                DpSize(boxWidth - 246.dp, 75.dp),
                                CallbackStore(
                                    EnumLambda(Callback.VALUE_CHANGE) {
                                        description = it as String //Update course description
                                    }
                                )
                            )

                            Spacer(Modifier.height(25.dp))

                            //Course Location Input Field
                            outlineInput(
                                "Location",
                                DpSize(boxWidth - 246.dp, 75.dp),
                                CallbackStore(
                                    EnumLambda(Callback.VALUE_CHANGE) {
                                        location = it as String //Update course location
                                    }
                                )
                            )

                            Spacer(Modifier.height(25.dp))

                            //Course Time Input Field
                            outlineInput(
                                "time",
                                DpSize(boxWidth - 246.dp, 75.dp),
                                CallbackStore(
                                    EnumLambda(Callback.VALUE_CHANGE) {
                                        time = it as String //Update course time
                                    }
                                )
                            )

                            Spacer(Modifier.height(25.dp))

                            //Instructor Input Field
                            outlineInput(
                                "instructor",
                                DpSize(boxWidth - 246.dp, 75.dp),
                                CallbackStore(
                                    EnumLambda(Callback.VALUE_CHANGE) {
                                        instructor = it as String //Update instructor name
                                    }
                                )
                            )

                            Spacer(Modifier.height(25.dp))

                            //Course Prefix Input Field
                            outlineInput(
                                "coursePrefix",
                                DpSize(boxWidth - 246.dp, 75.dp),
                                CallbackStore(
                                    EnumLambda(Callback.VALUE_CHANGE) {
                                        coursePrefix = it as String //Update course prefix
                                    }
                                )
                            )

                            Spacer(Modifier.height(25.dp))

                            //Course Number Input Field
                            outlineInput(
                                "courseNumber",
                                DpSize(boxWidth - 246.dp, 75.dp),
                                CallbackStore(
                                    EnumLambda(Callback.VALUE_CHANGE) {
                                        courseNumber = it as String //Update course number
                                    }
                                )
                            )

                            Spacer(Modifier.weight(1f))

                            //Submit Button: Adds the new course to Firestore
                            outlineBoxTitleless(DpSize(150.dp, 50.dp)) {
                                Box(Modifier.fillMaxSize().clickable{
                                    coroutineScope.launch {
                                        if (adminUpdate) return@launch //Guard clause to avoid multiple submissions
                                        adminUpdate = true
                                        val courseNumbers = courseNumber.toIntOrNull() ?: return@launch
                                        //Push the new course details to the Firestore database
                                        Firebase.Firestore.createDocument(
                                            "courses",
                                            name.replace("_", " "),
                                            CourseData(courseNumbers, coursePrefix, description.replace("_", " "), time.replace("_", " "), location.replace("_", " "), instructor.replace("_", " ")),
                                            viewModel.firebaseUserInfo!!.idToken
                                        ).onSuccess {
                                            //Notify admin of successful course addition
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
                    /**
                     * Remove Courses Panel
                     *
                     * This panel allows the admin to view and remove existing courses from the database.
                     * The list of courses is fetched from Firestore, and each course has a "REMOVE" button
                     * that allows the admin to delete the course from the database.
                     */
                    "Remove Courses" -> {
                        Column {
                            //Header Row with "Remove Courses" title and back button
                            Row(Modifier.fillMaxWidth()) {
                                Text(
                                    text = "Remove Courses",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.weight(1f).padding(horizontal = 10.dp)
                                )
                                //Back button to navigate to the "Modify Courses" panel
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
                            //LazyColumn to display all courses that can be removed
                            LazyColumn(
                                Modifier.fillMaxSize().scrollable(rememberScrollState(), Orientation.Vertical),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                //For each course in the list, display its details and a "REMOVE" button
                                items(viewModel.courses.size) { index ->
                                    Spacer(modifier = Modifier.height(10.dp))
                                    //Box for each course, with the course details and the "REMOVE" button
                                    outlineBox(
                                        title = "${viewModel.courses[index].second.coursePrefix}${viewModel.courses[index].second.courseNumber}",
                                        DpSize(boxWidth - 246.dp, 100.dp),
                                    ) {
                                        Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
                                            Column(Modifier.fillMaxHeight().weight(1f)) {
                                                //Display the course name (first item in the tuple)
                                                Text(
                                                    text = viewModel.courses[index].first,
                                                    maxLines = 1,
                                                    color = Color.White,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 20.sp,
                                                    textAlign = TextAlign.Left,
                                                    modifier = Modifier.weight(1f).fillMaxWidth().padding(10.dp)
                                                )
                                                //Display course location and time (second item in the tuple)
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

                                            //"REMOVE" button to delete the course from the database
                                            outlineBoxTitleless(DpSize(150.dp, 50.dp)) {
                                                Box(Modifier.fillMaxSize().clickable {
                                                    coroutineScope.launch {
                                                        if (adminUpdate) return@launch //Guard clause to prevent multiple submissions
                                                        adminUpdate = true
                                                        //Push changes to Firestore: delete the selected course
                                                        Firebase.Firestore.deleteUser(
                                                            "courses/${viewModel.courses[index].first}",
                                                            viewModel.firebaseUserInfo!!.idToken,
                                                        )

                                                        //Remove the course from the local list of courses
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