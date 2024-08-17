package com.goalpanzi.mission_mate.feature.board.model

import com.luckyoct.core.model.response.MissionDetailResponse
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
}

fun MissionDetailResponse.toModel() : MissionDetail {
    return MissionDetail(
        missionId = missionId,
        hostMemberId = hostMemberId,
        description = description,
        missionStartDate = missionStartDate,
        missionEndDate = missionEndDate,
        boardCount = boardCount,
        invitationCode = invitationCode,
        missionDays =  missionDays.map {
            DayOfWeek.valueOf(it)
        },
        timeOfDay = timeOfDay
    )
}