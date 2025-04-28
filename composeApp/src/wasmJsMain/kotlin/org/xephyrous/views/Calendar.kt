package org.xephyrous.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.jetbrains.compose.resources.painterResource
import org.xephyrous.components.defaultScreen
import org.xephyrous.components.outlineBox
import org.xephyrous.data.LocalDate
import org.xephyrous.data.YearMonth
import platformx.composeapp.generated.resources.Calendar
import platformx.composeapp.generated.resources.Res

@Composable
fun CalendarView(yearMonth: YearMonth) {
    val firstDayOfMonth = yearMonth.getFirstDayOfMonth()
    val daysInMonth = yearMonth.getDaysInMonth()
    val startDayOfWeek = firstDayOfMonth.dayOfWeek  // This gives us the starting weekday of the month

    val days = List(daysInMonth) { day -> LocalDate(yearMonth.year, yearMonth.month, day + 1) }

    val localDensity = LocalDensity.current

    var boxWidth by remember { mutableStateOf(0.dp) }
    var boxHeight by remember { mutableStateOf(0.dp) }

    Box(
        Modifier.fillMaxSize().onGloballyPositioned {
            boxWidth = with(localDensity) { it.size.width.toDp() }
            boxHeight = with(localDensity) { it.size.height.toDp() }
        }
    ) {
        Text(text = yearMonth.getDisplayName())

        // Display weekday names (Sun, Mon, Tue, ...)
        Row {
            val weekdayNames = listOf("Sat", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri")
            weekdayNames.forEach {
                Text(text = it, modifier = Modifier.weight(1f), textAlign = TextAlign.Center) // change sizes
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
                        outlineBox(
                            day.day.toString(),
                            DpSize(boxWidth/15, boxWidth/15),
                            boxWidth/15*(colIndex+4) - boxWidth/30, boxWidth/16*(rowIndex+1) - boxWidth/30,
                        ){
                            Text(
                                text = day.day.toString(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalendarScreen() {
    val yearMonth = YearMonth.now()
    CalendarView(yearMonth = yearMonth)
}

@Composable
fun Calendar(
    viewController: ViewController, modifier: Modifier = Modifier
) {
    defaultScreen(viewController, title = "Calendar", painter = painterResource(Res.drawable.Calendar)) {
        CalendarScreen()
    }
}
