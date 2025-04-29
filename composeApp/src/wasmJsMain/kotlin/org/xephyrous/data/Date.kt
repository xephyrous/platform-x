package org.xephyrous.data

import kotlinx.serialization.Serializable

data class YearMonth(val year: Int, val month: Int) {
    val monthValue: Int get() = month

    fun getDisplayName(): String {
        val monthNames = listOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )
        return "${monthNames[month - 1]} $year"
    }

    fun getFirstDayOfMonth(): LocalDate = LocalDate(year, month, 1)

    fun getDaysInMonth(): Int = when (month) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        4, 6, 9, 11 -> 30
        2 -> if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) 29 else 28
        else -> throw IllegalArgumentException("Invalid month $month")
    }

    companion object {
        fun now(): YearMonth {
            // Hardcoded to May 2025 for WASM compatibility
            return YearMonth(2025, 5)
        }
    }
}

@Serializable
data class LocalDate(val year: Int, val month: Int, val day: Int) {
    fun plusMonths(months: Int): LocalDate {
        val newMonth = (month + months - 1) % 12 + 1
        val newYear = year + (month + months - 1) / 12
        return LocalDate(newYear, newMonth, day)
    }

    fun plusDays(days: Int): LocalDate {
        var newDay = day + days
        var newMonth = month
        var newYear = year

        val daysInMonth = getDaysInMonth(year, month)

        while (newDay > daysInMonth) {
            newDay -= daysInMonth
            newMonth++
            if (newMonth > 12) {
                newMonth = 1
                newYear++
            }
        }

        return LocalDate(newYear, newMonth, newDay)
    }
    private fun getDaysInMonth(year: Int, month: Int): Int {
        return when (month) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            4, 6, 9, 11 -> 30
            2 -> if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) 29 else 28
            else -> throw IllegalArgumentException("Invalid month $month")
        }
    }

    val dayOfWeek: Int
        get() {
            val mon = when (month) {
                1 -> 13
                2 -> 14
                else -> month
            }
            val yr = if (month <= 2) year - 1 else year
            val q = day
            val k = yr % 100
            val j = yr / 100
            val h = q + 13*(mon + 1) / 5 + k + k / 4 + j / 4 + 5 * j
            return h % 7
        }

    override fun equals(other: Any?): Boolean {
        return (other is LocalDate) && year == other.year && month == other.month && day == other.day
    }

    companion object {
        fun now() = LocalDate(2025, 5, 1)  // Hardcoded May 2025
    }
}
