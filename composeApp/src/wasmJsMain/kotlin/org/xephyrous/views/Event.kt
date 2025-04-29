package org.xephyrous.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.xephyrous.apis.Firebase
import org.xephyrous.components.AlertBox
import org.xephyrous.components.defaultScreen
import org.xephyrous.data.EventData
import org.xephyrous.data.ViewModel
import platformx.composeapp.generated.resources.Event
import platformx.composeapp.generated.resources.Res

@Composable
fun Event(
    coroutineScope: CoroutineScope,
    viewModel: ViewModel,
    alertHandler: AlertBox,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedEvent by remember { mutableStateOf<EventData?>(null) }

    LaunchedEffect(viewModel.oAuthToken) {
        coroutineScope.launch {
            val token = viewModel.oAuthToken
            if (token != null) {
                val result = Firebase.Firestore.listDocuments<EventData>(
                    path = "Events",
                    idToken = token
                )

                result.onSuccess { documents ->
                    viewModel.events = documents.values.toList()
                }.onFailure {
                    println("Failed to load events: ${it.message}")
                }
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
            viewModel.events.forEach { event ->
                EventSummaryCard(event = event, onClick = {
                    selectedEvent = event
                    showDialog = true
                })
            }
        }
    }

    if (showDialog && selectedEvent != null) {
        EventDetailsDialog(event = selectedEvent!!, onDismiss = { showDialog = false })
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

@Composable
fun EventDetailsDialog(event: EventData, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(event.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(event.description)
                Text("LOCATION: ${event.location}", fontWeight = FontWeight.Medium)
                Text("TIME: ${event.time.month}/${event.time.day}/${event.time.year}")
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}
