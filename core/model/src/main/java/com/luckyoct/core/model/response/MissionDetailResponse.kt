package com.luckyoct.core.model.response

import kotlinx.serialization.Serializable

@Serializable
data class MissionDetailResponse(
    val missionId : Long,
    val hostMemberId : Long,
    val description : String,
    val missionStartDate : String,
    val missionEndDate : String,
    val timeOfDay : String,
    val missionDays : List<String>,
    val boardCount : Int,
    val invitationCode : String
)