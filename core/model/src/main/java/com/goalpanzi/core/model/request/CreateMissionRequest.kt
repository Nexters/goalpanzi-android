package com.goalpanzi.core.model.request

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