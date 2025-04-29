package org.xephyrous.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import org.xephyrous.components.AlertBox
import org.xephyrous.components.clickableOutlineTextTitleless
import org.xephyrous.components.defaultScreen
import org.xephyrous.components.viewPanel
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

@Composable
fun Event(
    coroutineScope: CoroutineScope,
    viewModel: ViewModel,
    alertHandler: AlertBox,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedEvent by remember { mutableStateOf<EventData>(EventData("", "", "", LocalDate(1,1,1))) }
    var enrolledInSelected by remember { mutableStateOf(false) }
    var updating = false

    LaunchedEffect(viewModel.oAuthToken) {
        coroutineScope.launch {
            Firebase.Firestore.listDocuments<EventData>(
                path = "events",
                idToken = viewModel.firebaseUserInfo!!.idToken
            ).onSuccess {
                viewModel.events = it.values.toList()
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
        Column(
            modifier = modifier
                .fillMaxSize()
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
            viewModel.events.forEach { event ->
                EventSummaryCard(event = event, onClick = {
                    selectedEvent = event
                    enrolledInSelected = isEnrolled(selectedEvent, viewModel)
                    showDialog = true
                })
            }
        }
    }

    viewPanel("EVENT OVERVIEW", DpSize(0.dp, 0.dp), DpSize(1200.dp, 800.dp), showDialog, closeHandler = { showDialog = false }) {
        Column(Modifier.fillMaxSize()) {
            Text(
                text = selectedEvent.name,
                maxLines = 1,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(10.dp)
            )
            Column(Modifier.weight(1f).scrollable(rememberScrollState(), Orientation.Vertical)) {
                Text(
                    text = selectedEvent.description,
                    maxLines = Int.MAX_VALUE,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(10.dp)
                )
            }
            Text(
                text = selectedEvent.time.toString(),
                maxLines = 1,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
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
        }
    }
}

@Composable
fun EventSummaryCard(event: EventData, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(event.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = event.description.take(50) + "...",
                fontSize = 14.sp,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "TIME: ${event.time.month}/${event.time.day}/${event.time.year}",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}
