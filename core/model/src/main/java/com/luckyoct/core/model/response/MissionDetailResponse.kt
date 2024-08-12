package com.luckyoct.core.model.response

import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Serializable
data class MissionDetailResponse(
    val missionId: Long,
    val hostMemberId: Long,
    val description: String,
    val missionStartDate: String,
    val missionEndDate: String,
    val timeOfDay: String,
    val missionDays: List<String>,
    val boardCount: Int,
    val invitationCode: String
) {
    val missionStartLocalDate: LocalDate by lazy {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        LocalDate.parse(missionStartDate, formatter)
    }

    fun isStartedMission() : Boolean {
        val currentDate = LocalDate.now()
        return currentDate.isEqual(missionStartLocalDate) || currentDate.isAfter(missionStartLocalDate)
    }


}