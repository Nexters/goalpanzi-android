package com.goalpanzi.mission_mate.core.network.model.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateMissionRequest(
    val description : String,
    val missionStartDate : String,
    val missionEndDate : String,
    val timeOfDay : String,
    val missionDays : List<String>,
    val boardCount : Int
)