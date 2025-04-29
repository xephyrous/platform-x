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
    private fun getDaysInMonth(year: Int, month: Int): Int {
        return when (month) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            4, 6, 9, 11 -> 30
            2 -> if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) 29 else 28
            else -> throw IllegalArgumentException("Invalid month $month")
        }
    }

    private fun getMonthName(): String {
        val monthNames = listOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )
        return monthNames[month - 1]
    }

    private fun getDaySuffix(): String {
        if (day in 11..13) return "th" // Special case for teens
        return when (day % 10) {
            1 -> "st"
            2 -> "nd"
            3 -> "rd"
            else -> "th"
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

    override fun toString(): String {
        return "${getMonthName()} $day${getDaySuffix()}, $year"
    }

    companion object {
        fun now() = LocalDate(2025, 5, 1)  // Hardcoded May 2025
    }
}
