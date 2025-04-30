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
import org.xephyrous.UserRole
import org.xephyrous.apis.Firebase
import org.xephyrous.components.*
import org.xephyrous.data.EventData
import org.xephyrous.data.LocalDate
import org.xephyrous.data.ViewModel
import platformx.composeapp.generated.resources.Event
import platformx.composeapp.generated.resources.Res

fun isEnrolled(
    event: EventData,
    viewModel: ViewModel
): Boolean {
    return viewModel.userData!!.events.contains(event)
}

var updating = false

@Composable
fun Event(
    coroutineScope: CoroutineScope,
    viewModel: ViewModel,
    alertHandler: AlertBox,
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedEvent by remember { mutableStateOf(EventData("", "", "", LocalDate(1,1,1))) }
    var enrolledInSelected by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.oAuthToken) {
        coroutineScope.launch {
            Firebase.Firestore.listDocuments<EventData>(
                path = "events",
                idToken = viewModel.firebaseUserInfo!!.idToken
            ).onSuccess {
                val events = it.values.toList()

                viewModel.events = events.filterNot { event -> event.time.isBefore(LocalDate.now()) }
            }.onFailure {
                alertHandler.displayAlert("Load Fail", "Failed to load events: ${it.message}")
            }
        }
    }

    defaultScreen(
        coroutineScope,
        viewModel,
        title = "Events",
        painter = painterResource(Res.drawable.Event),
        alertHandler = alertHandler
    ) {
        val localDensity = LocalDensity.current

        var boxWidth by remember { mutableStateOf(0.dp) }
        var boxHeight by remember { mutableStateOf(0.dp) }

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
            if (viewModel.events.isEmpty()) {
                Text(
                    text = "We have no events to show you right now!",
                    maxLines = 1,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
            LazyColumn (
                Modifier.fillMaxSize().scrollable(rememberScrollState(), Orientation.Vertical),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(viewModel.events.size) { index ->
                    Spacer(modifier = Modifier.height(10.dp))
                    outlineBox(
                        title = viewModel.events[index].name, DpSize(boxWidth - 196.dp, 100.dp),
                    ) {
                        Column(Modifier.fillMaxSize().clickable{
                            selectedEvent = viewModel.events[index]
                            enrolledInSelected = isEnrolled(selectedEvent, viewModel)
                            showDialog = true
                        }) {
                            Text(
                                text = viewModel.events[index].description.take(50) + if (viewModel.events[index].description.length > 50) "..." else "",
                                maxLines = 1,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                textAlign = TextAlign.Left,
                                modifier = Modifier.weight(1f).fillMaxWidth().padding(10.dp)
                            )
                            Text(
                                text = viewModel.events[index].location + " | " + viewModel.events[index].time,
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

    viewPanel("EVENT OVERVIEW", DpSize(0.dp, 0.dp), DpSize(1200.dp, 800.dp), showDialog, closeHandler = { showDialog = false }) {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = selectedEvent.name,
                maxLines = 1,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(10.dp)
            )
            Text(
                text = selectedEvent.description,
                maxLines = Int.MAX_VALUE,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f).padding(10.dp)
            )
            Text(
                text = selectedEvent.location + " | " + selectedEvent.time,
                maxLines = 1,
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(10.dp)
            )

            if (viewModel.userData!!.role != UserRole.Anonymous) {
                clickableOutlineTextTitleless(DpSize(300.dp, 60.dp), text = if (enrolledInSelected) "Unenroll" else "Enroll") {
                    coroutineScope.launch {
                        if (updating) return@launch // guard clause
                        updating = true
                        if (enrolledInSelected) {
                            viewModel.userData = viewModel.userData!!.copy(
                                events = viewModel.userData!!.events.filterNot { it == selectedEvent }
                            )
                        } else {
                            viewModel.userData = viewModel.userData!!.copy(
                                events = viewModel.userData!!.events + selectedEvent
                            )
                        }

                        // Push changes to database
                        Firebase.Firestore.updateDocument(
                            "users/${viewModel.firebaseUserInfo!!.localId}",
                            viewModel.firebaseUserInfo!!.idToken,
                            viewModel.userData!!
                        )
                        enrolledInSelected = !enrolledInSelected
                        updating = false
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
