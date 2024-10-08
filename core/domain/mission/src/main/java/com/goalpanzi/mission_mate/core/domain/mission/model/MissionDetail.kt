package com.goalpanzi.mission_mate.core.domain.mission.model

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

data class MissionDetail(
    val missionId : Long,
    val hostMemberId : Long,
    val description : String,
    val missionStartDate : String,
    val missionEndDate : String,
    val timeOfDay : String,
    val missionDays : List<DayOfWeek>,
    val boardCount : Int,
    val invitationCode : String
){
    val missionStartLocalDate: LocalDate by lazy {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        LocalDate.parse(missionStartDate, formatter)
    }

    val missionEndLocalDateTime : LocalDateTime by lazy {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime.parse(missionEndDate, formatter)
    }

    fun isStartedMission() : Boolean {
        val currentDate = LocalDate.now()
        return currentDate.isEqual(missionStartLocalDate) || currentDate.isAfter(missionStartLocalDate)
    }
    val missionPeriod : String by lazy {
        try {
            val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            val outputFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

            val startDate = LocalDateTime.parse(missionStartDate, inputFormatter)
            val endDate = LocalDateTime.parse(missionEndDate, inputFormatter)

            "${startDate.format(outputFormatter)} ~ ${endDate.format(outputFormatter)}"
        }catch (e: Exception){
            "$missionStartDate ~ $missionEndDate"
        }
    }

    val missionDaysOfWeekTextLocale : List<String> by lazy {
        try {
            missionDays.sortedBy {
                it.ordinal
            }.map {
                it.getDisplayName(TextStyle.SHORT, Locale.getDefault())
            }
        }catch (e: Exception){
            missionDays.map { it.name }
        }
    }
}
