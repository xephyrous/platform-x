package org.xephyrous.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.jetbrains.compose.resources.painterResource
import org.xephyrous.components.defaultScreen
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

    Column {
        Text(text = yearMonth.getDisplayName())

        // Display weekday names (Sun, Mon, Tue, ...)
        Row {
            val weekdayNames = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
            weekdayNames.forEach {
                Text(text = it, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            }
        }

        // Calendar grid
        val rows = (days.size + startDayOfWeek) / 7 + 1
        for (rowIndex in 0 until rows) {
            Row {
                // Generate each day of the row
                for (colIndex in 0 until 7) {
                    val dayIndex = rowIndex * 7 + colIndex - startDayOfWeek
                    if (dayIndex in 0 until days.size) {
                        val day = days[dayIndex]
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .size(40.dp)
                                .background(Color.LightGray)
                        ) {
                            Text(
                                text = day.day.toString(),
                                modifier = Modifier.align(Alignment.Center),
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.size(40.dp))
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
    navController: NavController, modifier: Modifier = Modifier
) {
    defaultScreen(navController = navController, title = "Calendar", painter = painterResource(Res.drawable.Calendar)) {
        CalendarScreen()
    }
}
