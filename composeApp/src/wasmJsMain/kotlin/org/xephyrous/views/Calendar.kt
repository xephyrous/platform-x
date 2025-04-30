package org.xephyrous.views

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.automirrored.sharp.ArrowForwardIos
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
import org.jetbrains.compose.resources.painterResource
import org.xephyrous.components.*
import org.xephyrous.data.EventData
import org.xephyrous.data.LocalDate
import org.xephyrous.data.ViewModel
import org.xephyrous.data.YearMonth
import platformx.composeapp.generated.resources.Calendar
import platformx.composeapp.generated.resources.Res

fun findEventsOnDay(
    localDate: LocalDate,
    viewModel: ViewModel
) : List<EventData> {
    val events = mutableListOf<EventData>()

    viewModel.userData!!.events.forEach { event -> if (event.time == localDate) events.add(event) }

    return events.toList()
}

@Composable
fun CalendarView(
    viewModel: ViewModel,
    yearMonth: YearMonth,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit
) {
    val firstDayOfMonth = yearMonth.getFirstDayOfMonth()
    val daysInMonth = yearMonth.getDaysInMonth()
    val startDayOfWeek = firstDayOfMonth.dayOfWeek  // This gives us the starting weekday of the month

    val days = List(daysInMonth) { day -> LocalDate(yearMonth.year, yearMonth.month, day + 1) }

    val localDensity = LocalDensity.current

    var boxWidth by remember { mutableStateOf(0.dp) }
    var boxHeight by remember { mutableStateOf(0.dp) }

    var panelX by remember { mutableStateOf(0.dp) }
    var panelY by remember { mutableStateOf(0.dp) }
    var panel by remember { mutableStateOf(false) }
    var viewDate by remember { mutableStateOf<LocalDate>(LocalDate(2, 2, 2)) } // this should never show
    var events: List<EventData> by remember { mutableStateOf<List<EventData>>(emptyList()) }

    Box(
        Modifier.fillMaxSize().onGloballyPositioned {
            boxWidth = with(localDensity) { it.size.width.toDp() }
            boxHeight = with(localDensity) { it.size.height.toDp() }
        }
    ) {
        outlineTextTitleless(
            DpSize(boxWidth/4, boxHeight/32),
            boxWidth/2 - boxWidth/8, 0.dp,
            26.sp, text = yearMonth.getDisplayName()
        )

        // Display weekday names (Sun, Mon, Tue, ...)
        Box {
            val weekdayNames = listOf("Sat", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri")
            for (weekdayIndex in 0 until weekdayNames.size) {
                outlineTextTitleless(
                    DpSize(boxWidth/16, boxHeight/32),
                    boxWidth/14*(weekdayIndex+4) - boxWidth/32, boxWidth/30,
                    26.sp,
                    text = weekdayNames[weekdayIndex]
                )
            }
        }

        // Calendar grid
        val rows = (days.size + startDayOfWeek) / 7 + 1
        for (rowIndex in 0 until rows) {
            Box {
                // Generate each day of the row
                for (colIndex in 0 until 7) {
                    val dayIndex = rowIndex * 7 + colIndex - startDayOfWeek
                    if (dayIndex in 0 until days.size) {
                        val day = days[dayIndex]
                        clickableOutlineTextTitleless(
                            DpSize(boxWidth/16, boxWidth/16),
                            boxWidth/14*(colIndex+4) - boxWidth/32, boxWidth/14*(rowIndex+1),
                            40.sp, text =day.day.toString(),
                            onClick = {
                                events = findEventsOnDay(day, viewModel)
                                viewDate = day
                                panelX = boxWidth/14*(colIndex+4) - boxWidth/32
                                panelY = boxWidth/14*(rowIndex+1)
                                panel = true
                            }
                        )
                    } else {
                        outlineTextTitleless(
                            DpSize(boxWidth/16, boxWidth/16),
                            boxWidth/14*(colIndex+4) - boxWidth/32, boxWidth/14*(rowIndex+1),
                            40.sp, text = ""
                        )
                    }
                }
            }
        }

        IconButton(
            onClick = { onPreviousMonth() },
            modifier = Modifier.offset(x = boxWidth/14*3, y = boxHeight/2 - boxWidth/32).size(boxWidth/32),
            enabled = true,
        ) {
            Icon(Icons.AutoMirrored.Sharp.ArrowBackIos, contentDescription = "Previous Month", Modifier.fillMaxSize(), tint = Color.White)
        }

        IconButton(
            onClick = { onNextMonth() },
            modifier = Modifier.offset(x = boxWidth/14*11 - boxWidth/32, y = boxHeight/2 - boxWidth/32).size(boxWidth/32),
            enabled = true,
        ) {
            Icon(Icons.AutoMirrored.Sharp.ArrowForwardIos, contentDescription = "Next Month", Modifier.fillMaxSize(), tint = Color.White)
        }

        viewPanel(
            "Daily Events",
            DpSize(boxWidth/16, boxHeight/16), DpSize(boxWidth-80.dp, boxHeight-80.dp),
            panelX, panelY, 40.dp, 40.dp, panel, closeHandler = { panel = false }
        ) {
            Column(Modifier.fillMaxSize()) {
                Text(
                    text = "Events For: $viewDate",
                    maxLines = 1,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(10.dp)
                )
                if (events.isEmpty()) {
                    Text(
                        text = "You have no events for this day!",
                        maxLines = 1,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f).padding(10.dp)
                    )
                }
                LazyColumn (
                    Modifier.fillMaxSize().scrollable(rememberScrollState(), Orientation.Vertical),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(events.size) { index ->
                        Spacer(modifier = Modifier.height(12.dp))
                        outlineBox(
                            title = events[index].name, DpSize(boxWidth - 160.dp, 240.dp), fontSize = 40.sp,
                        ) {
                            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = events[index].description,
                                    maxLines = 8,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.weight(1f).padding(10.dp)
                                )

                                Box(Modifier.offset(y = 8.dp).size(boxWidth - 200.dp, 4.dp).background(color = Color.White)) {}

                                Text(
                                    text = events[index].location,
                                    maxLines = 1,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 26.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth().padding(10.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun CalendarScreen(
    viewModel: ViewModel
) {
    var currentYearMonth by remember { mutableStateOf(YearMonth.now()) }

    fun goToPreviousMonth() {
        currentYearMonth = if (currentYearMonth.month == 1) {
            YearMonth(currentYearMonth.year - 1, 12)
        } else {
            YearMonth(currentYearMonth.year, currentYearMonth.month - 1)
        }
    }

    fun goToNextMonth() {
        currentYearMonth = if (currentYearMonth.month == 12) {
            YearMonth(currentYearMonth.year + 1, 1)
        } else {
            YearMonth(currentYearMonth.year, currentYearMonth.month + 1)
        }
    }

    CalendarView(
        viewModel = viewModel,
        yearMonth = currentYearMonth,
        onPreviousMonth = ::goToPreviousMonth,
        onNextMonth = ::goToNextMonth
    )
}

@Composable
fun Calendar(
    coroutineScope: CoroutineScope, viewModel: ViewModel, alertHandler: AlertBox
) {
    defaultScreen(coroutineScope, viewModel, title = "Calendar", painter = painterResource(Res.drawable.Calendar), alertHandler = alertHandler) {
        CalendarScreen(viewModel)
    }
}
