package org.xephyrous.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowBackIos
import androidx.compose.material.icons.automirrored.sharp.ArrowForwardIos
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.CoroutineScope
import org.jetbrains.compose.resources.painterResource
import org.xephyrous.components.AlertBox
import org.xephyrous.components.clickableOutlineTextTitleless
import org.xephyrous.components.defaultScreen
import org.xephyrous.components.outlineTextTitleless
import org.xephyrous.components.viewPanel
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
            // TODO: ADD EVENTS LAZY COL
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
