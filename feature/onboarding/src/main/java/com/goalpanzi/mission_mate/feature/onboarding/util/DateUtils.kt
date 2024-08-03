package com.goalpanzi.mission_mate.feature.onboarding.util

import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale
import kotlin.math.absoluteValue

object DateUtils {
    private fun convertMillisToLocalDateWithFormatter(date: LocalDate, dateTimeFormatter: DateTimeFormatter) : LocalDate {
        val dateInMillis = LocalDate.parse(date.format(dateTimeFormatter), dateTimeFormatter)
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        return Instant
            .ofEpochMilli(dateInMillis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }
    fun dateToString(date: LocalDate): String {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd", Locale.getDefault())
        val dateInMillis = convertMillisToLocalDateWithFormatter(date, dateFormatter)
        return dateFormatter.format(dateInMillis)
    }

    fun longToLocalDate(milliseconds: Long): LocalDate {
        val instant = Instant.ofEpochMilli(milliseconds)

        return instant.atZone(ZoneId.systemDefault()).toLocalDate()
    }

    fun localDateToMillis(localDate: LocalDate?): Long? {
        val instant = localDate?.atStartOfDay(ZoneId.of("UTC"))?.toInstant() ?: return null

        return instant.toEpochMilli()
    }

    fun filterDatesByDayOfWeek(startDate: LocalDate?, endDate: LocalDate?, days: List<DayOfWeek>): Int {
        if(startDate == null || endDate == null) return 0
        return generateSequence(startDate) { date ->
            if (date.isBefore(endDate)) date.plusDays(1) else null
        }.filter { it.dayOfWeek in days }.toList().size
    }

    fun isDifferenceTargetDaysOrMore(
        startDate: LocalDate,
        endDate: LocalDate,
        targetDifferenceDays : Int = 7
    ) = ChronoUnit.DAYS.between(startDate, endDate).absoluteValue >= targetDifferenceDays

}